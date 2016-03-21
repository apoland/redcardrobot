package controllers

import javax.inject.Inject

import dao.CatDAO
import models.Cat
import play.api._
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject() (catDao: CatDAO) extends Controller {

  def index = Action { implicit request =>

    val cat = Cat("Simon", "Black")
    val f: Future[Unit] = catDao.insert(cat)

    f.onSuccess { case s => println(s"Result: $s") }
    f.onFailure { case s => println(s"FAILED: $s") }

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