package ru.m2.calypso.scalapb

import cats.syntax.either._
import com.google.protobuf.timestamp.Timestamp
import ru.m2.calypso.Decoder

import java.time.Instant

object TimestampDecoder:

  given Decoder[Timestamp] =
    Decoder[Instant].map { instant => Timestamp(instant.getEpochSecond, instant.getNano) }
