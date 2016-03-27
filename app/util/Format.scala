package util

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

/**
  * Created by apoland on 3/26/16.
  */
object Format {
  def date(dt: DateTime): String =
    DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").print(dt);
}
