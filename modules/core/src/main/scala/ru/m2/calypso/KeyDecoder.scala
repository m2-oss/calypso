package ru.m2.calypso

import cats.syntax.either._

trait KeyDecoder[A] {
  def apply(key: String): Either[String, A]

  final def map[B](f: A => B): KeyDecoder[B] =
    KeyDecoder.instance { s =>
      apply(s).map(f)
    }

  final def emap[B](f: A => Either[String, B]): KeyDecoder[B] =
    KeyDecoder.instance { s =>
      apply(s).flatMap(f)
    }
}

object KeyDecoder {
  def apply[A](implicit instance: KeyDecoder[A]): KeyDecoder[A] = instance

  def instance[A](f: String => Either[String, A]): KeyDecoder[A] = f(_)

  implicit val decodeKeyString: KeyDecoder[String] = KeyDecoder.instance(_.asRight)

  implicit val decodeKeyInt: KeyDecoder[Int] = KeyDecoder.instance { s =>
    s.toIntOption.toRight(s"KeyDecoder[Int].failure: [$s]")
  }
}
