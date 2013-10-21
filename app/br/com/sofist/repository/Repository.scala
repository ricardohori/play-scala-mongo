package br.com.sofist.repository

import scala.concurrent.Future

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/20/13
 * Time: 11:58 PM
 * To change this template use File | Settings | File Templates.
 */
trait Repository[T, S <: Serializable] {

    def get(id: S) : Future[Option[T]]

    def delete(domain: T): Future[AnyRef]

    def list(): Future[List[T]]

    def save(domain: T): Future[AnyRef]
}
