package longevity.integration.subdomain.foreignKeyList

import longevity.ddd.subdomain.Root
import longevity.subdomain.ptype.PType

case class Associated(id: AssociatedId) extends Root

object Associated extends PType[Associated] {
  object props {
    val id = prop[AssociatedId]("id")
  }
  object keys {
    val id = key(props.id)
  }
}
