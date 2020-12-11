package ru.m2.calypso

import org.scalatest.matchers.should.Matchers
import org.scalatest.propspec.AnyPropSpec
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class BsonSpec extends AnyPropSpec with ScalaCheckDrivenPropertyChecks with Matchers {

  property("Bson.obj skips null value") {
    forAll { k: String =>
      Bson.obj(k -> Bson.nullValue).shouldBe(Bson.empty)
    }
  }

}
