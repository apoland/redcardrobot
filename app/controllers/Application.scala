package controllers

import models.{Db, Member}
import play.api._
import play.api.mvc._
import play.api.db.DB
import play.api.libs.json._
import sorm.Persisted


class Application extends Controller {

  def index = Action { implicit request =>

    //val bob = Db.save(Member("Bob"))
    //val dole = Db.save(Member("Dole"))

    val members = Db.query[Member]
      .fetch() // the sql query gets emitted only at this point

    members.foreach(println)

    Ok(views.html.index("Dashboard"))
  }

  def members = Action { implicit request =>
    Ok(views.html.index("Members"))
  }

  def events = Action { implicit request =>
    Ok(views.html.index("Events"))
  }

  def messages = Action { implicit request =>
    Ok(views.html.index("Messages"))
  }



}