package ru.m2.calypso

import cats.syntax.either.*
import org.bson.{BsonDocument, BsonValue}

import scala.jdk.CollectionConverters.*

object syntax:

  extension [A](a: A)

    def asBson(using Encoder[A]): BsonValue =
      Encoder[A].apply(a)

    def asKey(using KeyEncoder[A]): String =
      KeyEncoder[A].apply(a)

  extension (bson: BsonValue)

    def focus: Either[String, BsonDocument] =
      Either.catchNonFatal(bson.asDocument()).leftMap(_.getMessage)

    def as[A: Decoder]: Either[String, A] =
      Decoder[A].apply(bson)

  extension (s: String)
    def as[K: KeyDecoder]: Either[String, K] =
      KeyDecoder[K].apply(s)

  extension (bson: BsonDocument)
    /** BsonNull on non-existing keys allows to avoid Zipper/Cursor on decoding
      */
    def downField(k: String): BsonValue =
      Option(bson.get(k)).getOrElse(Bson.nullValue)

    def asList: List[(String, BsonValue)] =
      bson.entrySet.asScala.toList.map(e => e.getKey -> e.getValue)
