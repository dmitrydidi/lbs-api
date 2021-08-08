import LongBoardMap.LongBoardsMap.{Add, Delete, GetAll}
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.stream.SystemMaterializer
import akka.http.scaladsl.server.Directives._
import _root_.entity.{DeleteRequest, LongBoard, LongBoardRequest}
import actor.LongBoardActor
import spray.json._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._


trait LongBoardJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {


  implicit val longBoardFormat = jsonFormat4(LongBoard)
  implicit val longBoardRequest = jsonFormat3(LongBoardRequest)

  implicit val longBoardDeleteRequest = jsonFormat1(DeleteRequest)
}

object LongBoardsApp extends App with LongBoardJsonProtocol {
  implicit val system = ActorSystem("LongBoardsAPI")
  implicit val materializer = SystemMaterializer(system).materializer
  private val longBoardActor = system.actorOf(Props[LongBoardActor], "LongBoardActor")
  implicit val DEFAULT_TIMEOUT = Timeout(2 seconds)

  import system.dispatcher

  val PORT = 8080
  val HOST = "localhost"
  val initialRoute =
    path("api" / "longboard") {
      get {
        path("mark" / Segment) { _ =>
          reject
        } ~
          path("lb" / Segment) { _ =>
            reject
          } ~
          parameter('id) { _ =>
            reject
          } ~
          pathEndOrSingleSlash {
            complete((longBoardActor ? GetAll).mapTo[Seq[LongBoard]])
          }
      } ~
          post {
            entity(as[LongBoardRequest]) { l =>
              longBoardActor ! Add(l)
              complete(StatusCodes.OK)
            }
          } ~
          delete {
            entity(as[DeleteRequest]) { longBoardId =>
              println(longBoardId)
              longBoardActor ! Delete(longBoardId.id)
              complete(StatusCodes.OK)
            }
          } ~
          put {
            reject
          }
      }

      Http().bindAndHandle(initialRoute, HOST, PORT)
}