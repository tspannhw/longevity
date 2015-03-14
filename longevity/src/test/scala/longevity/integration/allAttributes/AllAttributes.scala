package longevity.integration.allAttributes

import longevity.domain._

case class AllAttributes(
  boolean: Boolean,
  char: Char,
  double: Double,
  float: Float,
  int: Int,
  long: Long,
  string: String)
extends RootEntity

object AllAttributes extends RootEntityType[AllAttributes]