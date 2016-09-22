package longevity.integration.subdomain

import longevity.TestLongevityConfigs
import longevity.subdomain.embeddable.ETypePool
import longevity.subdomain.embeddable.EntityType
import longevity.subdomain.Subdomain
import longevity.subdomain.ptype.PTypePool

/** covers a root entity with an optional component entity */
package object componentOption {

  val subdomain = Subdomain(
    "Component Option",
    PTypePool(WithComponentOption),
    ETypePool(EntityType[Component]))

  val contexts = TestLongevityConfigs.sparseContextMatrix(subdomain)

}