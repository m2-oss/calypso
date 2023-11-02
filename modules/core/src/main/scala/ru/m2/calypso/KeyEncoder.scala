package ru.m2.calypso

trait KeyEncoder[A] {
  def apply(key: A): String

  final def contramap[B](f: B => A): KeyEncoder[B] =
    KeyEncoder.instance { b =>
      apply(f(b))
    }
}

object KeyEncoder {
  def apply[A](implicit instance: KeyEncoder[A]): KeyEncoder[A] = instance

  def instance[A](f: A => String): KeyEncoder[A] = f(_)

  implicit val encodeKeyString: KeyEncoder[String] = KeyEncoder.instance(identity)

  implicit val encodeKeyInt: KeyEncoder[Int] = KeyEncoder.instance(_.toString)
}
