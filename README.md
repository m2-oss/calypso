# calypso

[![build](https://github.com/m2-oss/calypso/actions/workflows/release.yml/badge.svg)](https://github.com/m2-oss/calypso/actions)
[![Maven Central](https://img.shields.io/maven-central/v/ru.m2/calypso-core_3)](https://maven-badges.herokuapp.com/maven-central/ru.m2/calypso-core_3)

A BSON library based on `org.bson`. Encoder and Decoder type classes with instances for common types.

### Usage

To use calypso in an existing sbt project, add the following dependencies to your build.sbt:
```scala 3
libraryDependencies += "ru.m2" %% "calypso-core" % "<version>"
```
The most current release can be found in the maven badge at the top of this readme.

### Supported types

#### Scala
```scala 3
Unit
Boolean
Int
Long
Double
String
Array[Byte]
(A, B)
List[A]
Set[A]
SortedSet[A]
Map[K, V]
SortedMap[K, V]
Option[A]
Either[A, B]
```

#### java
```scala 3
java.time.Instant
java.util.UUID
```

#### cats
```scala 3
cats.data.NonEmptyList
```

### Product type (case class)
It is possible to construct codecs for [product types](https://en.wikipedia.org/wiki/Product_type) (case classes) using `forProductN` constructors if you have codecs for each of its elements.
```scala 3
import org.bson.BsonValue
import ru.m2.calypso.syntax.*
import ru.m2.calypso.{Decoder, Encoder}

case class Record(id: Int, name: String)
object Record:
  given Encoder[Record] = Encoder.forProduct2("id", "name")(r => (r.id, r.name))
  given Decoder[Record] = Decoder.forProduct2("id", "name")(Record.apply)

val bson: BsonValue = Record(1, "John").asBson // {"id": 1, "name": "John"}
val record: Either[String, Record] = bson.as[Record] // Right(Record(1,John))
```

### Coproduct type (sealed trait hierarchy)
Coproduct is also known as [ADT](https://en.wikipedia.org/wiki/Algebraic_data_type), sum, or [tagged union](https://en.wikipedia.org/wiki/Tagged_union). Not as ergonomic as product type, but it is possible to create codecs for coproduct types using `forCoproductN` constructors.
```scala 3
import org.bson.BsonValue
import ru.m2.calypso.syntax.*
import ru.m2.calypso.{Decoder, Encoder}

enum AorB:
  case A(i: Int)
  case B(s: String)

object AorB:

  import AorB.*
  
  given Encoder[A] = Encoder.forProduct1("i")(_.i)
  given Encoder[B] = Encoder.forProduct1("s")(_.s)
  given Encoder[AorB] = Encoder.forCoproduct {
    case a: A => "A" -> a.asBson
    case b: B => "B" -> b.asBson
  }
  
  given Decoder[A]    = Decoder.forProduct1("i")(A.apply)
  given Decoder[B]    = Decoder.forProduct1("s")(B.apply)
  given Decoder[AorB] = Decoder.forCoproduct2[AorB, A, B]("A", "B")

object Example:

  import AorB.*

  val aBson: BsonValue = (A(42): AorB).asBson      // {"tag": "A", "value": {"i": 42}}
  val bBson: BsonValue = (B("hello"): AorB).asBson // {"tag": "B", "value": {"s": "hello"}}
  
  val a: Either[String, AorB] = aBson.as[AorB] // Right(A(42))
  val b: Either[String, AorB] = bBson.as[AorB] // Right(B(hello))


```

### Derive codecs

Use existing codecs to derive new ones.
```scala 3
import org.bson.BsonValue
import ru.m2.calypso.syntax.*
import ru.m2.calypso.{Decoder, Encoder}

import java.time.{Instant, LocalDateTime, ZoneOffset}

given Encoder[LocalDateTime] = Encoder[Instant].contramap(_.toInstant(ZoneOffset.UTC))
given Decoder[LocalDateTime] = Decoder[Instant].map(LocalDateTime.ofInstant(_, ZoneOffset.UTC))

val bson: BsonValue = LocalDateTime.of(1970, 1, 1, 0, 0, 0).asBson // BsonDateTime{value=0}
val time: Either[String, LocalDateTime] = bson.as[LocalDateTime] // Right(1970-01-01T00:00)
```

### Refined
Codecs for [refined](https://github.com/fthomas/refined) types are derived, so if you have `Encoder[A]`, then you have `Encoder[A Refined P]` (where `P` is a predicate) for free. The same for decoders, so having `Decoder[A]` in implicit scope automatically gives you `Decoder[A Refined P]`.

To use calypso-refined in an existing sbt project, add the following dependencies to your build.sbt:
```scala 3
libraryDependencies += "ru.m2" %% "calypso-refined" % "<version>"
```
```scala 3
import eu.timepit.refined.types.string.NonEmptyString
import ru.m2.calypso.refined.*
import ru.m2.calypso.syntax.*

NonEmptyString("Text").asBson // BsonString{value='Text'}
```

### Why?
Passion for going with Java MongoDB Driver in a type-safe manner.
* `MongoDB Scala Driver` are wrappers around `org.bson` without advantages.
* `Reactive Scala Driver for MongoDB` can not be used without shenanigans with Java MongoDB Driver, as well as it
  does not offer reasonable API to encode/decode case classes.
* `MongoLess`, `shapeless-reactivemongo`, `Pure BSON` and `medeia` are based on shapeless, so they are refactoring blind
  and not a safe way to express persistence schema.
* `circe-bson` use JSON subset of BSON which is a no go for binary data.

### Design
```scala 3
opaque type Encoder[A] = A => org.bson.BsonValue
opaque type Decoder[A] = org.bson.BsonValue => Either[String, A]

opaque type KeyEncoder[A] = A => String
opaque type KeyDecoder[A] = String => Either[String, A]
```
This type classes allows to map Scala types to BSON and back. Key codecs are essential to preserving Map keys.
Library is heavily inspired by [circe](https://circe.github.io/circe/) and [argonaut](http://argonaut.io).

* Map keys are encoded as strings
* Tuple (A, B) is encoded as object {"_1": A, "_2": B}
* String Refined Uuid is encoded as native binary BsonUuid
* Instant is encoded as DateTime (epoch millis)

On optional values: object keys with null values and non-existing object keys are semantically equal.

### Law testing
Calypso type classes come with laws. Encoder and Decoder instances should hold CodecLaws. Calypso uses discipline to define type class laws and the ScalaCheck tests based on them.

First, you will need to specify dependencies on `calypso-testing` in your `build.sbt` file.
```scala 3
libraryDependencies ++= List(
  "ru.m2"         %% "calypso-core"         % "<version>",
  "ru.m2"         %% "calypso-testing"      % "<version>" % "test",
  "org.typelevel" %% "discipline-scalatest" % "2.2.0"     % "test"
)
```

We’ll begin by creating an `Eq` instance for UserId data type, as laws will need to compare values.

```scala 3
import cats.Eq
import ru.m2.calypso.{Decoder, Encoder}

opaque type UserId = Long
object UserId:
  def apply(id: Long): UserId = id

  given Encoder[UserId] = Encoder.given_Encoder_Long
  given Decoder[UserId] = Decoder.given_Decoder_Long
  given Eq[UserId]      = Eq.fromUniversalEquals

```

ScalaCheck requires Arbitrary instances for data types being tested. The following example is for ScalaTest.

```scala 3
import io.testing.CodecSuite.given
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.prop.Configuration
import org.typelevel.discipline.scalatest.FunSuiteDiscipline
import ru.m2.calypso.testing.CodecTests

class CodecSuite extends AnyFunSuiteLike with FunSuiteDiscipline with Configuration:
  checkAll("Codec[UserId]", CodecTests[UserId].codec)

object CodecSuite:
  given Arbitrary[UserId] = Arbitrary(Gen.long.map(UserId.apply))

```

Now when we run test in sbt console, ScalaCheck will test if the Codec laws hold for our UserId type. You should see something like this:
```
[info] CodecSuite:
[info] - Codec[UserId].codec.roundTrip
[info] Run completed in 289 milliseconds.
[info] Total number of tests run: 1
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 1, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 1 s, completed Nov 10, 2023 3:08:44 AM
```
You are gorgeous — the data type upholds the Codec laws!
