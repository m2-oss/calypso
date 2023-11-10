package ru.m2.calypso.scalapb

import com.google.protobuf.timestamp.Timestamp
import ru.m2.calypso.Decoder

import java.time.Instant

object TimestampDecoder:

  given Decoder[Timestamp] =
    Decoder[Instant].map(t => Timestamp(t.getEpochSecond, t.getNano))
