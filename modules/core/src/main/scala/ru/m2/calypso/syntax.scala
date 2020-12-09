package ru.m2.calypso

import cats.syntax.either._
import org.bson.{BsonDocument, BsonValue}

import scala.jdk.CollectionConverters._

object syntax {

  implicit class BsonSyntax[A](val a: A) extends AnyVal {
    def asBson(implicit encoder: Encoder[A]): BsonValue =
      Encoder[A].apply(a)

    def asKey(implicit encoder: KeyEncoder[A]): String =
      KeyEncoder[A].apply(a)
  }

  implicit class BsonValueSyntax(val bson: BsonValue) extends AnyVal {
    def focus: Either[String, BsonDocument] =
      Either.catchNonFatal(bson.asDocument()).leftMap(_.getMessage)

    def as[A: Decoder]: Either[String, A] =
      Decoder[A].apply(bson)
  }

  implicit class StringSyntax(val s: String) extends AnyVal {
    def as[K: KeyDecoder]: Either[String, K] =
      KeyDecoder[K].apply(s)
  }

  implicit class BsonDocumentSyntax(val bson: BsonDocument) extends AnyVal {

    /** BsonNull on non-existing keys allows to avoid Zipper/Cursor on decoding
      */
    def downField(k: String): BsonValue =
      Option(bson.get(k)).getOrElse(Bson.nullValue)

    def asList: List[(String, BsonValue)] =
      bson.entrySet.asScala.toList.map(e => e.getKey -> e.getValue)
  }

}
