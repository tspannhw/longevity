package longevity.exceptions

import longevity.subdomain.NatKeyProp
import longevity.subdomain.RootEntity
import longevity.subdomain.RootEntityType

/** an exception indicating that the
 * [[longevity.subdomain.NatKeyProp natural key property]] passed to
 * [[longevity.subdomain.RootEntityType#NatKey#Builder.setProp]] is not a part of the natural key
 * being built.
 *
 * @param key the nat key
 * @param prop the nat key prop
 */
class NatKeyDoesNotContainPropException[E <: RootEntity](
  key: RootEntityType[E]#NatKey,
  prop: NatKeyProp[E])
extends NatKeyValBuilderException(
  s"nat key prop $prop is not a part of natural key $key")
