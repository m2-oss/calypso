package ru.m2.calypso.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uuid
import org.bson.BsonBinary
import ru.m2.calypso.Encoder

import java.util.UUID

trait RefinedEncoder {

  implicit def encodeRefined[A: Encoder, P]: Encoder[A Refined P] =
    Encoder[A].contramap(_.value)

  implicit val encodeStringRefinedUuid: Encoder[String Refined Uuid] =
    Encoder.instance(s => new BsonBinary(UUID.fromString(s.value)))
}
