package longevity.test

import emblem._
import emblem.traversors.Transformer
import emblem.traversors.Transformer.CustomTransformer
import emblem.traversors.Transformer.emptyCustomTransformers
import longevity.context.LongevityContext
import longevity.subdomain.Assoc
import longevity.subdomain.AssocAny
import longevity.subdomain.RootEntity
import longevity.subdomain.UnpersistedAssoc
import longevity.exceptions.AssocIsUnpersistedException
import longevity.persistence.PersistedAssoc

/** traverses an entity graph, replacing every [[longevity.persistence.PersistedAssoc persisted assoc]] with an
 * [[longevity.subdomain.UnpersistedAssoc]].
 *
 * this is useful for testing purposes, as it transforms a persisted entity into its unpersisted equivalent.
 *
 * TODO docs for params
 * @param emblemPool a pool of emblems for the entities to be transformed
 * @param shorthandPool a complete set of the shorthands used by the bounded context
 */
class PersistedToUnpersistedTransformer(
  override protected val emblemPool: EmblemPool,
  override protected val shorthandPool: ShorthandPool)
extends Transformer {

  override protected val customTransformers = emptyCustomTransformers + transformAssoc

  private lazy val transformAssoc = new CustomTransformer[AssocAny] {
    def apply[B <: AssocAny : TypeKey](transformer: Transformer, input: B): B = input match {
      case unpersistedAssoc: UnpersistedAssoc[_] =>
        throw new AssocIsUnpersistedException(unpersistedAssoc)
      case persistedAssoc: PersistedAssoc[_] => {
        val persistedEntity = input.persisted
        val entityTypeKey = typeKey[B].typeArgs.head.asInstanceOf[TypeKey[RootEntity]]
        val unpersistedEntity = transform(persistedEntity)(entityTypeKey)
        Assoc(unpersistedEntity).asInstanceOf[B]
      }
    }
  }

}