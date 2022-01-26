package ru.m2.calypso.scalapb

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
import org.scalacheck.Gen.Choose.chooseInstant

import java.time.Instant

object MissingInstances {
  private val NanosPerMilli    = 1000_000L
  private val MinTimestampDate = Instant.parse("0001-01-01T00:00:00Z")
  private val MaxTimestampDate = Instant.parse("9999-12-31T23:59:59Z")

  implicit def eqGeneratedEnum[A <: GeneratedEnum]: Eq[A] = Eq.fromUniversalEquals

  implicit val eqTimestamp: Eq[Timestamp] = Eq.fromUniversalEquals

  implicit val arbTimestamp: Arbitrary[Timestamp] =
    Arbitrary(
      Gen
        .choose(MinTimestampDate, MaxTimestampDate)
        .map { instant =>
          val epoch = instant.getEpochSecond
          val nanos = (instant.getNano / NanosPerMilli * NanosPerMilli).toInt
          Timestamp(epoch, nanos)
        }
    )

  implicit val arbUnknownFieldSet: Arbitrary[UnknownFieldSet] =
    Arbitrary(Gen.const(scalapb.UnknownFieldSet.empty))

  implicit def arbGeneratedEnum[A <: GeneratedEnum: GeneratedEnumCompanion]: Arbitrary[A] =
    Arbitrary(
      Gen.oneOf(implicitly[GeneratedEnumCompanion[A]].values)
    )

}
