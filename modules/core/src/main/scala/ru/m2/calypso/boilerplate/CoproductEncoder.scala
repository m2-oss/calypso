package ru.m2.calypso.boilerplate

import org.bson.BsonValue
import ru.m2.calypso.Encoder

trait CoproductEncoder {

  final def forCoproduct[A](f: A => (String, BsonValue)): Encoder[A] =
    Encoder.forProduct2("tag", "value")(f)

}
