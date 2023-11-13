package ru.m2.calypso

import cats.syntax.either.*

trait KeyDecoder[A]:

  def apply(key: String): Either[String, A]

  final def map[B](f: A => B): KeyDecoder[B] =
    s => apply(s).map(f)

  final def emap[B](f: A => Either[String, B]): KeyDecoder[B] =
    s => apply(s).flatMap(f)

object KeyDecoder:

  def apply[A: KeyDecoder]: KeyDecoder[A] = summon

  def instance[A](f: String => Either[String, A]): KeyDecoder[A] = f(_)

  given KeyDecoder[String] = s => s.asRight
  given KeyDecoder[Int]    = s => s.toIntOption.toRight(s"KeyDecoder[Int].failure: [$s]")
