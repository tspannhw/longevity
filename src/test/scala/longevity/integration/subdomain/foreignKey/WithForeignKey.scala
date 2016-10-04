package longevity.integration.subdomain.foreignKey

import longevity.ddd.subdomain.Root
import longevity.subdomain.ptype.PType

case class WithForeignKey(
  id: WithForeignKeyId,
  associated: AssociatedId)
extends Root

object WithForeignKey extends PType[WithForeignKey] {
  object props {
    val id = prop[WithForeignKeyId]("id")
    val associated = prop[AssociatedId]("associated")
  }
  object keys {
    val id = key(props.id)
  }
  object indexes {
    val id = index(props.id)
    val associated = index(props.associated)
  }
}
