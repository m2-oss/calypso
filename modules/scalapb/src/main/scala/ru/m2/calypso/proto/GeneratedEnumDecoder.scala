package ru.m2.calypso.proto

import cats.syntax.either._
import ru.m2.calypso.Decoder
import scalapb.GeneratedEnum
import scalapb.GeneratedEnumCompanion

object GeneratedEnumDecoder {
  implicit def decodeGeneratedEnum[A <: GeneratedEnum: GeneratedEnumCompanion]: Decoder[A] =
    Decoder[Int].emap { e =>
      Either
        .catchNonFatal(implicitly[GeneratedEnumCompanion[A]].fromValue(e))
        .bimap(_.getMessage, identity)
    }
}
