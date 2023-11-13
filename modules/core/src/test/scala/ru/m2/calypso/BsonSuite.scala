package ru.m2.calypso

import munit.ScalaCheckSuite
import org.scalacheck.Prop.*

class BsonSuite extends ScalaCheckSuite:
  property("Bson.obj skips null value") {
    forAll { (k: String) =>
      assertEquals(Bson.obj(k -> Bson.nullValue), Bson.empty)
    }
  }
