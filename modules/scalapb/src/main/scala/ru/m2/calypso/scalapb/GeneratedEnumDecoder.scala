package ru.m2.calypso.scalapb

import cats.syntax.either.*
import ru.m2.calypso.Decoder
import scalapb.{GeneratedEnum, GeneratedEnumCompanion}

object GeneratedEnumDecoder:

  given [A <: GeneratedEnum](using GMC: GeneratedEnumCompanion[A]): Decoder[A] =
    Decoder[Int].emap { e => Either.catchNonFatal(GMC.fromValue(e)).leftMap(_.getMessage) }
