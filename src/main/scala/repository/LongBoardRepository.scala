package repository

import db.DBConfig
import entity.LongBoard
import org.mongodb.scala.{Completed, MongoCollection}
import org.mongodb.scala.model.Filters.equal

import scala.concurrent.Future
import org.mongodb.scala.result.DeleteResult
object LongBoardRepository {
 private val longboards : MongoCollection[LongBoard] = DBConfig.longBoardsCollection

  def createCollection(): Unit = {
    DBConfig.db.createCollection(DBConfig.COLLECTION_NAME).subscribe(
      (result: Completed) => println(s"$result"),
      (e: Throwable) => println(e.getLocalizedMessage),
      () => println("complete")
    )
  }

  def insert(l: LongBoard): Future[Completed] = {
    longboards.insertOne(l).toFuture()
  }

  def findAll(): Future[Seq[LongBoard]] = {
    longboards.find().toFuture()
  }

  def delete(id: String): Future[DeleteResult] = {
    longboards.deleteOne(equal("id", id)).toFuture()
  }


}
