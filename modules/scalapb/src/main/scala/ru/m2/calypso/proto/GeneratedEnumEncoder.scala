package ru.m2.calypso.proto

import ru.m2.calypso.Encoder
import ru.m2.calypso.Encoder._
import ru.m2.calypso.syntax._
import scalapb.GeneratedEnum

object GeneratedEnumEncoder {
  implicit def encodeGeneratedEnum[A <: GeneratedEnum]: Encoder[A] =
    Encoder.instance(e => e.value.asBson)
}
