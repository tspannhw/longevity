package longevity.integration.subdomain.keyWithComponent

import longevity.model.annotations.persistent

@persistent(keySet = Set(key(props.id), key(props.secondaryKey)))
case class KeyWithComponent(
  id: KeyWithComponentId,
  secondaryKey: SecondaryKey)
