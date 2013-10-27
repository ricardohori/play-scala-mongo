package br.com.sofist.infrastructure.persistence.mongo

import reactivemongo.bson.{BSONObjectID, BSONDocumentWriter, BSONDocument, BSONDocumentReader}
import br.com.sofist.models.user.{User, Feed}

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/27/13
 * Time: 12:24 AM
 * To change this template use File | Settings | File Templates.
 */
object UserMapperMongo{

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
