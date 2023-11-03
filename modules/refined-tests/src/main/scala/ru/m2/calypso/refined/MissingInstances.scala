package ru.m2.calypso.refined

import cats.Eq
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Uuid
import eu.timepit.refined.types.string.NonEmptyString

object MissingInstances {

  implicit val eqNonEmptyString: Eq[NonEmptyString] = Eq.fromUniversalEquals

  implicit val eqStringRefinedUuid: Eq[String Refined Uuid] = Eq.fromUniversalEquals
}
