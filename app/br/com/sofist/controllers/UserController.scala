package br.com.sofist.controllers

import play.api.mvc._
import br.com.sofist.models.user.{UserRepository, User, Feed}
import br.com.sofist.infrastructure.persistence.mongo.UserRepositoryMongo
import scala.concurrent.ExecutionContext.Implicits.global
import br.com.sofist.services.interfaces.UserDTO
import UserDTO._

object UserController extends Controller {

    def userRepository: UserRepository[Serializable] = new UserRepositoryMongo

    /**
     * JSON EXEMPLO:
     * {
            "firstName":"Fulano",
            "lastName":"da Silva",
            "age":34,
            "feeds":[
                {
                    "name":"Scala json!",
                    "url":"scala.json"
                },
                {
                    "name":"Scala bson!",
                    "url":"scala.bson"
                }
            ]
        }
     */
    def createUserFeed = Action { request =>
        request.body.asJson match {
            case None => Status(400)
            case Some(user) => {
                user.validate[UserDTO].map{userDto =>
                    val user = User(
                        firstName = userDto.firstName,
                        lastName = userDto.lastName,
                        age = userDto.age,
                        feeds = userDto.feeds.map{feedDto => Feed(feedDto.name, feedDto.url)}
                    )

                    val futureResult = userRepository.save(user)
                    Async {
                        futureResult.map(result => Ok)
                    }
                }.recoverTotal{error =>
                    Status(400)
                }
            }
        }
    }

    def findUserFeedByName(name: String) = Action {
        Async {
            // gather all the JsObjects in a list
            val futureUsersList = userRepository.list()

            // everything's ok! Let's reply with the array
            futureUsersList.map {users =>
                    Ok(users.toString)
            }
        }
    }
}