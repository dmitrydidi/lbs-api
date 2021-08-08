package actor


import akka.actor.{Actor, ActorLogging}
import service.LongBoardService

import scala.concurrent.ExecutionContext

class LongBoardActor extends Actor with ActorLogging {
  import LongBoardMap.LongBoardsMap._
  implicit val ec: ExecutionContext = context.dispatcher
  import akka.pattern.pipe

  override def receive: Receive = {

    case GetAll =>
      log.info("# FIND ALL #  Finding all Longboards")
      LongBoardService.findAll().pipeTo(sender())

    case Add(l) =>
      log.info("# ADD # Adding new Longboard")
      sender ! LongBoardService.saveLongBoard(l)

    case Delete(id) =>
      log.info(s"# DELETE # Deleting LongBoard with id: $id")
      sender ! LongBoardService.deleteLongBoard(id)
  }

}
