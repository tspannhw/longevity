package longevity.integration.model.simpleConstraint

import longevity.model.annotations.persistent

@persistent[DomainModel](keySet = Set(key(props.id)))
case class SimpleConstraint(
  id: SimpleConstraintId,
  primaryEmail: Email,
  emails: Set[Email])
