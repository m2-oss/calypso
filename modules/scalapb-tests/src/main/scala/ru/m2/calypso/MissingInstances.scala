package ru.m2.calypso

import cats.Eq
import cats.syntax.eq._
import com.google.protobuf.timestamp.Timestamp
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.string.Uuid
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.{Arbitrary, Gen}
import scalapb.GeneratedEnum
import scalapb.GeneratedEnumCompanion
import scalapb.UnknownFieldSet

import java.time.Instant

object MissingInstances {

  implicit def eqGeneratedEnum[A <: GeneratedEnum]: Eq[A] = Eq.fromUniversalEquals

  implicit val eqTimestamp: Eq[Timestamp] = Eq.fromUniversalEquals

  implicit val arbTimestamp: Arbitrary[Timestamp] =
    Arbitrary(
      arbitrary[Instant].map { instant =>
        val epoch = instant.getEpochSecond
        Timestamp(epoch, instant.getNano)
      }
    )

  implicit val arbUnknownFieldSet: Arbitrary[UnknownFieldSet] =
    Arbitrary(Gen.const(scalapb.UnknownFieldSet.empty))

  implicit def arbGeneratedEnum[A <: GeneratedEnum: GeneratedEnumCompanion]: Arbitrary[A] =
    Arbitrary(
      Gen.oneOf(implicitly[GeneratedEnumCompanion[A]].values)
    )

}
