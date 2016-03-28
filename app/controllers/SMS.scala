package controllers

import models._
import org.joda.time.DateTime
import play.api.mvc._
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._

class SMS extends Controller {

  def incoming = Action { implicit request =>

    val response = Form(
      mapping(
        "MessageSid" -> text,
        "From" -> text,
        "Body" -> text,
        "message" -> ignored( Message("", new DateTime, Set[Member]() ) ),
        "member" -> ignored( Member("", "", "", "") )
       )(Response.apply)(Response.unapply)
    ).bindFromRequest.get

    Logger.info(s"[${response.sid}] Incoming SMS from: ${response.from} -> ${response.body}")

    //log incoming SMS message in the db
    //associate response with member that matches this phone number and the last message that was sent to them
    try {
      val member = Db.query[Member].whereEqual("phone", response.from).fetchOne().get
      val message = Db.query[Message].whereContains("recipients", member).order("date", true).fetchOne().get
      Db.save(response.copy(member = member, message = message))

      Ok(<Response><Message>Thank you.  Your response has been noted!</Message></Response>)

    } catch {
      case e: Throwable =>
        Logger.error(s"Could not find a member for phone number ${response.from} - ${e}")
        Ok(<Response><Message>Sorry, the system didn't recognize your phone number!  Please check with Caryn or Andrew to get this fixed.</Message></Response>)
    }

  }

}