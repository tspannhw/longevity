package longevity.integration.subdomain.basicSets

import com.github.nscala_time.time.Imports._
import longevity.ddd.subdomain.Root
import longevity.subdomain.ptype.PType

case class BasicSets(
  id: BasicSetsId,
  boolean: Set[Boolean],
  char: Set[Char],
  double: Set[Double],
  float: Set[Float],
  int: Set[Int],
  long: Set[Long],
  string: Set[String],
  dateTime: Set[DateTime])
extends Root

object BasicSets extends PType[BasicSets] {
  object props {
    val id = prop[BasicSetsId]("id")
  }
  object keys {
    val id = key(props.id)
  }
}
