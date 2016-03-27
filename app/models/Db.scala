package models

// Initialize SORM, automatically generating schema:

import org.joda.time.DateTime
import play.api.Play
import sorm._

// Declare a model:
case class Member( name : String, email: String, phone: String, instrument: String )
case class Message(body: String, date: DateTime, recipients: Set[Member] )

object Test {

  val foo = Play.current.configuration.getString("db.url")


}

object Db extends Instance (
  entities = Set() + Entity[Member]() + Entity[Message](),
  url = Play.current.configuration.getString("db.default.url").get,
  user = Play.current.configuration.getString("db.default.username").get,
  password = Play.current.configuration.getString("db.default.password").get,
  initMode = InitMode.Create,
  poolSize = 12

)



