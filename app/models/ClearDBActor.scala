package models

import akka.actor.Actor
import play.api._
import play.api.Play.current
import play.api.libs.json._
import play.api.Logger

class ClearDBActor extends Actor {
 
  def receive = {
    case msg:String => {
      Logger.info("Old DB records were cleared.")
    }
  }
}
