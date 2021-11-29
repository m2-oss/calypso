package ru.m2.calypso.proto

import cats.syntax.either._
import com.google.protobuf.timestamp.Timestamp
import ru.m2.calypso.Decoder

object TimestampDecoder {
  implicit val decodeTimestamp: Decoder[Timestamp] =
    Decoder.decodeInstant.emap { instant =>
      val epoch = instant.getEpochSecond
      Timestamp(epoch, instant.getNano).asRight
    }
}
