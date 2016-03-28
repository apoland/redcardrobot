package util

import java.util
import play.api.mvc._
import com.twilio.sdk._
import com.twilio.sdk.verbs.TwiMLResponse
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
          val message = messageFactory.create(params)
          Logger.info(s"Sent message: ${message.getSid()} to $number")
        }
      }
    }.start()

  }

  def processInboundSMS(request: Request[AnyContent]): String = {

    Logger.info("Incoming SMS: "+ request.body.asFormUrlEncoded.get.map(v => s"${v._1} -> ${v._2.head} "))

    "<Response/>"

  }

}
