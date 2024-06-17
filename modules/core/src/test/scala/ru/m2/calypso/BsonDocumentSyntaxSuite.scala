package ru.m2.calypso

import munit.ScalaCheckSuite
import org.bson.BsonDocument
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalacheck.Prop.*
import ru.m2.calypso.syntax.*

class BsonDocumentSyntaxSuite extends ScalaCheckSuite:
  import BsonDocumentSyntaxSuite.*

  property("downField") {
    forAll(bsonDocumentGen) { case (k, v, doc) =>
      assertEquals(doc.downField(k), Bson.int32(v))
    }
  }

  property("downField empty") {
    forAll(Gen.alphaNumStr) { k =>
      assertEquals(Bson.obj().downField(k), Bson.nullValue)
    }
  }

object BsonDocumentSyntaxSuite:
  val bsonDocumentGen: Gen[(String, Int, BsonDocument)] =
    for
      k <- Gen.alphaNumStr
      v <- arbitrary[Int]
    yield (k, v, Bson.obj(k -> Bson.int32(v)))
