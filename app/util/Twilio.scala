package util

import java.util

import com.twilio.sdk._
import org.apache.http.NameValuePair
import org.apache.http.message.BasicNameValuePair
import play.api.{Logger, Play}


/**
  * Created by apoland on 3/27/16.
  */
object Twilio {

  val ACCOUNT_SID = Play.current.configuration.getString("twilio.account").get
  val AUTH_TOKEN = Play.current.configuration.getString("twilio.token").get
  val FROM_PHONE = Play.current.configuration.getString("twilio.from.phone").get

  def sendMessages(messageBody: String, phoneNumbers: Seq[String]): Unit = {

    val client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN)
    val messageFactory = client.getAccount.getMessageFactory

    new Thread {
      override def run {
        for(number <- phoneNumbers) {
          val params = new util.ArrayList[NameValuePair]
          params.add(new BasicNameValuePair("Body", messageBody))
          params.add(new BasicNameValuePair("To", number))
          params.add(new BasicNameValuePair("From", FROM_PHONE))

          try {
            val message = messageFactory.create(params)
            Logger.info(s"[${message.getSid()}] Sent SMS to $number -> $messageBody")
          } catch {
            case e: Exception =>
              Logger.error(s"Problem sending SMS to ${number} - ${e}")
              e.printStackTrace()
          }

        }
      }
    }.start()

  }

}
