package ru.m2.calypso

import org.bson._

import java.util.UUID

object Bson {

  def obj(xs: (String, BsonValue)*): BsonDocument =
    xs.foldLeft(new BsonDocument()) {
      case (bson, (_, v)) if v.isNull => bson
      case (bson, (k, v))             => bson.append(k, v)
    }

  def of(xs: List[(String, BsonValue)]): BsonDocument = obj(xs: _*)

  def arr(xs: List[BsonValue]): BsonArray = {
    val bson = new BsonArray()
    xs.foreach(bson.add)
    bson
  }

  /** If the value is an expression, literal does not evaluate the expression but instead returns
    * the unparsed expression.
    *
    * https://docs.mongodb.com/manual/reference/operator/aggregation/literal/
    */
  def literal(value: BsonValue): BsonDocument =
    Bson.obj("$literal" -> value)

  val nullValue: BsonNull = new BsonNull()

  val empty: BsonDocument = new BsonDocument()

  def boolean(b: Boolean): BsonBoolean = new BsonBoolean(b)

  def int32(i: Int): BsonInt32 = new BsonInt32(i)

  def int64(l: Long): BsonInt64 = new BsonInt64(l)

  def double(d: Double): BsonDouble = new BsonDouble(d)

  def string(s: String): BsonString = new BsonString(s)

  def binary(data: Array[Byte]): BsonBinary = new BsonBinary(data)

  def uuid(uuid: UUID): BsonBinary = new BsonBinary(uuid)

  def dateTime(l: Long): BsonDateTime = new BsonDateTime(l)
}
