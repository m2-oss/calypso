package ru.m2.calypso.scalapb

import cats.Eq
import com.google.protobuf.timestamp.Timestamp
import org.scalacheck.Gen.Choose.chooseInstant
import org.scalacheck.{Arbitrary, Gen}
import scalapb.{GeneratedEnum, GeneratedEnumCompanion, UnknownFieldSet}

import java.time.Instant

object MissingInstances:

  private val NanosPerMilli = 1000_000L

  private val MinTimestampDate = Instant.parse("0001-01-01T00:00:00Z")

  private val MaxTimestampDate = Instant.parse("9999-12-31T23:59:59Z")

  given [A <: GeneratedEnum]: Eq[A] = Eq.fromUniversalEquals

  given Eq[Timestamp] = Eq.fromUniversalEquals

  given Arbitrary[Timestamp] =
    Arbitrary(
      Gen
        .choose(MinTimestampDate, MaxTimestampDate)
        .map { instant =>
          val epoch = instant.getEpochSecond
          val nanos = (instant.getNano / NanosPerMilli * NanosPerMilli).toInt
          Timestamp(epoch, nanos)
        }
    )

  given Arbitrary[UnknownFieldSet] =
    Arbitrary(Gen.const(scalapb.UnknownFieldSet.empty))

  given [A <: GeneratedEnum](using GEC: GeneratedEnumCompanion[A]): Arbitrary[A] =
    Arbitrary(Gen.oneOf(GEC.values))
