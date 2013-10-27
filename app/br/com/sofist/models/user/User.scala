package br.com.sofist.models.user

import reactivemongo.bson.{BSONDocumentWriter, BSONDocument, BSONDocumentReader, BSONObjectID}

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/17/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
case class User(id: Option[BSONObjectID] = None, age: Int, firstName: String, lastName: String, feeds: List[Feed])
case class Feed(name: String, url: String)

