package longevity.integration.subdomain

import longevity.context._
import longevity.subdomain._

/** covers a root entity with a nat key that contains a shorthand */
package object keyWithMultipleProperties {

  object context {
    val entityTypes = EntityTypePool() + KeyWithMultipleProperties
    val subdomain = Subdomain("Nat Key With Multiple Props", entityTypes)
    val mongoContext = LongevityContext(subdomain, Mongo)
    val cassandraContext = LongevityContext(subdomain, Cassandra)
  }

}