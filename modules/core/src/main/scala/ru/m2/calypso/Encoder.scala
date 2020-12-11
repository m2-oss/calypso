package ru.m2.calypso

import cats.data.NonEmptyList
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uuid
import org.bson.BsonValue
import ru.m2.calypso.boilerplate.{CoproductEncoder, ProductEncoder}
import ru.m2.calypso.syntax._

import java.time.Instant
import java.util.UUID
import scala.collection.{SortedMap, SortedSet}

trait Encoder[A] {
  def apply(a: A): BsonValue

  final def contramap[B](f: B => A): Encoder[B] =
    Encoder.instance { b =>
      apply(f(b))
    }
}

object Encoder extends ProductEncoder with CoproductEncoder {
  def apply[A](implicit instance: Encoder[A]): Encoder[A] = instance

  def instance[A](f: A => BsonValue): Encoder[A] = f(_)

  implicit val encodeBsonValue: Encoder[BsonValue] = Encoder.instance(identity)

  implicit val encodeBoolean: Encoder[Boolean] = Encoder.instance(Bson.boolean)

  implicit val encodeInt: Encoder[Int] = Encoder.instance(Bson.int32)

  implicit val encodeLong: Encoder[Long] = Encoder.instance(Bson.int64)

  implicit val encodeDouble: Encoder[Double] = Encoder.instance(Bson.double)

  implicit val encodeString: Encoder[String] = Encoder.instance(Bson.string)

  implicit val encodeArrayByte: Encoder[Array[Byte]] = Encoder.instance(Bson.binary)

  implicit val encodeUnit: Encoder[Unit] = Encoder.instance(_ => Bson.empty)

  implicit def encodeTuple2[A: Encoder, B: Encoder]: Encoder[(A, B)] =
    Encoder.forProduct2("_1", "_2")(identity)

  implicit def encodeList[A: Encoder]: Encoder[List[A]] =
    Encoder.instance { xs =>
      Bson.arr(xs.map(_.asBson))
    }

  implicit def encodeNel[A: Encoder]: Encoder[NonEmptyList[A]] =
    Encoder.instance(_.toList.asBson)

  implicit def encodeSet[A: Encoder]: Encoder[Set[A]] =
    Encoder.instance { s =>
      s.toList.asBson
    }

  implicit def encodeSortedSet[A: Encoder: Ordering]: Encoder[SortedSet[A]] =
    Encoder.instance { s =>
      s.toList.asBson
    }

  implicit def encodeMap[K: KeyEncoder, V: Encoder]: Encoder[Map[K, V]] =
    Encoder.instance { m =>
      Bson.of(m.foldLeft(List.empty[(String, BsonValue)]) { case (xs, (k, v)) =>
        k.asKey -> v.asBson :: xs
      })
    }

  implicit def encodeSortedMap[K: KeyEncoder: Ordering, V: Encoder]: Encoder[SortedMap[K, V]] =
    Encoder.instance { m =>
      Bson.of(m.foldLeft(List.empty[(String, BsonValue)]) { case (xs, (k, v)) =>
        k.asKey -> v.asBson :: xs
      })
    }

  implicit def encodeOption[A: Encoder]: Encoder[Option[A]] =
    Encoder.instance { oa =>
      oa.fold[BsonValue](Bson.nullValue)(_.asBson)
    }

  implicit def encodeEither[A: Encoder, B: Encoder]: Encoder[Either[A, B]] =
    Encoder.forCoproduct {
      case Left(a)  => "left"  -> a.asBson
      case Right(b) => "right" -> b.asBson
    }

  implicit def encodeRefined[A: Encoder, P]: Encoder[A Refined P] =
    Encoder[A].contramap(_.value)

  implicit val encodeInstant: Encoder[Instant] =
    Encoder.instance(t => Bson.dateTime(t.toEpochMilli))

  implicit val encodeUUID: Encoder[UUID] =
    Encoder.instance(Bson.uuid)

  implicit val encodeStringRefinedUuid: Encoder[String Refined Uuid] =
    Encoder.instance(Bson.uuid)
}
