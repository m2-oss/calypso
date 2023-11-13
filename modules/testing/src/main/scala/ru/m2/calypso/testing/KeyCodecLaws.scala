package ru.m2.calypso.testing

import cats.Eq
import cats.laws.*
import cats.laws.discipline.catsLawsIsEqToProp
import org.scalacheck.{Arbitrary, Prop, Shrink}
import org.typelevel.discipline.Laws
import ru.m2.calypso.{KeyDecoder, KeyEncoder}

trait KeyCodecLaws[A]:

  def decode: KeyDecoder[A]

  def encode: KeyEncoder[A]

  final def codecRoundTrip(a: A): IsEq[Either[String, A]] =
    decode(encode(a)) <-> Right(a)

object KeyCodecLaws:

  def apply[A](using KeyDecoder[A], KeyEncoder[A]): KeyCodecLaws[A] =
    new KeyCodecLaws[A]:
      val decode: KeyDecoder[A] = KeyDecoder[A]
      val encode: KeyEncoder[A] = KeyEncoder[A]

trait KeyCodecTests[A] extends Laws:

  def laws: KeyCodecLaws[A]

  def codec(using Arbitrary[A], Shrink[A], Eq[A]): RuleSet =
    new DefaultRuleSet(
      name = "codec",
      parent = None,
      "roundTrip" -> Prop.forAll { (a: A) =>
        catsLawsIsEqToProp(laws.codecRoundTrip(a))
      }
    )

object KeyCodecTests:

  def apply[A: KeyDecoder: KeyEncoder]: KeyCodecTests[A] =
    new KeyCodecTests[A]:
      val laws: KeyCodecLaws[A] = KeyCodecLaws[A]
