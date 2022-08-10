package ru.m2.calypso

import cats.syntax.either._
import org.bson.{BsonInt32, BsonInt64, BsonString}
import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary
import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import ru.m2.calypso.syntax._

class DecoderSuite extends AnyPropSpec with ScalaCheckDrivenPropertyChecks with Matchers {

  import DecoderSuite._

  property("decode lhs") {
    forAll { (s: BsonString) =>
      decodeStringOrInt(s).shouldBe(s.as[String].map(_.asLeft))
    }
  }

  property("decode rhs") {
    forAll { (i32: BsonInt32) =>
      decodeStringOrInt(i32).shouldBe(i32.as[Int].map(_.asRight))
    }
  }

  property("decode failure") {
    forAll { (i64: BsonInt64) =>
      decodeStringOrInt(i64).isLeft.shouldBe(true)
    }
  }

}

object DecoderSuite {

  val decodeStringOrInt: Decoder[Either[String, Int]] =
    Decoder.decodeString.map(_.asLeft).or(Decoder.decodeInt.map(_.asRight))

  implicit val arbBsonString: Arbitrary[BsonString] = Arbitrary(arbitrary[String].map(Bson.string))
  implicit val arbBsonInt32: Arbitrary[BsonInt32]   = Arbitrary(arbitrary[Int].map(Bson.int32))
  implicit val arbBsonInt64: Arbitrary[BsonInt64]   = Arbitrary(arbitrary[Long].map(Bson.int64))

}
