package longevity.integration.model.keyWithShorthand

import longevity.model.annotations.persistent

@persistent[DomainModel](keySet = Set(key(props.id), key(props.secondaryKey)))
case class KeyWithShorthand(
  id: KeyWithShorthandId,
  secondaryKey: SecondaryKey)
