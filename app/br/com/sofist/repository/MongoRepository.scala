package br.com.sofist.repository

import play.modules.reactivemongo.ReactiveMongoPlugin
import play.modules.reactivemongo.json.collection.JSONCollection
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json.{JsValue, Json}
import play.api.Play.current
import ExecutionContext.Implicits.global
import reactivemongo.bson.BSONObjectID

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/21/13
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */
abstract class MongoRepository[T] extends Repository[T, BSONObjectID]{

    lazy val db = ReactiveMongoPlugin.db

    def collection: JSONCollection
}
