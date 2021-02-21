package ru.m2.calypso.testing

import cats.Eq
import cats.laws._
import cats.laws.discipline.catsLawsIsEqToProp
import org.scalacheck.{Arbitrary, Prop, Shrink}
import org.typelevel.discipline.Laws
import ru.m2.calypso.{Decoder, Encoder}

trait CodecLaws[A] {
  def decode: Decoder[A]
  def encode: Encoder[A]

  final def codecRoundTrip(a: A): IsEq[Either[String, A]] =
    decode(encode(a)) <-> Right(a)
}

object CodecLaws {
  def apply[A](implicit decodeA: Decoder[A], encodeA: Encoder[A]): CodecLaws[A] = new CodecLaws[A] {
    val decode: Decoder[A] = decodeA
    val encode: Encoder[A] = encodeA
  }
}

trait CodecTests[A] extends Laws {
  def laws: CodecLaws[A]

  def codec(implicit
      arbitraryA: Arbitrary[A],
      shrinkA: Shrink[A],
      eqA: Eq[A]
  ): RuleSet = new DefaultRuleSet(
    name = "codec",
    parent = None,
    "roundTrip" -> Prop.forAll { a: A =>
      catsLawsIsEqToProp(laws.codecRoundTrip(a))
    }
  )

}

object CodecTests {
  def apply[A: Decoder: Encoder]: CodecTests[A] = new CodecTests[A] {
    val laws: CodecLaws[A] = CodecLaws[A]
  }
}
