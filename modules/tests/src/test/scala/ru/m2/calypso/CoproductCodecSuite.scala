package ru.m2.calypso

import cats.Eq
import org.scalacheck.ScalacheckShapeless._
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import ru.m2.calypso.syntax._
import ru.m2.calypso.testing.CodecTests

class CoproductCodecSuite extends AnyFunSuiteLike with FunSuiteDiscipline with Configuration {
  import CoproductCodecSuite._

  checkAll("Codec[AorB]", CodecTests[AorB].codec)
}

object CoproductCodecSuite {

  import AorB.{A, B}

  sealed trait AorB
  object AorB {
    final case class A(i: Int)    extends AorB
    final case class B(s: String) extends AorB
  }

  implicit val eqAorB: Eq[AorB] = Eq.fromUniversalEquals

  implicit val encodeA: Encoder[A] = Encoder.forProduct1("i")(_.i)
  implicit val encodeB: Encoder[B] = Encoder.forProduct1("s")(_.s)
  implicit val encodeAorB: Encoder[AorB] = Encoder.forCoproduct {
    case a: A => "A" -> a.asBson
    case b: B => "B" -> b.asBson
  }

  implicit val decodeA: Decoder[A]       = Decoder.forProduct1("i")(A.apply)
  implicit val decodeB: Decoder[B]       = Decoder.forProduct1("s")(B.apply)
  implicit val decodeAorB: Decoder[AorB] = Decoder.forCoproduct2[AorB, A, B]("A", "B")

}
