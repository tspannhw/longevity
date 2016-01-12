package longevity.exceptions.subdomain

import longevity.subdomain._

/** thrown on attempt to retrieve an unpersisted aggregate from a persisted assoc */
class AssocIsPersistedException(assoc: Assoc[_ <: Root])
extends AssocException(assoc, "cannot retrieve an unpersisted aggregate from a persisted assoc")