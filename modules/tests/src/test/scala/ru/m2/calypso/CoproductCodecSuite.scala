package ru.m2.calypso

import cats.Eq
import munit.DisciplineSuite
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import ru.m2.calypso.syntax.*
import ru.m2.calypso.testing.CodecTests

class CoproductCodecSuite extends DisciplineSuite:
  import CoproductCodecSuite.*
  checkAll("Codec[AorB]", CodecTests[AorB].codec)

object CoproductCodecSuite:

  import AorB.{A, B}

  enum AorB:
    case A(i: Int)
    case B(s: String)

  object AorB:
    given Eq[AorB] = Eq.fromUniversalEquals

    given Encoder[A]    = Encoder.forProduct1("i")(_.i)
    given Encoder[B]    = Encoder.forProduct1("s")(_.s)
    given Encoder[AorB] = Encoder.forCoproduct {
      case a: A => "A" -> a.asBson
      case b: B => "B" -> b.asBson
    }

    given Decoder[A]    = Decoder.forProduct1("i")(A.apply)
    given Decoder[B]    = Decoder.forProduct1("s")(B.apply)
    given Decoder[AorB] = Decoder.forCoproduct2[AorB, A, B]("A", "B")

  given Arbitrary[AorB] =
    Arbitrary(Gen.oneOf(arbitrary[Int].map(A.apply), arbitrary[String].map(B.apply)))
