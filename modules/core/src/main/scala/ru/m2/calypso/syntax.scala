package ru.m2.calypso

import cats.syntax.either.*
import org.bson.{BsonDocument, BsonValue}

import scala.jdk.CollectionConverters.*

object syntax:

  implicit class EncoderOps[A](val a: A) extends AnyVal:
    def asBson(using Encoder[A]): BsonValue = Encoder[A].apply(a)
    def asKey(using KeyEncoder[A]): String  = KeyEncoder[A].apply(a)

  implicit class BsonValueOps(val bson: BsonValue) extends AnyVal:
    def focus: Either[String, BsonDocument] =
      Either.catchNonFatal(bson.asDocument()).leftMap(_.getMessage)

    def as[A: Decoder]: Either[String, A] =
      Decoder[A].apply(bson)

  implicit class StringOps(val s: String) extends AnyVal:
    def as[K: KeyDecoder]: Either[String, K] = KeyDecoder[K].apply(s)

  implicit class BsonDocumentOps(val bson: BsonDocument) extends AnyVal:

    /** BsonNull on non-existing keys allows to avoid Zipper/Cursor on decoding
      */
    def downField(k: String): BsonValue =
      Option(bson.get(k)).getOrElse(Bson.nullValue)

    def asList: List[(String, BsonValue)] =
      bson.entrySet.asScala.toList.map(e => e.getKey -> e.getValue)
