package db

import org.mongodb.scala._
import com.mongodb.{MongoCredential, ServerAddress}
import entity.LongBoard

import scala.collection.JavaConverters.seqAsJavaListConverter
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.MongoClient.DEFAULT_CODEC_REGISTRY
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}

object DBConfig {

  val COLLECTION_NAME = "longboards"
  val user: String = "root"
  val password: Array[Char] = "example".toCharArray
  val source: String = "admin"
  private val credential: MongoCredential = MongoCredential.createCredential(user, source, password)

  val codecRegistry = fromRegistries(fromProviders(classOf[LongBoard]), DEFAULT_CODEC_REGISTRY )

  val config: MongoClientSettings =
    MongoClientSettings
      .builder()
      .credential(credential)
      .codecRegistry(codecRegistry)
      .applyToClusterSettings( b =>
        b.hosts(List(
          new ServerAddress("localhost")
        )
          .asJava)
      )
      .build()


  // Use a Connection String
  val client: MongoClient = MongoClient(config)
  val db: MongoDatabase = client.getDatabase("lbs")



  val longBoardsCollection: MongoCollection[LongBoard] = db.getCollection(COLLECTION_NAME)

}