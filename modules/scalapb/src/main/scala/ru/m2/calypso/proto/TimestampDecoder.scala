package ru.m2.calypso.proto

import cats.syntax.either._
import com.google.protobuf.timestamp.Timestamp
import ru.m2.calypso.Decoder

import java.time.Instant

object TimestampDecoder {
  implicit val decodeTimestamp: Decoder[Timestamp] =
    Decoder[Instant].emap { instant =>
      Timestamp(instant.getEpochSecond, instant.getNano).asRight
    }
}
