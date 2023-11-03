package ru.m2.calypso.refined

import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import eu.timepit.refined.string.Uuid
import ru.m2.calypso.Decoder

import java.util.UUID

trait RefinedDecoder {

  implicit def decodeRefined[A: Decoder, P](implicit v: Validate[A, P]): Decoder[A Refined P] =
    Decoder[A].emap(refineV(_))

  implicit val decodeStringRefinedUuid: Decoder[String Refined Uuid] =
    Decoder[UUID].emap(uuid => refineV(uuid.toString))
}
