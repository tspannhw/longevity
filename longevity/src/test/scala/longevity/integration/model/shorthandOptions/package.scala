package longevity.integration.model

import longevity.TestLongevityConfigs
import longevity.model.annotations.domainModel
import scala.concurrent.Future

package object shorthandOptions {

  @domainModel trait DomainModel

  val contexts = TestLongevityConfigs.sparseContextMatrix[Future, DomainModel]()

}
