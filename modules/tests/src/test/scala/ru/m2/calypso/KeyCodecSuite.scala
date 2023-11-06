package ru.m2.calypso

import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import ru.m2.calypso.testing.KeyCodecTests

class KeyCodecSuite extends AnyFunSuiteLike with FunSuiteDiscipline with Configuration {
  checkAll("KeyCodec[String]", KeyCodecTests[String].codec)
  checkAll("KeyCodec[Int]", KeyCodecTests[Int].codec)
}
