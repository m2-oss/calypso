package ru.m2.calypso.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.scalacheck.string.*
import eu.timepit.refined.string.Uuid
import eu.timepit.refined.types.string.NonEmptyString
import munit.DisciplineSuite
import ru.m2.calypso.refined.MissingInstances.given
import ru.m2.calypso.testing.CodecTests

class RefinedSuite extends DisciplineSuite:
  checkAll("Codec[NonEmptyString]", CodecTests[NonEmptyString].codec)
  checkAll("Codec[String Refined Uuid]", CodecTests[String Refined Uuid].codec)
