package longevity.integration.subdomain.attributeSets

import com.github.nscala_time.time.Imports._
import longevity.subdomain.persistent.Root
import longevity.subdomain.ptype.RootType

case class AttributeSets(
  uri: String,
  boolean: Set[Boolean],
  char: Set[Char],
  double: Set[Double],
  float: Set[Float],
  int: Set[Int],
  long: Set[Long],
  string: Set[String],
  dateTime: Set[DateTime])
extends Root

object AttributeSets extends RootType[AttributeSets] {
  object props {
    val uri = prop[String]("uri")
  }
  object keys {
    val uri = key(props.uri)
  }
  object indexes {
  }
}
