package ru.m2.calypso.boilerplate

import ru.m2.calypso.syntax._
import ru.m2.calypso.{Bson, Encoder}

trait ProductEncoder {

  final def forProduct1[A, B: Encoder](nb: String)(f: A => B): Encoder[A] =
    Encoder.instance[A] { a =>
      Bson.obj(nb -> f(a).asBson)
    }

  final def forProduct2[A, B: Encoder, C: Encoder](
      nb: String,
      nc: String
  )(
      f: A => (B, C)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c) = f(a)
      Bson.obj(nb -> b.asBson, nc -> c.asBson)
    }

  final def forProduct3[A, B: Encoder, C: Encoder, D: Encoder](
      nb: String,
      nc: String,
      nd: String
  )(
      f: A => (B, C, D)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c, d) = f(a)
      Bson.obj(nb -> b.asBson, nc -> c.asBson, nd -> d.asBson)
    }

  final def forProduct4[A, B: Encoder, C: Encoder, D: Encoder, E: Encoder](
      nb: String,
      nc: String,
      nd: String,
      ne: String
  )(
      f: A => (B, C, D, E)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c, d, e) = f(a)
      Bson.obj(nb -> b.asBson, nc -> c.asBson, nd -> d.asBson, ne -> e.asBson)
    }

  final def forProduct5[A, B: Encoder, C: Encoder, D: Encoder, E: Encoder, F: Encoder](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String
  )(
      f: A => (B, C, D, E, F)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c, d, e, ff) = f(a)
      Bson.obj(nb -> b.asBson, nc -> c.asBson, nd -> d.asBson, ne -> e.asBson, nf -> ff.asBson)
    }

  final def forProduct6[A, B: Encoder, C: Encoder, D: Encoder, E: Encoder, F: Encoder, G: Encoder](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String,
      ng: String
  )(
      f: A => (B, C, D, E, F, G)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c, d, e, ff, g) = f(a)
      Bson.obj(
        nb -> b.asBson,
        nc -> c.asBson,
        nd -> d.asBson,
        ne -> e.asBson,
        nf -> ff.asBson,
        ng -> g.asBson
      )
    }

  final def forProduct7[
      A,
      B: Encoder,
      C: Encoder,
      D: Encoder,
      E: Encoder,
      F: Encoder,
      G: Encoder,
      H: Encoder
  ](
      nb: String,
      nc: String,
      nd: String,
      ne: String,
      nf: String,
      ng: String,
      nh: String
  )(
      f: A => (B, C, D, E, F, G, H)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c, d, e, ff, g, h) = f(a)
      Bson.obj(
        nb -> b.asBson,
        nc -> c.asBson,
        nd -> d.asBson,
        ne -> e.asBson,
        nf -> ff.asBson,
        ng -> g.asBson,
        nh -> h.asBson
      )
    }

  final def forProduct8[
      A,
      B: Encoder,
      C: Encoder,
      D: Encoder,
      E: Encoder,
      F: Encoder,
      G: Encoder,
      H: Encoder,
      I: Encoder
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
      f: A => (B, C, D, E, F, G, H, I)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c, d, e, ff, g, h, i) = f(a)
      Bson.obj(
        nb -> b.asBson,
        nc -> c.asBson,
        nd -> d.asBson,
        ne -> e.asBson,
        nf -> ff.asBson,
        ng -> g.asBson,
        nh -> h.asBson,
        ni -> i.asBson
      )
    }

  final def forProduct9[
      A,
      B: Encoder,
      C: Encoder,
      D: Encoder,
      E: Encoder,
      F: Encoder,
      G: Encoder,
      H: Encoder,
      I: Encoder,
      J: Encoder
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
      f: A => (B, C, D, E, F, G, H, I, J)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c, d, e, ff, g, h, i, j) = f(a)
      Bson.obj(
        nb -> b.asBson,
        nc -> c.asBson,
        nd -> d.asBson,
        ne -> e.asBson,
        nf -> ff.asBson,
        ng -> g.asBson,
        nh -> h.asBson,
        ni -> i.asBson,
        nj -> j.asBson
      )
    }

  final def forProduct10[
      A,
      B: Encoder,
      C: Encoder,
      D: Encoder,
      E: Encoder,
      F: Encoder,
      G: Encoder,
      H: Encoder,
      I: Encoder,
      J: Encoder,
      K: Encoder
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
      f: A => (B, C, D, E, F, G, H, I, J, K)
  ): Encoder[A] =
    Encoder.instance[A] { a =>
      val (b, c, d, e, ff, g, h, i, j, k) = f(a)
      Bson.obj(
        nb -> b.asBson,
        nc -> c.asBson,
        nd -> d.asBson,
        ne -> e.asBson,
        nf -> ff.asBson,
        ng -> g.asBson,
        nh -> h.asBson,
        ni -> i.asBson,
        nj -> j.asBson,
        nk -> k.asBson
      )
    }

}
