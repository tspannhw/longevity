package longevity.integration.subdomain

import longevity.TestLongevityConfigs
import longevity.subdomain.Subdomain
import longevity.subdomain.embeddable.ETypePool
import longevity.subdomain.embeddable.ValueType
import longevity.subdomain.ptype.PTypePool

/** covers a root entity with a key that contains a shorthand */
package object keyWithForeignKey {

  val subdomain = Subdomain(
    "Key With Foreign Key",
    PTypePool(KeyWithForeignKey, Associated),
    ETypePool(ValueType[Uri]))

  val contexts = TestLongevityConfigs.sparseContextMatrix(subdomain)

}