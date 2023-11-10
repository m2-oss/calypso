package ru.m2.calypso

import munit.ScalaCheckSuite
import org.bson.BsonValue
import org.scalacheck.Prop.*
import ru.m2.calypso.syntax.*

import scala.collection.immutable.SortedMap

class EncoderSuite extends ScalaCheckSuite:

  property("encodeMap preserve insertion order") {
    forAll { (m: Map[Int, Long]) =>
      val expected = Bson.of(
        m.foldLeft(List.empty[(String, BsonValue)]) { case (xs, (k, v)) =>
          (k.asKey, v.asBson) :: xs
        }.reverse
      )
      assertEquals(m.asBson, expected)
    }
  }

  property("encodeSortedMap preserve insertion order") {
    forAll { (m: SortedMap[Int, Long]) =>
      val expected = Bson.of(
        m.foldLeft(List.empty[(String, BsonValue)]) { case (xs, (k, v)) =>
          (k.asKey, v.asBson) :: xs
        }.reverse
      )
      assertEquals(m.asBson, expected)
    }
  }
