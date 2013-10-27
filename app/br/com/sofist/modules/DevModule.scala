package br.com.sofist.modules

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import br.com.sofist.models.user.UserRepository
import br.com.sofist.infrastructure.persistence.mongo.UserRepositoryMongo

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/27/13
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
class DevModule extends AbstractModule with ScalaModule {
    def configure {
        bind[UserRepository[Serializable]].to[UserRepositoryMongo]
    }
}
