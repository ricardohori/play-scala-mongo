package br.com.sofist.controllers

import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global
import br.com.sofist.services.interfaces.UserDTO
import UserDTO._
import br.com.sofist.services.UserService
import com.google.inject.{Inject, Singleton}
import play.api.libs.json.{JsString, Json}

@Singleton
class UserController @Inject() (userService: UserService) extends Controller {

    /**
     * JSON EXEMPLO:
     * {
            "firstName":"Fulano",
            "lastName":"da Silva",
            "age":34,
            "feeds":[
                {
                    "firstName":"Scala json!",
                    "url":"scala.json"
                },
                {
                    "firstName":"Scala bson!",
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
                    val futureResult = userService.saveUser(userDto)
                    Async {
                        futureResult.map(result => Ok)
                    }
                }.recoverTotal{error =>
                    Status(400)
                }
            }
        }
    }

    def deleteUserFeed = Action { request=>
        request.body.asJson match {
            case None => Status(400)
            case Some(json) => {
                (json \ "firstName").validate[String].map{firstName =>
                    val futureResult = userService.deleteUser(firstName)
                    Async{
                        futureResult.map(result => Ok )
                    }
                }.recoverTotal{error =>
                    Status(400)
                }
            }
        }
    }

    def findUserFeed = Action { request =>
        request.body.asJson match {
            case None => Status(400)
            case Some(json) => {
                (json \ "firstName").validate[String].map{firstName =>
                    val futureUser = userService.findUser(firstName)
                    Async{
                        futureUser.map{user =>
                            Ok(
                                if(user.isDefined) Json.toJson(user.get) else JsString("User not found!")
                            )
                        }
                    }
                }.recoverTotal{error =>
                    Status(400)
                }
            }
        }
    }

    def listUsers = Action {
        Async {
            // gather all the JsObjects in a list
            val futureUsersList = userService.listUsers()

            // everything's ok! Let's reply with the array
            futureUsersList.map {users =>
                Ok(Json.toJson(users))
            }
        }
    }
}