package longevity.persistence.mongo

import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase

private[mongo] case class MongoSession(client: MongoClient, db: MongoDatabase)
