# calypso

[![build](https://github.com/m2-oss/calypso/workflows/build/badge.svg)](https://github.com/m2-oss/calypso/actions)
[![Maven Central](https://img.shields.io/maven-central/v/ru.m2/calypso-core_2.13)](https://maven-badges.herokuapp.com/maven-central/ru.m2/calypso-core_2.13)

A BSON library based on `org.bson`. Encoder and Decoder type classes with instances for common types.

### Usage

To use calypso in an existing sbt project, add the following dependencies to your build.sbt:
```scala
libraryDependencies += "ru.m2" %% "calypso-core" % "<version>"
```
The most current release can be found in the maven badge at the top of this readme.

### Supported types

#### Scala
```
Boolean
Int
Long
Double
String
Array[Byte]
(A, B)
List[A]
Set[A]
SortedSet[K, V]
Map[K, V]
SortedMap[K, V]
Option[A]
Either[A, B]
Unit
```

#### java
```
java.time.Instant
java.util.UUID
```

#### cats
```
cats.data.NonEmptyList
```

### Refined
Codecs for [refined](https://github.com/fthomas/refined) types are derived, so if you have `Encoder[A]`, then you have `Encoder[A Refined P]` (where `P` is a predicate) for free. The same for decoders, so having `Decoder[A]` in implicit scope automatically gives you `Decoder[A Refined P]`.

### Product type (case class)
It is possible to construct codecs for [product types](https://en.wikipedia.org/wiki/Product_type) (case classes) using `forProductN` helper methods if you have codecs for each of its elements.
```
import org.bson.BsonValue
import ru.m2.calypso.syntax._
import ru.m2.calypso.{Decoder, Encoder}

final case class Record(id: Int, name: String)
object Record {
  implicit val encodeRecord: Encoder[Record] =
    Encoder.forProduct2("id", "name")(r => (r.id, r.name))

  implicit val decodeRecord: Decoder[Record] =
    Decoder.forProduct2("id", "name")(Record.apply)
}

val bson: BsonValue = Record(1, "John").asBson // {"id": 1, "name": "John"}

val record: Either[String, Record] = bson.as[Record] // Right(Record(1,John))
```

### Coproduct type (sealed trait hierarchy)
Coproduct is also known as [ADT](https://en.wikipedia.org/wiki/Algebraic_data_type), sum, or [tagged union](https://en.wikipedia.org/wiki/Tagged_union). Not as ergonomic as product type, but it is possible to create codecs for coproduct types using `forCoproductN` helper methods.
```
import org.bson.BsonValue
import ru.m2.calypso.syntax._
import ru.m2.calypso.{Decoder, Encoder}

sealed trait AorB extends Product with Serializable
object AorB {
  final case class A(i: Int)    extends AorB
  final case class B(s: String) extends AorB
}

import AorB._

implicit val encodeA: Encoder[A] = Encoder.forProduct1("i")(_.i)
implicit val encodeB: Encoder[B] = Encoder.forProduct1("s")(_.s)
implicit val encodeAorB: Encoder[AorB] = Encoder.forCoproduct {
  case a: A => "A" -> a.asBson
  case b: B => "B" -> b.asBson
}

implicit val decodeA: Decoder[A]       = Decoder.forProduct1("i")(A.apply)
implicit val decodeB: Decoder[B]       = Decoder.forProduct1("s")(B.apply)
implicit val decodeAorB: Decoder[AorB] = Decoder.forCoproduct2[AorB, A, B]("A", "B")

val aBson: BsonValue = (A(42): AorB).asBson      // {"tag": "A", "value": {"i": 42}}
val bBson: BsonValue = (B("hello"): AorB).asBson // {"tag": "B", "value": {"s": "hello"}}

val a: Either[String, AorB] = aBson.as[AorB] // Right(A(42))
val b: Either[String, AorB] = bBson.as[AorB] // Right(B(hello))
```

### Derive codecs

Use existing codecs to derive complex ones.
```
import org.bson.BsonValue
import ru.m2.calypso.syntax._
import ru.m2.calypso.{Decoder, Encoder}

final case class UserId(value: Long) extends AnyVal
object UserId {
  implicit val encodeUserId: Encoder[UserId] =
    Encoder.encodeLong.contramap(_.value)

  implicit val decodeUserId: Decoder[UserId] =
    Decoder.decodeLong.map(UserId.apply)
}

val bson: BsonValue = UserId(42).asBson // BsonInt64{value=42}

val userId: Either[String, UserId] = bson.as[UserId] // Right(UserId(42))
```


### Why?
Passion for going with Java MongoDB driver in a type-safe manner.
* `MongoDB Scala Driver` are wrappers around `org.bson` without advantages.
* `Reactive Scala Driver for MongoDB` can not be used without shenanigans with Java MongoDB driver, as well as it
  does not offer reasonable API to encode/decode case classes.
* `MongoLess`, `shapeless-reactivemongo`, and `Pure BSON` are based on shapeless, so they are refactoring blind
  and not a safe way to express persistence schema.
* `circe-bson` use JSON subset of BSON which is a no go for binary data.

### Design
```
Encoder[A]: A => org.bson.BsonValue
Decoder[A]: org.bson.BsonValue => Either[String, A]

KeyEncoder[A]: A => String
KeyDecoder[A]: String => Either[String, A]
```
This type classes allows to map Scala types to BSON and back. Key codecs are essential to preserving Map keys.
Library is heavily inspired by [circe](https://circe.github.io/circe/) and [argonaut](http://argonaut.io).

* Map keys are encoded as strings
* Tuple (A, B) is encoded as object {"_1": A, "_2": B}
* String Refined Uuid is encoded as native binary BsonUuid
* Instant is encoded as DateTime (epoch millis)

On optional values: object keys with null values and non-existing object keys are semantically equal.

### Test
Calypso type classes come with laws. Encoder and Decoder instances should hold CodecLaws.
```
sbt test
```
