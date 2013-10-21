package br.com.sofist.models.domain

import br.com.sofist.repository.{MongoRepository}
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json.collection.JSONCollection
import play.api.libs.json.Json
import scala.concurrent.Future
import reactivemongo.core.commands.LastError

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/17/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
case class User(id: Option[BSONObjectID] = None, age: Int, firstName: String, lastName: String, feeds: List[Feed])
case class Feed(name: String, url: String)

object UserRepository extends MongoRepository[User]{
    import scala.concurrent.ExecutionContext.Implicits.global
    import play.modules.reactivemongo.json.BSONFormats._

    implicit val feedFormat = Json.format[Feed]
    implicit val userFormat = Json.format[User]

    val collection = db.collection[JSONCollection]("userFeeds")

    override def get(id: BSONObjectID): Future[Option[User]] = {
        collection.find(Json.obj("_id" -> id)).one[User]
    }

    override def delete(domain: User): Future[LastError] = {
        collection.remove(Json.obj("_id" -> domain.id))
    }

    override def list(): Future[List[User]] = {
        collection.find(Json.parse("{}")).cursor[User].toList()
    }

    override def save(domain: User): Future[LastError] = {
        collection.save(domain)
    }
}