package ru.m2.calypso.scalapb

import com.google.protobuf.`type`.Field.Cardinality
import com.google.protobuf.timestamp.Timestamp
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import ru.m2.calypso.scalapb.MissingInstances.given
import ru.m2.calypso.scalapb.GeneratedEnumDecoder.given
import ru.m2.calypso.scalapb.GeneratedEnumEncoder.given
import ru.m2.calypso.scalapb.TimestampDecoder.given
import ru.m2.calypso.scalapb.TimestampEncoder.given
import ru.m2.calypso.testing.CodecTests

class CodecTestsSpec extends AnyFunSuiteLike with FunSuiteDiscipline with Configuration {
  checkAll("Codec[Cardinality]", CodecTests[Cardinality].codec)
  checkAll("Codec[Timestamp]", CodecTests[Timestamp].codec)
}
