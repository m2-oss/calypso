package ru.m2.calypso

import org.bson.BsonDocument
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import ru.m2.calypso.syntax.*

class BsonDocumentSyntaxSuite extends AnyPropSpec with ScalaCheckDrivenPropertyChecks with Matchers:
  import BsonDocumentSyntaxSuite.*

  property("downField") {
    forAll(bsonDocumentGen) { case (k, v, doc) =>
      doc.downField(k).shouldBe(Bson.int32(v))
    }
  }

  property("downField empty") {
    forAll(Gen.alphaNumStr) { k =>
      Bson.obj().downField(k).shouldBe(Bson.nullValue)
    }
  }

object BsonDocumentSyntaxSuite:
  val bsonDocumentGen: Gen[(String, Int, BsonDocument)] =
    for {
      k <- Gen.alphaNumStr
      v <- arbitrary[Int]
    } yield (k, v, Bson.obj(k -> Bson.int32(v)))
