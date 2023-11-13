package ru.m2.calypso.scalapb

import ru.m2.calypso.Encoder
import ru.m2.calypso.syntax.*
import scalapb.GeneratedEnum

object GeneratedEnumEncoder:

  given [A <: GeneratedEnum]: Encoder[A] =
    e => e.value.asBson
