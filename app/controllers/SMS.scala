package controllers

import util.Twilio

import models.{Db, Member, Message}
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

class SMS extends Controller {

  def incoming = Action { implicit request =>
    Ok(Twilio.processInboundSMS(request))
  }

}