package longevity.integration.model.shorthandSets

import longevity.model.annotations.persistent

@persistent[DomainModel](keySet = Set(key(props.id)))
case class ShorthandSets(
  id: ShorthandSetsId,
  boolean: Set[BooleanShorthand],
  char: Set[CharShorthand],
  double: Set[DoubleShorthand],
  float: Set[FloatShorthand],
  int: Set[IntShorthand],
  long: Set[LongShorthand],
  string: Set[StringShorthand],
  dateTime: Set[DateTimeShorthand])
