package ru.m2.calypso.boilerplate

import ru.m2.calypso.Decoder
import ru.m2.calypso.syntax._

trait ProductDecoder {

  final def forProduct1[A, B: Decoder](nb: String)(f: B => A): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
      } yield f(b)
    }

  final def forProduct2[A, B: Decoder, C: Decoder](
      nb: String,
      nc: String
  )(
      f: (B, C) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
      } yield f(b, c)
    }

  final def forProduct3[A, B: Decoder, C: Decoder, D: Decoder](
      nb: String,
      nc: String,
      nd: String
  )(
      f: (B, C, D) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
        d   <- doc.downField(nd).as[D]
      } yield f(b, c, d)
    }

  final def forProduct4[A, B: Decoder, C: Decoder, D: Decoder, E: Decoder](
      nb: String,
      nc: String,
      nd: String,
      ne: String
  )(
      f: (B, C, D, E) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
        d   <- doc.downField(nd).as[D]
        e   <- doc.downField(ne).as[E]
      } yield f(b, c, d, e)
    }

  final def forProduct5[A, B: Decoder, C: Decoder, D: Decoder, E: Decoder, F: Decoder](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String
  )(
      f: (B, C, D, E, F) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
        d   <- doc.downField(nd).as[D]
        e   <- doc.downField(ne).as[E]
        ff  <- doc.downField(nf).as[F]
      } yield f(b, c, d, e, ff)
    }

  final def forProduct6[A, B: Decoder, C: Decoder, D: Decoder, E: Decoder, F: Decoder, G: Decoder](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String,
      ng: String
  )(
      f: (B, C, D, E, F, G) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
        d   <- doc.downField(nd).as[D]
        e   <- doc.downField(ne).as[E]
        ff  <- doc.downField(nf).as[F]
        g   <- doc.downField(ng).as[G]
      } yield f(b, c, d, e, ff, g)
    }

  final def forProduct7[
      A,
      B: Decoder,
      C: Decoder,
      D: Decoder,
      E: Decoder,
      F: Decoder,
      G: Decoder,
      H: Decoder
  ](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String,
      ng: String,
      nh: String
  )(
      f: (B, C, D, E, F, G, H) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
        d   <- doc.downField(nd).as[D]
        e   <- doc.downField(ne).as[E]
        ff  <- doc.downField(nf).as[F]
        g   <- doc.downField(ng).as[G]
        h   <- doc.downField(nh).as[H]
      } yield f(b, c, d, e, ff, g, h)
    }

  final def forProduct8[
      A,
      B: Decoder,
      C: Decoder,
      D: Decoder,
      E: Decoder,
      F: Decoder,
      G: Decoder,
      H: Decoder,
      I: Decoder
  ](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String,
      ng: String,
      nh: String,
      ni: String
  )(
      f: (B, C, D, E, F, G, H, I) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
        d   <- doc.downField(nd).as[D]
        e   <- doc.downField(ne).as[E]
        ff  <- doc.downField(nf).as[F]
        g   <- doc.downField(ng).as[G]
        h   <- doc.downField(nh).as[H]
        i   <- doc.downField(ni).as[I]
      } yield f(b, c, d, e, ff, g, h, i)
    }

  final def forProduct9[
      A,
      B: Decoder,
      C: Decoder,
      D: Decoder,
      E: Decoder,
      F: Decoder,
      G: Decoder,
      H: Decoder,
      I: Decoder,
      J: Decoder
  ](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String,
      ng: String,
      nh: String,
      ni: String,
      nj: String
  )(
      f: (B, C, D, E, F, G, H, I, J) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
        d   <- doc.downField(nd).as[D]
        e   <- doc.downField(ne).as[E]
        ff  <- doc.downField(nf).as[F]
        g   <- doc.downField(ng).as[G]
        h   <- doc.downField(nh).as[H]
        i   <- doc.downField(ni).as[I]
        j   <- doc.downField(nj).as[J]
      } yield f(b, c, d, e, ff, g, h, i, j)
    }

  final def forProduct10[
      A,
      B: Decoder,
      C: Decoder,
      D: Decoder,
      E: Decoder,
      F: Decoder,
      G: Decoder,
      H: Decoder,
      I: Decoder,
      J: Decoder,
      K: Decoder
  ](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String,
      ng: String,
      nh: String,
      ni: String,
      nj: String,
      nk: String
  )(
      f: (B, C, D, E, F, G, H, I, J, K) => A
  ): Decoder[A] =
    Decoder.instance { bson =>
      for {
        doc <- bson.focus
        b   <- doc.downField(nb).as[B]
        c   <- doc.downField(nc).as[C]
        d   <- doc.downField(nd).as[D]
        e   <- doc.downField(ne).as[E]
        ff  <- doc.downField(nf).as[F]
        g   <- doc.downField(ng).as[G]
        h   <- doc.downField(nh).as[H]
        i   <- doc.downField(ni).as[I]
        j   <- doc.downField(nj).as[J]
        k   <- doc.downField(nk).as[K]
      } yield f(b, c, d, e, ff, g, h, i, j, k)
    }

}
