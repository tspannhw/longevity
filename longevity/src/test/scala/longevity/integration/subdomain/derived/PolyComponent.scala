package longevity.integration.subdomain.derived

import longevity.model.annotations.polyComponent

@polyComponent
trait PolyComponent {
  val id: PolyComponentId
}
