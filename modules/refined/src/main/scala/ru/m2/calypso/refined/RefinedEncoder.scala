package ru.m2.calypso.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uuid
import org.bson.BsonBinary
import ru.m2.calypso.Encoder

import java.util.UUID

trait RefinedEncoder:

  given [A: Encoder, P]: Encoder[A Refined P] =
    Encoder[A].contramap(_.value)

  given Encoder[String Refined Uuid] =
    Encoder[UUID].contramap(s => UUID.fromString(s.value))
