package longevity.integration.subdomain

import longevity.TestLongevityConfigs
import longevity.model.annotations.subdomain

/** covers a persistent with a single component entity */
package object componentWithOption {

  @subdomain object subdomain

  val contexts = TestLongevityConfigs.sparseContextMatrix(subdomain)

}
