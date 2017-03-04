package longevity.integration.model.componentList

import longevity.model.annotations.persistent

@persistent(keySet = Set(key(WithComponentList.props.id)))
case class WithComponentList(
  id: WithComponentListId,
  components: List[Component])