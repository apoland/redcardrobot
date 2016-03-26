package controllers

import play.api._
import play.api.mvc._
import anorm._
import play.api.db.DB

import models.Computer
import play.api.libs.json._


class Application extends Controller {

  def index = Action { implicit request =>

    val list = Computer.list()
    println(list)
    for (item <- list.items if item._1 != "foo")
      println(item._1)

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


  def listComputers() = Action {

    val json: JsValue = JsObject(Seq(
      "name" -> JsString("Watership Down"),
      "location" -> JsObject(Seq("lat" -> JsNumber(51.235685), "long" -> JsNumber(-1.309197))),
      "residents" -> JsArray(Seq(
        JsObject(Seq(
          "name" -> JsString("Fiver"),
          "age" -> JsNumber(4),
          "role" -> JsNull
        )),
        JsObject(Seq(
          "name" -> JsString("Bigwig"),
          "age" -> JsNumber(6),
          "role" -> JsString("Owsla")
        ))
      ))
    ))

    Ok(json).as("application/json")
  }

}