package ru.m2.calypso

import munit.DisciplineSuite
import ru.m2.calypso.testing.KeyCodecTests

class KeyCodecSuite extends DisciplineSuite:
  checkAll("KeyCodec[String]", KeyCodecTests[String].codec)
  checkAll("KeyCodec[Int]", KeyCodecTests[Int].codec)
