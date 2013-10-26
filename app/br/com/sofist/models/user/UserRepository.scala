package br.com.sofist.models.user

import br.com.sofist.infrastructure.persistence.AsyncCrudRepository

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/25/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
trait UserRepository[ID <: Serializable] extends AsyncCrudRepository[ID, User]{

}
