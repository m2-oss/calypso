package ru.m2.calypso

import cats.data.NonEmptyList
import org.bson.BsonValue
import ru.m2.calypso.boilerplate.ProductEncoders
import ru.m2.calypso.syntax.*

import java.time.Instant
import java.util.UUID
import scala.collection.immutable.{SortedMap, SortedSet}

trait Encoder[A]:
  def apply(a: A): BsonValue

  def contramap[B](f: B => A): Encoder[B] =
    b => apply(f(b))

object Encoder extends ProductEncoders:
  def apply[A: Encoder]: Encoder[A] = summon

  def instance[A](f: A => BsonValue): Encoder[A] = f(_)

  def forCoproduct[A](f: A => (String, BsonValue)): Encoder[A] =
    Encoder.forProduct2("tag", "value")(f)

  given Encoder[BsonValue]   = Encoder.instance(identity)
  given Encoder[Boolean]     = Encoder.instance(Bson.boolean)
  given Encoder[Int]         = Encoder.instance(Bson.int32)
  given Encoder[Long]        = Encoder.instance(Bson.int64)
  given Encoder[Double]      = Encoder.instance(Bson.double)
  given Encoder[String]      = Encoder.instance(Bson.string)
  given Encoder[Array[Byte]] = Encoder.instance(Bson.binary)
  given Encoder[Unit]        = Encoder.instance(_ => Bson.empty)
  given Encoder[Instant]     = Encoder.instance(t => Bson.dateTime(t.toEpochMilli))
  given Encoder[UUID]        = Encoder.instance(Bson.uuid)

  given [A: Encoder, B: Encoder]: Encoder[(A, B)] =
    Encoder.forProduct2("_1", "_2")(identity)

  given [A: Encoder]: Encoder[List[A]] =
    xs => Bson.arr(xs.map(_.asBson))

  given [A: Encoder]: Encoder[NonEmptyList[A]] =
    xs => xs.toList.asBson

  given [A: Encoder]: Encoder[Set[A]] =
    s => s.toList.asBson

  given [A: Encoder: Ordering]: Encoder[SortedSet[A]] =
    s => s.toList.asBson

  /** Preserves iteration order
    */
  given [K: KeyEncoder, V: Encoder]: Encoder[Map[K, V]] =
    m => Bson.of(m.toList.map { case (k, v) => k.asKey -> v.asBson })

  /** Preserves iteration order
    */
  given [K: KeyEncoder: Ordering, V: Encoder]: Encoder[SortedMap[K, V]] =
    m => Bson.of(m.toList.map { case (k, v) => k.asKey -> v.asBson })

  given [A: Encoder]: Encoder[Option[A]] =
    oa => oa.fold[BsonValue](Bson.nullValue)(_.asBson)

  given [A: Encoder, B: Encoder]: Encoder[Either[A, B]] =
    Encoder.forCoproduct {
      case Left(a)  => "left"  -> a.asBson
      case Right(b) => "right" -> b.asBson
    }
