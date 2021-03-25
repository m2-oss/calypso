package ru.m2.calypso

import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import ru.m2.calypso.syntax._

import scala.collection.immutable.SortedMap

class EncoderSuite extends AnyPropSpec with ScalaCheckDrivenPropertyChecks with Matchers {

  property("encodeMap preserve insertion order") {
    forAll { m: Map[Int, Long] =>
      val bson     = m.asBson
      val expected = Bson.of(m.toList.map { case (k, v) => k.asKey -> v.asBson })
      bson.shouldBe(expected)
    }
  }

  property("encodeSortedMap preserve insertion order") {
    forAll { m: SortedMap[Int, Long] =>
      val bson     = m.asBson
      val expected = Bson.of(m.toList.map { case (k, v) => k.asKey -> v.asBson })
      bson.shouldBe(expected)
    }
  }

}
