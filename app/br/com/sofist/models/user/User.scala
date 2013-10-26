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

object User{
    implicit object FeedBSONReader extends BSONDocumentReader[Feed] {
        def read(doc: BSONDocument): Feed =
            Feed(
                doc.getAs[String]("name").get,
                doc.getAs[String]("url").get
            )
    }

    implicit object FeedBSONWriter extends BSONDocumentWriter[Feed] {
        def write(feed: Feed): BSONDocument =
            BSONDocument(
                "name" -> feed.name,
                "url" -> feed.url
            )
    }

    implicit object UserBSONReader extends BSONDocumentReader[User] {
        def read(doc: BSONDocument): User =
            User(
                doc.getAs[BSONObjectID]("_id"),
                doc.getAs[Int]("age").get,
                doc.getAs[String]("firstName").get,
                doc.getAs[String]("lastName").get,
                doc.getAs[List[Feed]]("feeds").get
            )
    }

    implicit object UserBSONWriter extends BSONDocumentWriter[User] {
        def write(user: User): BSONDocument =
            BSONDocument(
                "_id" -> user.id.getOrElse(BSONObjectID.generate),
                "age" -> user.age,
                "firstName" -> user.firstName,
                "lastName" -> user.lastName,
                "feeds" -> user.feeds
            )
    }
}