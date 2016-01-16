package longevity.integration.subdomain

import longevity.context._
import longevity.subdomain._
import emblem.traversors.sync.CustomGeneratorPool
import emblem.traversors.sync.CustomGenerator

/** covers a root entity with a simple shorthand constraint */
package object withSimpleConstraint {

  object shorthands {
    val emailShorthand = Shorthand[Email, String]
    implicit val shorthandPool = ShorthandPool.empty + emailShorthand
  }

  import shorthands._

  object context {
    val entityTypes = EntityTypePool() + WithSimpleConstraint
    val subdomain = Subdomain("With Simple Constraint", entityTypes)

    val emailGenerator = CustomGenerator.simpleGenerator[Email] { generator =>
      Email(s"{generator.generate[String]}@{generate.generate[String]")
    }
    val generators = CustomGeneratorPool.empty + emailGenerator

    val mongoContext = LongevityContext(subdomain, Mongo, customGeneratorPool = generators)
    val cassandraContext = LongevityContext(subdomain, Cassandra, customGeneratorPool = generators)
  }

}
