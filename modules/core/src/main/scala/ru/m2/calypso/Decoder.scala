package ru.m2.calypso

import cats.data.NonEmptyList
import cats.syntax.either._
import cats.syntax.traverse._
import org.bson._
import ru.m2.calypso.boilerplate.{CoproductDecoders, ProductDecoders}
import ru.m2.calypso.syntax._

import java.time.Instant
import java.util.UUID
import scala.collection.immutable.{SortedMap, SortedSet}
import scala.jdk.CollectionConverters._

trait Decoder[A]:

  def apply(b: BsonValue): Either[String, A]

  final def map[B](f: A => B): Decoder[B] =
    bson => apply(bson).map(f)

  final def emap[B](f: A => Either[String, B]): Decoder[B] =
    bson => apply(bson).flatMap(f)

  final def or[AA >: A](d: => Decoder[AA]): Decoder[AA] =
    bson => apply(bson).orElse(d(bson))

object Decoder extends ProductDecoders with CoproductDecoders:

  def apply[A: Decoder]: Decoder[A] = summon

  def instance[A](f: BsonValue => Either[String, A]): Decoder[A] = f(_)

  def const[A](a: A): Decoder[A] = Decoder.instance(_ => a.asRight)

  given Decoder[Boolean] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asBoolean()).bimap(_.getMessage, _.getValue)
    }

  given Decoder[Int] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asInt32()).bimap(_.getMessage, _.getValue)
    }

  given Decoder[Long] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asInt64()).bimap(_.getMessage, _.getValue)
    }

  given Decoder[Double] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asDouble).bimap(_.getMessage, _.getValue)
    }

  given Decoder[String] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asString()).bimap(_.getMessage, _.getValue)
    }

  given Decoder[Array[Byte]] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asBinary()).bimap(_.getMessage, _.getData)
    }

  given Decoder[Unit] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asDocument()).bimap(_.getMessage, _ => ())
    }

  given [A: Decoder, B: Decoder]: Decoder[(A, B)] =
    Decoder.forProduct2[(A, B), A, B]("_1", "_2")(_ -> _)

  given [A: Decoder]: Decoder[List[A]] =
    Decoder.instance { bson =>
      for {
        xs <- Either.catchNonFatal(bson.asArray()).bimap(_.getMessage, _.asScala.toList)
        as <- xs.traverse(_.as[A])
      } yield as
    }

  given [A: Decoder]: Decoder[NonEmptyList[A]] =
    Decoder[List[A]].emap { xs =>
      NonEmptyList.fromList(xs) match {
        case None     => "NonEmptyList.empty".asLeft
        case Some(xs) => xs.asRight
      }
    }

  given [A: Decoder]: Decoder[Set[A]] =
    Decoder.instance { bson =>
      bson.as[List[A]].map(_.toSet)
    }

  given [A: Decoder: Ordering]: Decoder[SortedSet[A]] =
    Decoder.instance { bson =>
      bson.as[List[A]].map(l => SortedSet.from(l))
    }

  given [K: KeyDecoder, V: Decoder]: Decoder[Map[K, V]] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        xs <- doc.asList.traverse { case (key, bson) =>
          for {
            k <- key.as[K]
            v <- bson.as[V]
          } yield k -> v
        }
      } yield xs.toMap
    }

  given [K: KeyDecoder: Ordering, V: Decoder]: Decoder[SortedMap[K, V]] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        xs <- doc.asList.traverse { case (key, bson) =>
          for {
            k <- key.as[K]
            v <- bson.as[V]
          } yield k -> v
        }
      } yield SortedMap.from(xs)
    }

  given [A: Decoder]: Decoder[Option[A]] =
    Decoder.instance { bson =>
      bson.as[A].toOption.asRight
    }

  given [A: Decoder, B: Decoder]: Decoder[Either[A, B]] =
    Decoder.forCoproduct2("left", "right")(Decoder[A].map(_.asLeft), Decoder[B].map(_.asRight))

  given Decoder[Instant] =
    Decoder.instance { bson =>
      Either
        .catchNonFatal(bson.asDateTime())
        .bimap(_.getMessage, t => Instant.ofEpochMilli(t.getValue))
    }

  given Decoder[UUID] =
    Decoder.instance { bson =>
      for {
        b    <- Either.catchNonFatal(bson.asBinary()).leftMap(_.getMessage)
        uuid <- Either.catchNonFatal(b.asUuid()).leftMap(_.getMessage)
      } yield uuid
    }
