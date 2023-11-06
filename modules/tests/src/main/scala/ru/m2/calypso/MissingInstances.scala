package ru.m2.calypso

import cats.Eq
import cats.syntax.eq._
import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary

import java.time.Instant

object MissingInstances {

  implicit val arbInstant: Arbitrary[Instant] = Arbitrary(
    arbitrary[Long].map(Instant.ofEpochMilli)
  )

  implicit val eqArrayByte: Eq[Array[Byte]] = Eq.instance { case (l, r) =>
    l.toList === r.toList
  }

  implicit val eqInstant: Eq[Instant] = Eq.fromUniversalEquals

  implicit val eqUnit: Eq[Unit] = Eq.fromUniversalEquals
}
