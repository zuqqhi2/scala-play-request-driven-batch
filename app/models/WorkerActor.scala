package models

import akka.actor.Actor
import play.api._
import play.api.Play.current

import scala.concurrent._
import scala.concurrent.duration.Duration
import play.api.libs.concurrent.Execution.Implicits._
import play.Logger
import akka.util.Timeout
import akka.actor._

import models.WorkRequest

class WorkerActor extends Actor {

  def receive = {
    case WorkRequest(getParams) => {
        Logger.info("Worker : " + getParams.toString)
    }

    case _ => 
  }
}
