package models

import akka.actor.Actor
import play.api._
import play.api.Play.current

import akka.actor.actorRef2Scala
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor._
import akka.util.Timeout
import akka.routing.RoundRobinPool
import akka.routing._
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._
import scala.concurrent._
import ExecutionContext.Implicits.global


import models.WorkRequest

class ManagerActor extends Actor with ActorLogging {
  implicit val timeout = Timeout(5.seconds)

  // Retry strategy
  val mySupervisorStrategy = OneForOneStrategy(
      maxNrOfRetries = 10,
      withinTimeRange = 3.seconds
    ){
      case _ => Restart
    }

  // Child actors
  val workerActors = context.actorOf(Props(classOf[WorkerActor]).withRouter(RoundRobinPool(3, supervisorStrategy = mySupervisorStrategy)))

  def receive = {
    case msg:WorkRequest => workerActors ! msg
    case _  => 
  }
}
