package ru.m2.calypso

import org.bson.BsonValue
import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import ru.m2.calypso.syntax.*

import scala.collection.immutable.SortedMap

class EncoderSuite extends AnyPropSpec with ScalaCheckDrivenPropertyChecks with Matchers:

  property("encodeMap preserve insertion order") {
    forAll { (m: Map[Int, Long]) =>
      val expected = Bson.of(
        m.foldLeft(List.empty[(String, BsonValue)]) { case (xs, (k, v)) =>
          (k.asKey, v.asBson) :: xs
        }.reverse
      )
      m.asBson.shouldBe(expected)
    }
  }

  property("encodeSortedMap preserve insertion order") {
    forAll { (m: SortedMap[Int, Long]) =>
      val expected = Bson.of(
        m.foldLeft(List.empty[(String, BsonValue)]) { case (xs, (k, v)) =>
          (k.asKey, v.asBson) :: xs
        }.reverse
      )
      m.asBson.shouldBe(expected)
    }
  }
