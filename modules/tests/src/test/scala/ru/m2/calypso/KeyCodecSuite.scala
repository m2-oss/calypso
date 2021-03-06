package ru.m2.calypso

import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.scalacheck.string._
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import ru.m2.calypso.MissingInstances._
import ru.m2.calypso.testing.KeyCodecTests

class KeyCodecSuite extends AnyFunSuiteLike with FunSuiteDiscipline with Configuration {
  checkAll("KeyCodec[String]", KeyCodecTests[String].codec)
  checkAll("KeyCodec[Int]", KeyCodecTests[Int].codec)
  checkAll("KeyCodec[String Refined NonEmpty]", KeyCodecTests[String Refined NonEmpty].codec)
}
