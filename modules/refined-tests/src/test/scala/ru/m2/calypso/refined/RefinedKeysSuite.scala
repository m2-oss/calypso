package ru.m2.calypso.refined

import eu.timepit.refined.scalacheck.string.*
import eu.timepit.refined.types.string.NonEmptyString
import munit.DisciplineSuite
import ru.m2.calypso.refined.MissingInstances.given
import ru.m2.calypso.testing.KeyCodecTests

class RefinedKeysSuite extends DisciplineSuite:
  checkAll("KeyCodec[NonEmptyString]", KeyCodecTests[NonEmptyString].codec)
