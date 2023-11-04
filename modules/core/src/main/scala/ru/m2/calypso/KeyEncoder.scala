package ru.m2.calypso

trait KeyEncoder[A]:

  def apply(key: A): String

  final def contramap[B](f: B => A): KeyEncoder[B] =
    b => apply(f(b))

object KeyEncoder:

  def apply[A: KeyEncoder]: KeyEncoder[A] = summon

  def instance[A](f: A => String): KeyEncoder[A] = f(_)

  given KeyEncoder[String] = KeyEncoder.instance(identity)

  given KeyEncoder[Int] = KeyEncoder.instance(_.toString)
