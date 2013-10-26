package br.com.sofist.infrastructure.persistence.mongo

import play.modules.reactivemongo.ReactiveMongoPlugin
import play.api.Play.current
import reactivemongo.api.collections.default.BSONCollection

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/25/13
 * Time: 11:47 PM
 * To change this template use File | Settings | File Templates.
 */
trait MongoRepository{
    lazy val db = ReactiveMongoPlugin.db

    def collection: BSONCollection
}
