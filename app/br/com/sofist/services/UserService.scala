package br.com.sofist.services

import br.com.sofist.models.user.{Feed, User, UserRepository}
import br.com.sofist.services.interfaces.{FeedDTO, UserDTO}
import com.google.inject.{Singleton, Inject}
import scala.concurrent._
import ExecutionContext.Implicits.global
import reactivemongo.bson.BSONObjectID

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/27/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
class UserService @Inject() (userRepository: UserRepository[Serializable]) {

    def saveUser(userDto: UserDTO): Future[Throwable] = {
        userRepository.save(User(
            id = userDto.id.map{id => BSONObjectID(id)},
            firstName = userDto.firstName,
            lastName = userDto.lastName,
            age = userDto.age,
            feeds = userDto.feeds.map{feedDto => Feed(feedDto.name, feedDto.url)}
        ))
    }

    def deleteUser(firstName: String): Future[Throwable] = {
        userRepository.deleteUserByName(firstName)
    }

    def findUser(firstName: String): Future[Option[UserDTO]] = {
        userRepository.findUserByName(firstName).map{user =>
            user match {
                case Some(user) => {
                    Some(UserDTO(
                        user.id.map{id => id.toString()},
                        user.age,
                        user.firstName,
                        user.lastName,
                        user.feeds.map{feed => FeedDTO(feed.name, feed.url)}
                    ))
                }
                case None => None
            }
        }
    }

    def listUsers(): Future[List[UserDTO]] = {
        userRepository.list().map{userList =>
            userList.map{user=>
                UserDTO(
                    user.id.map{id => id.toString()},
                    user.age,
                    user.firstName,
                    user.lastName,
                    user.feeds.map{feed => FeedDTO(feed.name, feed.url)}
                )
            }
        }
    }
}
