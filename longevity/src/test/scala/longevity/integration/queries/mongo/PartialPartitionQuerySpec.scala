package longevity.integration.queries.mongo

import longevity.TestLongevityConfigs
import longevity.context.LongevityContext
import longevity.test.QuerySpec
import longevity.integration.model.primaryKeyWithPartialPartition._
import longevity.integration.queries.queryTestsExecutionContext
import longevity.integration.queries.queryTestsExecutionContext
import scala.concurrent.Future

class PartialPartitionQuerySpec extends QuerySpec[Future, DomainModel, PrimaryKeyWithPartialPartition](
  new LongevityContext(TestLongevityConfigs.mongoConfig)) {

  lazy val sample = randomP
  val keyProp = PrimaryKeyWithPartialPartition.props.key
  import PrimaryKeyWithPartialPartition.queryDsl._

  behavior of "MongoPRepo.retrieveByQuery"

  it should "produce expected results for simple equality queries" in {
    exerciseQuery(keyProp eqs sample.key)
    exerciseQuery(keyProp neq sample.key)
    exerciseQuery(keyProp lt  sample.key)
    exerciseQuery(keyProp lte sample.key)
    exerciseQuery(keyProp gt  sample.key)
    exerciseQuery(keyProp gte sample.key)
  }

}
