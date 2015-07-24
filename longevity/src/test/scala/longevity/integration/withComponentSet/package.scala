package longevity.integration

import longevity.context._
import longevity.shorthands._
import longevity.subdomain._

/** covers a root entity with a single setal non-root entity */
package object withComponentSet {

  val entityTypes = EntityTypePool() + WithComponentSet + Component

  val subdomain = Subdomain("With Component Set", entityTypes)

  val longevityContext = LongevityContext(subdomain, ShorthandPool.empty, Mongo)

}