package controllers

import util.Twilio
import models.{Db, Member, Message, Response}
import org.joda.time.DateTime
import play.api._
import play.api.mvc._
import play.api.db.DB
import play.api.libs.json._
import play.api.Logger
import play.api.data._
import play.api.data.Form
import play.api.data.Forms._
import sorm.Persisted



class Application extends Controller {


  def index = Action { implicit request =>
    Ok(views.html.index("Dashboard"))
  }

  def members = Action { implicit request =>

    val members = Db.query[Member]
      .fetch()

    Ok(views.html.members(members))
  }

  def saveMember = Action { implicit request =>

    val memberForm = Form(
      mapping(
        "name" -> text,
        "email" -> text,
        "phone" -> text,
        "instrument" -> text
      )(Member.apply)(Member.unapply)
    )

    val member = memberForm.bindFromRequest.get
    Db.save(member)


    Redirect(routes.Application.members())
  }

  def deleteMember(memberId: Long) = Action { implicit request =>

    val member = Db.fetchById[Member](memberId)
    Db.delete(member)

    Redirect(routes.Application.members())
  }

  def newMessage = Action { implicit request =>
    val messageForm = Form(
      mapping(
        "body" -> text,
        "date" -> default(jodaDate, new DateTime),
        "recipients" -> ignored( Set[Member]() ),
        "responses" -> ignored( Set[Response]() )
      )(Message.apply)(Message.unapply)
    )

    val message = Db.save(messageForm.bindFromRequest.get)

    //TODO: send to specific members by instrument type
    val recipients = Db.query[Member].fetch()

    Twilio.sendMessages(message.body, recipients.map( _.phone ))

    Db.save(message.copy(recipients = recipients.toSet))

    Redirect(routes.Application.messages())
  }



  def events = Action { implicit request =>
    Ok(views.html.index("Events"))
  }

  def messages = Action { implicit request =>

     val messages = Db.query[Message]
         .order("date", true)
         .limit(3)
         .fetch()


    Ok(views.html.messages(messages))
  }



}
