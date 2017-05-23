package longevity.integration.model.foreignKey

import longevity.model.annotations.persistent

@persistent[DomainModel](keySet = Set(key(props.id)))
case class Associated(id: AssociatedId)
