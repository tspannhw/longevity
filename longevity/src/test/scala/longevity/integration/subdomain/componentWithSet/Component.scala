package longevity.integration.subdomain.componentWithSet

import longevity.model.annotations.component

@component
case class Component(id: String, tags: Set[String])
