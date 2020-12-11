package ru.m2.calypso.testing

import cats.Eq
import cats.instances.either._
import cats.instances.string._
import cats.laws._
import cats.laws.discipline.catsLawsIsEqToProp
import org.scalacheck.{Arbitrary, Prop, Shrink}
import org.typelevel.discipline.Laws
import ru.m2.calypso.{KeyDecoder, KeyEncoder}

trait KeyCodecLaws[A] {
  def decode: KeyDecoder[A]
  def encode: KeyEncoder[A]

  final def codecRoundTrip(a: A): IsEq[Either[String, A]] =
    decode(encode(a)) <-> Right(a)
}

object KeyCodecLaws {
  def apply[A](implicit decodeA: KeyDecoder[A], encodeA: KeyEncoder[A]): KeyCodecLaws[A] =
    new KeyCodecLaws[A] {
      val decode: KeyDecoder[A] = decodeA
      val encode: KeyEncoder[A] = encodeA
    }
}

trait KeyCodecTests[A] extends Laws {
  def laws: KeyCodecLaws[A]

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

object KeyCodecTests {
  def apply[A: KeyDecoder: KeyEncoder]: KeyCodecTests[A] = new KeyCodecTests[A] {
    val laws: KeyCodecLaws[A] = KeyCodecLaws[A]
  }
}
