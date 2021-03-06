package longevity.integration.model

import longevity.TestLongevityConfigs
import longevity.model.annotations.domainModel
import longevity.effect.Blocking

package object shorthandLists {

  @domainModel trait DomainModel

  val contexts = TestLongevityConfigs.sparseContextMatrix[Blocking, DomainModel]()

}
