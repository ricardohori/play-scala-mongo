package models

/**
 * Created with IntelliJ IDEA.
 * User: rfh
 * Date: 10/17/13
 * Time: 11:44 PM
 * To change this template use File | Settings | File Templates.
 */
object JsonFormats {
  import play.api.libs.json.Json

  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val feedFormat = Json.format[Feed]
  implicit val userFormat = Json.format[User]
}