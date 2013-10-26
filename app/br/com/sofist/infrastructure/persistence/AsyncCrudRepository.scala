package br.com.sofist.infrastructure.persistence

import scala.concurrent.Future

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/25/13
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
trait AsyncCrudRepository[ID <: Serializable, T] extends Repository[ID, T]{

    def get(id: ID): Future[Option[T]]

    def delete(id: ID): Future[Throwable]

    def list(): Future[List[T]]

    def save(domain: T): Future[Throwable]

}
