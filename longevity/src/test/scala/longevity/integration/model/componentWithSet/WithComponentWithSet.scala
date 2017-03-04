package longevity.integration.model.componentWithSet

import longevity.model.annotations.persistent

@persistent(keySet = Set(key(props.id)))
case class WithComponentWithSet(
  id: WithComponentWithSetId,
  component: Component)