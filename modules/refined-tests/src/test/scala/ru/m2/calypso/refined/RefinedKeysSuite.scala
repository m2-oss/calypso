package ru.m2.calypso.refined

import eu.timepit.refined.scalacheck.string._
import eu.timepit.refined.types.string.NonEmptyString
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import ru.m2.calypso.refined.MissingInstances._
import ru.m2.calypso.testing.KeyCodecTests

class RefinedKeysSuite extends AnyFunSuiteLike with FunSuiteDiscipline with Configuration {
  checkAll("KeyCodec[NonEmptyString]", KeyCodecTests[NonEmptyString].codec)
}
