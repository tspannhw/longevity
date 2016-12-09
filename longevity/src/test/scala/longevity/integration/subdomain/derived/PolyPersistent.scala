package longevity.integration.subdomain.derived

import longevity.model.annotations.polyPersistent

@polyPersistent(keySet = Set(key(props.id)))
trait PolyPersistent {
  val id: PolyPersistentId
  val component: PolyComponent
}
