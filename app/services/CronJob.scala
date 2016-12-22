package services

import javax.inject.{ Inject, Singleton }
import play.api.libs.concurrent.Akka
import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import com.google.inject.ImplementedBy
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.inject.ApplicationLifecycle
import scala.concurrent.Future
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension

import models.ClearDBActor

@ImplementedBy(classOf[CronJob])
trait Cron

@Singleton
class CronJob @Inject() (system: ActorSystem, lifeCycle: ApplicationLifecycle) extends Cron {
  import scala.concurrent.duration._

  val ClearDBActor = system.actorOf(Props(classOf[ClearDBActor]))
  QuartzSchedulerExtension(system).schedule("ClearDB", ClearDBActor, "")
}
