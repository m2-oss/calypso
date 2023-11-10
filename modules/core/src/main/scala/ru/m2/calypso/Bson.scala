package ru.m2.calypso

import org.bson.*

import java.util.UUID

object Bson:

  def obj(xs: (String, BsonValue)*): BsonDocument =
    xs.foldLeft(BsonDocument()):
      case (bson, (_, v)) if v.isNull => bson
      case (bson, (k, v))             => bson.append(k, v)

  def of(xs: List[(String, BsonValue)]): BsonDocument = obj(xs: _*)

  def arr(xs: List[BsonValue]): BsonArray =
    val bson = BsonArray()
    xs.foreach(bson.add)
    bson

  /** If the value is an expression, literal does not evaluate the expression but instead returns
    * the unparsed expression.
    *
    * https://docs.mongodb.com/manual/reference/operator/aggregation/literal/
    */
  def literal(value: BsonValue): BsonDocument = Bson.obj("$literal" -> value)

  val nullValue: BsonNull                   = BsonNull()
  val empty: BsonDocument                   = BsonDocument()
  def boolean(b: Boolean): BsonBoolean      = BsonBoolean(b)
  def int32(i: Int): BsonInt32              = BsonInt32(i)
  def int64(l: Long): BsonInt64             = BsonInt64(l)
  def double(d: Double): BsonDouble         = BsonDouble(d)
  def string(s: String): BsonString         = BsonString(s)
  def binary(data: Array[Byte]): BsonBinary = BsonBinary(data)
  def uuid(uuid: UUID): BsonBinary          = BsonBinary(uuid)
  def dateTime(l: Long): BsonDateTime       = BsonDateTime(l)
