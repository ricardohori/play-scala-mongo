package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.concurrent.Future
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api._
import models.{Feed, User}
import models.JsonFormats._
import scala.collection.mutable.{ListBuffer, ArrayBuffer}
import play.api.data.validation.ValidationError
import play.libs.F.Tuple

object Application extends Controller with MongoController {

    def collection: JSONCollection = db.collection[JSONCollection]("userFeeds")

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
                val errors = new ArrayBuffer[JsError]

                // val firstName = (user \ "firstName").as[String]
                val firstName: Option[String] = (user \ "firstName").validate[String].map{s: String => Some(s)}.recoverTotal{jsError: JsError=>
                    errors += jsError
                    None
                }

                // val lastName = (user \ "lastName").as[String]
                val lastName: Option[String] = (user \ "lastName").validate[String].map{s: String => Some(s)}.recoverTotal{jsError: JsError=>
                    errors += jsError
                    None
                }

                // val age = (age \ "lastName").as[Int]
                val age: Option[Int] = (user \ "age").validate[Int].map{i: Int => Some(i)}.recoverTotal{jsError: JsError=>
                    errors += jsError
                    None
                }

                val feeds = (user \ "feeds").asOpt[Array[JsValue]] match {
                    case None => List()
                    case Some(feedsJs) => createFeedsFromJs(feedsJs)
                }

                if(errors.size > 0){
                    Status(400)
                }else{
                    val user = User(
                        firstName = firstName.get,
                        lastName = lastName.get,
                        age = age.get,
                        feeds = feeds
                    )
                    val futureResult = collection.insert(user)
                    Async {
                        futureResult.map(_ => Ok)
                    }
                }
            }
        }
    }

    private def createFeedsFromJs(feedsJs: Array[JsValue]): List[Feed] = {
        val feeds = for(
            feedJs <- feedsJs;
            name = (feedJs \ "name").validate[String].map{s: String => s}.recoverTotal{jsError: JsError=> ""};
            url = (feedJs \ "url").validate[String].map{s: String => s}.recoverTotal{jsError: JsError=> ""}
        ) yield Feed(name, url)
        feeds.toList
    }

    def findUserFeedByName(name: String) = Action {
        Async {
            val cursor: Cursor[User] = collection.find(Json.obj("firstName" -> name)).
                sort(Json.obj("created" -> -1)).cursor[User]

            // gather all the JsObjects in a list
            val futureUsersList: Future[List[User]] = cursor.toList

            // everything's ok! Let's reply with the array
            futureUsersList.map {users =>
                    Ok(users.toString)
            }
        }
    }
}