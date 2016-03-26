package util

import play.api._

/**
  * Created by apoland on 3/23/16.
  */
object Dev {

  def launchApp = {

    val env = Environment(new java.io.File("."), this.getClass.getClassLoader, Mode.Dev)
    val context = ApplicationLoader.createContext(env)
    val loader = ApplicationLoader(context)
    val app = loader.load(context)
    Play.start(app)

  }

}

abstract class Foo extends (Int => Int) {


}
