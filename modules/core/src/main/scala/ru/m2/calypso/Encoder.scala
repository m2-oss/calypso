package ru.m2.calypso

import org.bson.BsonValue

trait Encoder[A] {
  def apply(a: A): BsonValue
}
