package ru.m2.calypso

import cats.data.NonEmptyList
import cats.laws.discipline.arbitrary._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.scalacheck.string._
import eu.timepit.refined.string.Uuid
import org.scalatest.funsuite.AnyFunSuiteLike
import org.typelevel.discipline.scalatest.Discipline
import ru.m2.calypso.MissingInstances._
import ru.m2.calypso.testing.CodecTests

import java.time.Instant
import java.util.UUID

class CodecTestsSpec extends AnyFunSuiteLike with Discipline {
  checkAll("CodecTests[Boolean]", CodecTests[Boolean].codec)
  checkAll("CodecTests[Int]", CodecTests[Int].codec)
  checkAll("CodecTests[Long]", CodecTests[Long].codec)
  checkAll("CodecTests[String]", CodecTests[String].codec)
  checkAll("CodecTests[(Int, Long)]", CodecTests[(Int, Long)].codec)
  checkAll("CodecTests[NonEmptyList[Int]]", CodecTests[NonEmptyList[Int]].codec)
  checkAll("CodecTests[List[Int]]", CodecTests[List[Int]].codec)
  checkAll("CodecTests[Set[Int]]", CodecTests[Set[Int]].codec)
  checkAll("CodecTests[Map[Int, Long]]", CodecTests[Map[Int, Long]].codec)
  checkAll("CodecTests[Option[Int]]", CodecTests[Option[Int]].codec)
  checkAll("CodecTests[Either[String, Int]]", CodecTests[Either[String, Int]].codec)
  checkAll("CodecTests[Array[Byte]]", CodecTests[Array[Byte]].codec)
  checkAll("CodecTests[Instant]", CodecTests[Instant].codec)
  checkAll("CodecTests[UUID]", CodecTests[UUID].codec)
  checkAll("CodecTests[Unit]", CodecTests[Unit].codec)
  checkAll("CodecTests[String Refined NonEmpty]", CodecTests[String Refined NonEmpty].codec)
  checkAll("CodecTests[String Refined Uuid]", CodecTests[String Refined Uuid].codec)
}
