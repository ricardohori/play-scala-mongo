package br.com.sofist.infrastructure.persistence.mongo

import reactivemongo.bson.{BSONDocument, BSONObjectID}
import scala.concurrent.Future
import reactivemongo.core.commands.LastError
import br.com.sofist.models.user.{User, UserRepository}
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.api.collections.default.BSONCollection
import UserMapping._

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/25/13
 * Time: 11:58 PM
 * To change this template use File | Settings | File Templates.
 */
class UserRepositoryMongo extends UserRepository[Serializable] with MongoRepository{

    override lazy val collection = db[BSONCollection]("userFeeds")

    override def get(id: Serializable): Future[Option[User]] = {
        id match {
            case bsonId: BSONObjectID => collection.find(BSONDocument("_id" -> bsonId)).one[User]
            case _ => throw new IllegalArgumentException("[id] must be a [BSONObjectID] when using MongoRepository")
        }

    }

    override def delete(id: Serializable): Future[LastError] = {
        id match {
            case bsonId: BSONObjectID => collection.remove(BSONDocument("_id" -> bsonId))
            case _ => throw new IllegalArgumentException("[id] must be convertible to [BSONObjectID] when using MongoRepository")
        }
    }

    override def list(): Future[List[User]] = {
        collection.find(BSONDocument("$query" -> BSONDocument())).cursor[User].toList()
    }

    override def save(domain: User): Future[LastError] = {
        collection.save(domain)
    }
}

