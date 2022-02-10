package ru.m2.calypso.scalapb

import java.time.Instant

import com.google.protobuf.timestamp.Timestamp
import ru.m2.calypso.Encoder

object TimestampEncoder {
  implicit val encodeTimestamp: Encoder[Timestamp] =
    Encoder[Instant]
      .contramap(t => Instant.ofEpochSecond(t.seconds, t.nanos.toLong))
}
