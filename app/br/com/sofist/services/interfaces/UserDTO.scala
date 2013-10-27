package br.com.sofist.services.interfaces


/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/26/13
 * Time: 7:53 PM
 * To change this template use File | Settings | File Templates.
 */
case class UserDTO(age: Int, firstName: String, lastName: String, feeds: List[FeedDTO])
case class FeedDTO(name: String, url: String)

object UserDTO {
    import play.api.libs.json._

    implicit val feedDTOReads = Json.reads[FeedDTO]
    implicit val feedDTOWrites = Json.writes[FeedDTO]

    implicit val userDTOReads = Json.reads[UserDTO]
    implicit val userDTOWrites = Json.writes[UserDTO]

}
