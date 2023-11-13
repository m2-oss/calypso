package ru.m2.calypso

import cats.Eq
import cats.syntax.eq.*
import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary

import java.time.Instant

object MissingInstances:
  given Arbitrary[Instant] = Arbitrary(arbitrary[Long].map(Instant.ofEpochMilli))
  given Eq[Array[Byte]]    = Eq.instance { case (l, r) => l.toList === r.toList }
  given Eq[Instant]        = Eq.fromUniversalEquals
  given Eq[Unit]           = Eq.fromUniversalEquals
