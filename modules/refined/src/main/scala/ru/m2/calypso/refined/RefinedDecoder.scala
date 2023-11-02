package ru.m2.calypso.refined

import cats.syntax.either._
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import eu.timepit.refined.string.Uuid
import ru.m2.calypso.Decoder

trait RefinedDecoder {

  implicit def decodeRefined[A: Decoder, P](implicit v: Validate[A, P]): Decoder[A Refined P] =
    Decoder[A].emap(refineV(_))

  implicit val decodeStringRefinedUuid: Decoder[String Refined Uuid] =
    Decoder.instance { bson =>
      for {
        b  <- Either.catchNonFatal(bson.asBinary()).leftMap(_.getMessage)
        s  <- Either.catchNonFatal(b.asUuid()).bimap(_.getMessage, _.toString)
        sr <- refineV[Uuid](s)
      } yield sr
    }
}
