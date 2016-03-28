package models

// Initialize SORM, automatically generating schema:

import org.joda.time.DateTime
import play.api.Play
import sorm._

// Declare a model:
case class Member( name : String, email: String, phone: String, instrument: String )
case class Message(body: String, date: DateTime, recipients: Set[Member] )
case class Response(sid: String, from: String, body: String, message: Message, member: Member )


object Db extends Instance (
  entities = Set() + Entity[Member]() + Entity[Message]() + Entity[Response](),
  url = Play.current.configuration.getString("db.default.url").get,
  user = Play.current.configuration.getString("db.default.username").get,
  password = Play.current.configuration.getString("db.default.password").get,
  initMode = InitMode.Create,
  poolSize = 12

)



