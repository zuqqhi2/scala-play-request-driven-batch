package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.concurrent.duration._

import play.Logger
import akka.util.Timeout
import models.{ManagerActor, WorkRequest}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  val system = ActorSystem("MainSystem")
  val actor = system.actorOf(Props(classOf[ManagerActor]))
  def index = Action { implicit request =>
    implicit val timeout = Timeout(5.seconds)
    
    try {
      var getParams: Map[String,String] = request.queryString.map { case (k,v) => k -> v.mkString }
      actor ! WorkRequest(getParams)
    } catch {
      case e: Exception => {
        Logger.error(e.toString())
      }
    }

    // Return immediately
    Ok("")
  }

}
