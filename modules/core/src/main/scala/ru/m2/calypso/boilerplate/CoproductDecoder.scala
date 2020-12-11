package ru.m2.calypso.boilerplate

import cats.syntax.either._
import ru.m2.calypso.Decoder
import ru.m2.calypso.syntax._

trait CoproductDecoder {

  final def forCoproduct1[A, B <: A: Decoder](tb: String): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct2[A, B <: A: Decoder, C <: A: Decoder](tb: String, tc: String): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct3[A, B <: A: Decoder, C <: A: Decoder, D <: A: Decoder](
      tb: String,
      tc: String,
      td: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct4[A, B <: A: Decoder, C <: A: Decoder, D <: A: Decoder, E <: A: Decoder](
      tb: String,
      tc: String,
      td: String,
      te: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct5[
      A,
      B <: A: Decoder,
      C <: A: Decoder,
      D <: A: Decoder,
      E <: A: Decoder,
      F <: A: Decoder
  ](
      tb: String,
      tc: String,
      td: String,
      te: String,
      tf: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case t if t == tf => doc.downField("value").as[F]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct6[
      A,
      B <: A: Decoder,
      C <: A: Decoder,
      D <: A: Decoder,
      E <: A: Decoder,
      F <: A: Decoder,
      G <: A: Decoder
  ](
      tb: String,
      tc: String,
      td: String,
      te: String,
      tf: String,
      tg: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case t if t == tf => doc.downField("value").as[F]
          case t if t == tg => doc.downField("value").as[G]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct7[
      A,
      B <: A: Decoder,
      C <: A: Decoder,
      D <: A: Decoder,
      E <: A: Decoder,
      F <: A: Decoder,
      G <: A: Decoder,
      H <: A: Decoder
  ](
      tb: String,
      tc: String,
      td: String,
      te: String,
      tf: String,
      tg: String,
      th: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case t if t == tf => doc.downField("value").as[F]
          case t if t == tg => doc.downField("value").as[G]
          case t if t == th => doc.downField("value").as[H]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct8[
      A,
      B <: A: Decoder,
      C <: A: Decoder,
      D <: A: Decoder,
      E <: A: Decoder,
      F <: A: Decoder,
      G <: A: Decoder,
      H <: A: Decoder,
      I <: A: Decoder
  ](
      tb: String,
      tc: String,
      td: String,
      te: String,
      tf: String,
      tg: String,
      th: String,
      ti: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case t if t == tf => doc.downField("value").as[F]
          case t if t == tg => doc.downField("value").as[G]
          case t if t == th => doc.downField("value").as[H]
          case t if t == ti => doc.downField("value").as[I]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct9[
      A,
      B <: A: Decoder,
      C <: A: Decoder,
      D <: A: Decoder,
      E <: A: Decoder,
      F <: A: Decoder,
      G <: A: Decoder,
      H <: A: Decoder,
      I <: A: Decoder,
      J <: A: Decoder
  ](
      tb: String,
      tc: String,
      td: String,
      te: String,
      tf: String,
      tg: String,
      th: String,
      ti: String,
      tj: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case t if t == tf => doc.downField("value").as[F]
          case t if t == tg => doc.downField("value").as[G]
          case t if t == th => doc.downField("value").as[H]
          case t if t == ti => doc.downField("value").as[I]
          case t if t == tj => doc.downField("value").as[J]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct10[
      A,
      B <: A: Decoder,
      C <: A: Decoder,
      D <: A: Decoder,
      E <: A: Decoder,
      F <: A: Decoder,
      G <: A: Decoder,
      H <: A: Decoder,
      I <: A: Decoder,
      J <: A: Decoder,
      K <: A: Decoder
  ](
      tb: String,
      tc: String,
      td: String,
      te: String,
      tf: String,
      tg: String,
      th: String,
      ti: String,
      tj: String,
      tk: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case t if t == tf => doc.downField("value").as[F]
          case t if t == tg => doc.downField("value").as[G]
          case t if t == th => doc.downField("value").as[H]
          case t if t == ti => doc.downField("value").as[I]
          case t if t == tj => doc.downField("value").as[J]
          case t if t == tk => doc.downField("value").as[K]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct11[
      A,
      B <: A: Decoder,
      C <: A: Decoder,
      D <: A: Decoder,
      E <: A: Decoder,
      F <: A: Decoder,
      G <: A: Decoder,
      H <: A: Decoder,
      I <: A: Decoder,
      J <: A: Decoder,
      K <: A: Decoder,
      L <: A: Decoder
  ](
      tb: String,
      tc: String,
      td: String,
      te: String,
      tf: String,
      tg: String,
      th: String,
      ti: String,
      tj: String,
      tk: String,
      tl: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case t if t == tf => doc.downField("value").as[F]
          case t if t == tg => doc.downField("value").as[G]
          case t if t == th => doc.downField("value").as[H]
          case t if t == ti => doc.downField("value").as[I]
          case t if t == tj => doc.downField("value").as[J]
          case t if t == tk => doc.downField("value").as[K]
          case t if t == tl => doc.downField("value").as[L]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a
    }

  final def forCoproduct12[
      A,
      B <: A: Decoder,
      C <: A: Decoder,
      D <: A: Decoder,
      E <: A: Decoder,
      F <: A: Decoder,
      G <: A: Decoder,
      H <: A: Decoder,
      I <: A: Decoder,
      J <: A: Decoder,
      K <: A: Decoder,
      L <: A: Decoder,
      M <: A: Decoder
  ](
      tb: String,
      tc: String,
      td: String,
      te: String,
      tf: String,
      tg: String,
      th: String,
      ti: String,
      tj: String,
      tk: String,
      tl: String,
      tm: String
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        tag <- doc.downField("tag").as[String]
        a <- tag match {
          case t if t == tb => doc.downField("value").as[B]
          case t if t == tc => doc.downField("value").as[C]
          case t if t == td => doc.downField("value").as[D]
          case t if t == te => doc.downField("value").as[E]
          case t if t == tf => doc.downField("value").as[F]
          case t if t == tg => doc.downField("value").as[G]
          case t if t == th => doc.downField("value").as[H]
          case t if t == ti => doc.downField("value").as[I]
          case t if t == tj => doc.downField("value").as[J]
          case t if t == tk => doc.downField("value").as[K]
          case t if t == tl => doc.downField("value").as[L]
          case t if t == tm => doc.downField("value").as[M]
          case _            => s"Tag.unknown: [$tag]".asLeft
        }
      } yield a

    }
}
