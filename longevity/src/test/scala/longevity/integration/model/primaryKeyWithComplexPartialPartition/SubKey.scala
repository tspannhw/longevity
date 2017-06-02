package longevity.integration.model.primaryKeyWithComplexPartialPartition

import longevity.model.annotations.component

@component[DomainModel]
case class SubKey(
  prop1: String,
  prop2: String)
