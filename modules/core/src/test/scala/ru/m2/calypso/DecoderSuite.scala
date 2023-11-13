package ru.m2.calypso

import cats.syntax.either.*
import munit.ScalaCheckSuite
import org.bson.{BsonInt32, BsonInt64, BsonString}
import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop.*
import ru.m2.calypso.syntax.*

class DecoderSuite extends ScalaCheckSuite:
  import DecoderSuite.{*, given}

  property("decode lhs") {
    forAll { (s: BsonString) =>
      assertEquals(decodeStringOrInt(s), s.as[String].map(_.asLeft))
    }
  }

  property("decode rhs") {
    forAll { (i32: BsonInt32) =>
      assertEquals(decodeStringOrInt(i32), i32.as[Int].map(_.asRight))
    }
  }

  property("decode failure") {
    forAll { (i64: BsonInt64) =>
      assertEquals(decodeStringOrInt(i64).isLeft, true)
    }
  }

object DecoderSuite:

  val decodeStringOrInt: Decoder[Either[String, Int]] =
    Decoder[String].map(_.asLeft).or(Decoder[Int].map(_.asRight))

  given Arbitrary[BsonString] = Arbitrary(arbitrary[String].map(Bson.string))
  given Arbitrary[BsonInt32]  = Arbitrary(arbitrary[Int].map(Bson.int32))
  given Arbitrary[BsonInt64]  = Arbitrary(arbitrary[Long].map(Bson.int64))
