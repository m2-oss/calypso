package ru.m2.calypso

import cats.instances.int._
import cats.instances.string._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.scalacheck.string._
import org.scalatest.funsuite.AnyFunSuiteLike
import org.typelevel.discipline.scalatest.Discipline
import ru.m2.calypso.MissingInstances._
import ru.m2.calypso.testing.KeyCodecTests

class KeyCodecTestsSpec extends AnyFunSuiteLike with Discipline {
  checkAll("KeyCodecTests[String]", KeyCodecTests[String].codec)
  checkAll("KeyCodecTests[Int]", KeyCodecTests[Int].codec)
  checkAll("KeyCodecTests[String Refined NonEmpty]", KeyCodecTests[String Refined NonEmpty].codec)
}
