package ru.m2.calypso

import cats.data.NonEmptyList
import cats.laws.discipline.arbitrary.*
import munit.DisciplineSuite
import ru.m2.calypso.MissingInstances.given
import ru.m2.calypso.testing.CodecTests

import java.time.Instant
import java.util.UUID
import scala.collection.immutable.{SortedMap, SortedSet}

class CodecSuite extends DisciplineSuite:
  checkAll("Codec[Unit]", CodecTests[Unit].codec)
  checkAll("Codec[Boolean]", CodecTests[Boolean].codec)
  checkAll("Codec[Int]", CodecTests[Int].codec)
  checkAll("Codec[Long]", CodecTests[Long].codec)
  checkAll("Codec[String]", CodecTests[String].codec)
  checkAll("Codec[(Int, Long)]", CodecTests[(Int, Long)].codec)
  checkAll("Codec[NonEmptyList[Int]]", CodecTests[NonEmptyList[Int]].codec)
  checkAll("Codec[List[Int]]", CodecTests[List[Int]].codec)
  checkAll("Codec[Set[Int]]", CodecTests[Set[Int]].codec)
  checkAll("Codec[SortedSet[Int]]", CodecTests[SortedSet[Int]].codec)
  checkAll("Codec[Map[Int, Long]]", CodecTests[Map[Int, Long]].codec)
  checkAll("Codec[SortedMap[Int, Long]]", CodecTests[SortedMap[Int, Long]].codec)
  checkAll("Codec[Option[Int]]", CodecTests[Option[Int]].codec)
  checkAll("Codec[Either[String, Int]]", CodecTests[Either[String, Int]].codec)
  checkAll("Codec[Array[Byte]]", CodecTests[Array[Byte]].codec)
  checkAll("Codec[Instant]", CodecTests[Instant].codec)
  checkAll("Codec[UUID]", CodecTests[UUID].codec)
