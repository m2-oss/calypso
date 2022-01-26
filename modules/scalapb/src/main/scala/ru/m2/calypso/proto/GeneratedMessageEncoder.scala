package ru.m2.calypso.proto

import ru.m2.calypso.Encoder

object GeneratedMessageEncoder {
  implicit def encodeGeneratedMessageToBinary[A <: scalapb.GeneratedMessage]: Encoder[A] =
    Encoder[Array[Byte]].contramap(_.toByteArray)
}
