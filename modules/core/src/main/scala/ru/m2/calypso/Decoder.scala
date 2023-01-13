package ru.m2.calypso

import cats.data.NonEmptyList
import cats.syntax.either._
import cats.syntax.traverse._
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import eu.timepit.refined.string.Uuid
import org.bson._
import ru.m2.calypso.boilerplate.{CoproductDecoders, ProductDecoders}
import ru.m2.calypso.syntax._

import java.time.Instant
import java.util.UUID
import scala.collection.immutable.{SortedMap, SortedSet}
import scala.jdk.CollectionConverters._

trait Decoder[A] {
  def apply(b: BsonValue): Either[String, A]

  final def map[B](f: A => B): Decoder[B] =
    Decoder.instance { bson =>
      apply(bson).map(f)
    }

  final def emap[B](f: A => Either[String, B]): Decoder[B] =
    Decoder.instance { bson =>
      apply(bson).flatMap(f)
    }

  final def or[AA >: A](d: => Decoder[AA]): Decoder[AA] =
    Decoder.instance { bson =>
      apply(bson).orElse(d(bson))
    }

}

object Decoder extends ProductDecoders with CoproductDecoders {
  def apply[A](implicit instance: Decoder[A]): Decoder[A] = instance

  def instance[A](f: BsonValue => Either[String, A]): Decoder[A] = f(_)

  implicit val decodeBoolean: Decoder[Boolean] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asBoolean()).bimap(_.getMessage, _.getValue)
    }

  implicit val decodeInt: Decoder[Int] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asInt32()).bimap(_.getMessage, _.getValue)
    }

  implicit val decodeLong: Decoder[Long] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asInt64()).bimap(_.getMessage, _.getValue)
    }

  implicit val decodeDouble: Decoder[Double] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asDouble).bimap(_.getMessage, _.getValue)
    }

  implicit val decodeString: Decoder[String] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asString()).bimap(_.getMessage, _.getValue)
    }

  implicit val decodeArrayByte: Decoder[Array[Byte]] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asBinary()).bimap(_.getMessage, _.getData)
    }

  implicit val decodeUnit: Decoder[Unit] =
    Decoder.instance { bson =>
      Either.catchNonFatal(bson.asDocument()).bimap(_.getMessage, _ => ())
    }

  def const[A](a: A): Decoder[A] = Decoder.instance(_ => a.asRight)

  implicit def decodeTuple2[A: Decoder, B: Decoder]: Decoder[(A, B)] =
    Decoder.forProduct2[(A, B), A, B]("_1", "_2")(_ -> _)

  implicit def decodeList[A: Decoder]: Decoder[List[A]] =
    Decoder.instance { bson =>
      for {
        xs <- Either.catchNonFatal(bson.asArray()).bimap(_.getMessage, _.asScala.toList)
        as <- xs.traverse(_.as[A])
      } yield as
    }

  implicit def decodeNel[A: Decoder]: Decoder[NonEmptyList[A]] =
    Decoder[List[A]].emap { xs =>
      NonEmptyList.fromList(xs) match {
        case None     => "NonEmptyList.empty".asLeft
        case Some(xs) => xs.asRight
      }
    }

  implicit def decodeSet[A: Decoder]: Decoder[Set[A]] =
    Decoder.instance { bson =>
      bson.as[List[A]].map(_.toSet)
    }

  implicit def decodeSortedSet[A: Decoder: Ordering]: Decoder[SortedSet[A]] =
    Decoder.instance { bson =>
      bson.as[List[A]].map(l => SortedSet.from(l))
    }

  implicit def decodeMap[K: KeyDecoder, V: Decoder]: Decoder[Map[K, V]] =
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

  implicit def decodeSortedMap[K: KeyDecoder: Ordering, V: Decoder]: Decoder[SortedMap[K, V]] =
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

  implicit def decodeOption[A: Decoder]: Decoder[Option[A]] =
    Decoder.instance { bson =>
      bson.as[A].toOption.asRight
    }

  implicit def decodeEither[A: Decoder, B: Decoder]: Decoder[Either[A, B]] =
    Decoder.forCoproduct2("left", "right")(Decoder[A].map(_.asLeft), Decoder[B].map(_.asRight))

  implicit def decodeRefined[A: Decoder, P](implicit v: Validate[A, P]): Decoder[A Refined P] =
    Decoder[A].emap(refineV(_))

  implicit val decodeInstant: Decoder[Instant] =
    Decoder.instance { bson =>
      Either
        .catchNonFatal(bson.asDateTime())
        .bimap(_.getMessage, t => Instant.ofEpochMilli(t.getValue))
    }

  implicit val decodeUUID: Decoder[UUID] =
    Decoder.instance { bson =>
      for {
        b    <- Either.catchNonFatal(bson.asBinary()).leftMap(_.getMessage)
        uuid <- Either.catchNonFatal(b.asUuid()).leftMap(_.getMessage)
      } yield uuid
    }

  implicit val decodeStringRefinedUuid: Decoder[String Refined Uuid] =
    Decoder.instance { bson =>
      for {
        b  <- Either.catchNonFatal(bson.asBinary()).leftMap(_.getMessage)
        s  <- Either.catchNonFatal(b.asUuid()).bimap(_.getMessage, _.toString)
        sr <- refineV[Uuid](s)
      } yield sr
    }
}
