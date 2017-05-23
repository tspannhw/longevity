package longevity.integration.model.componentOption

import longevity.model.annotations.persistent

@persistent[DomainModel](keySet = Set(key(WithComponentOption.props.id)))
case class WithComponentOption(
  id: WithComponentOptionId,
  component: Option[Component])
