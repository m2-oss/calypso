package ru.m2.calypso

import cats.data.NonEmptyList
import cats.laws.discipline.arbitrary._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.scalacheck.string._
import eu.timepit.refined.string.Uuid
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import ru.m2.calypso.MissingInstances._
import ru.m2.calypso.testing.CodecTests

import java.time.Instant
import java.util.UUID
import scala.collection.immutable.{SortedMap, SortedSet}

class CodecSuite extends AnyFunSuiteLike with FunSuiteDiscipline with Configuration {
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
  checkAll("Codec[String Refined NonEmpty]", CodecTests[String Refined NonEmpty].codec)
  checkAll("Codec[String Refined Uuid]", CodecTests[String Refined Uuid].codec)
}
