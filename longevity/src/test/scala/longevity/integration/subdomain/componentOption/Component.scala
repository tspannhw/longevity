package longevity.integration.subdomain.componentOption

import longevity.model.annotations.component

@component
case class Component(id: String, tags: Set[String])
