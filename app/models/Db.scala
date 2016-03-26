package models

// Initialize SORM, automatically generating schema:
import sorm._

// Declare a model:
case class Member( name : String )

object Db extends Instance (
  entities = Set() + Entity[Member](),
  url = "jdbc:mysql://localhost/redcardrobot",
  user = "root",
  password = "root",
  initMode = InitMode.Create,
  poolSize = 12
)



