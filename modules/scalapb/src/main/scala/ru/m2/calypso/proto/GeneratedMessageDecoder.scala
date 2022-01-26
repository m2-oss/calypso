package ru.m2.calypso.proto

import cats.syntax.either._
import ru.m2.calypso.Decoder
import scalapb.GeneratedMessageCompanion

object GeneratedMessageDecoder {
  implicit def decodeGeneratedMessageFromBinary[
      A <: scalapb.GeneratedMessage: GeneratedMessageCompanion
  ]: Decoder[A] =
    Decoder[Array[Byte]]
      .emap { ba =>
        Either
          .catchNonFatal(implicitly[GeneratedMessageCompanion[A]].parseFrom(ba))
          .leftMap(_.getMessage)
      }
}
