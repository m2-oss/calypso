package ru.m2.calypso

import cats.Eq
import cats.instances.byte._
import cats.instances.list._
import cats.syntax.eq._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.Uuid
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}

import java.time.Instant

object MissingInstances {

  implicit val arbInstant: Arbitrary[Instant] = Arbitrary(
    arbitrary[Long].map(Instant.ofEpochMilli)
  )

  implicit val arbStringRefinedUuid: Arbitrary[String Refined Uuid] = Arbitrary(
    Gen.uuid.map(v => Refined.unsafeApply[String, Uuid](v.toString))
  )

  implicit val eqArrayByte: Eq[Array[Byte]] = Eq.instance { case (l, r) =>
    l.toList === r.toList
  }

  implicit val eqInstant: Eq[Instant] = Eq.fromUniversalEquals

  implicit val eqUnit: Eq[Unit] = Eq.fromUniversalEquals

  implicit val eqStringRefinedNonEmpty: Eq[String Refined NonEmpty] = Eq.fromUniversalEquals

  implicit val eqStringRefinedUuid: Eq[String Refined Uuid] = Eq.fromUniversalEquals

}
