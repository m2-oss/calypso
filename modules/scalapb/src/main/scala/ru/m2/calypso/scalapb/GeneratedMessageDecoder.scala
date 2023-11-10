package ru.m2.calypso.scalapb

import cats.syntax.either.*
import ru.m2.calypso.Decoder
import scalapb.GeneratedMessageCompanion

object GeneratedMessageDecoder:

  given [A <: scalapb.GeneratedMessage](using GMC: GeneratedMessageCompanion[A]): Decoder[A] =
    Decoder[Array[Byte]].emap { ba =>
      Either.catchNonFatal(GMC.parseFrom(ba)).leftMap(_.getMessage)
    }
