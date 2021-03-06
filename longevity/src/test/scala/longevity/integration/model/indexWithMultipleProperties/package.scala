package longevity.integration.model

import longevity.TestLongevityConfigs
import longevity.model.annotations.domainModel
import longevity.effect.Blocking

/** covers a persistent with a index that contains a shorthand */
package object indexWithMultipleProperties {

  @domainModel trait DomainModel

  val contexts = TestLongevityConfigs.sparseContextMatrix[Blocking, DomainModel]()

}
