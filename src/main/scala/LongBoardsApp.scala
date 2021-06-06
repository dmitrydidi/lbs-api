import akka.actor.{ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.stream.SystemMaterializer
import akka.http.scaladsl.server.Directives._

object LongBoardsApp extends App {
  implicit val system = ActorSystem("LongBoardsAPI")
  implicit val materializer = SystemMaterializer(system).materializer
  import system.dispatcher

  val PORT = 8080
  val HOST = "localhost"

  val initialRoute =
    path("api" / "longboard") {
      get {
        complete(StatusCodes.OK)
      }
    }

  Http().bindAndHandle(initialRoute, HOST, PORT)
}