package longevity.integration.subdomain.simpleConstraint

import longevity.model.annotations.persistent

@persistent(keySet = Set(key(props.id)))
case class SimpleConstraint(
  id: SimpleConstraintId,
  primaryEmail: Email,
  emails: Set[Email])
