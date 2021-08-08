package service

import akka.actor.ActorSystem
import akka.stream.SystemMaterializer
import entity.{LongBoard, LongBoardRequest}
import org.mongodb.scala.Completed
import java.util.UUID

import akka.NotUsed
import akka.stream.scaladsl.Source
import org.mongodb.scala.result.DeleteResult
import repository.LongBoardRepository

import scala.concurrent.{ExecutionContextExecutor, Future}

object LongBoardService {
  implicit val system: ActorSystem = ActorSystem("LongBoardService")
  implicit val materializer = SystemMaterializer(system).materializer
  import system.dispatcher

  def findAll(): Future[Seq[LongBoard]] = LongBoardRepository.findAll()

  def saveLongBoard (lb: LongBoardRequest): Future[Completed] = {
    val longBoardDocument: LongBoard = longBoardMapperWithNewID(lb)
    LongBoardRepository.insert(longBoardDocument)
  }

  def deleteLongBoard (id: String): Future[DeleteResult] = {
    LongBoardRepository.delete(id)
  }

  private def longBoardMapperWithNewID(l: LongBoardRequest) = {
    LongBoard(id = UUID.randomUUID.toString, mark = l.mark, model = l.model, quantity = l.quantity)
  }
}
