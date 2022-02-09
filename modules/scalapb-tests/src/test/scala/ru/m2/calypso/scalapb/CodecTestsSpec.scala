package ru.m2.calypso.scalapb

import com.google.protobuf.`type`.Field.Cardinality
import com.google.protobuf.timestamp.Timestamp
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import ru.m2.calypso.scalapb.MissingInstances._
import ru.m2.calypso.scalapb.GeneratedEnumDecoder._
import ru.m2.calypso.scalapb.GeneratedEnumEncoder._
import ru.m2.calypso.scalapb.TimestampDecoder._
import ru.m2.calypso.scalapb.TimestampEncoder._
import ru.m2.calypso.testing.CodecTests

class CodecTestsSpec extends AnyFunSuiteLike with FunSuiteDiscipline with Configuration {
  checkAll("Codec[Cardinality]", CodecTests[Cardinality].codec)
  checkAll("Codec[Timestamp]", CodecTests[Timestamp].codec)
}
