package longevity.persistence.cassandra

import emblem.Emblematic
import emblem.TypeKey
import emblem.traversors.sync.EmblemToJsonTranslator
import longevity.exceptions.persistence.AssocIsUnpersistedException
import longevity.subdomain.Assoc
import longevity.subdomain.AssocAny
import longevity.subdomain.persistent.Persistent
import org.json4s.JsonAST.JValue
import org.json4s.JsonAST.JString

private[cassandra] class PersistentToJsonTranslator(
  override protected val emblematic: Emblematic)
extends EmblemToJsonTranslator {

  override protected val customTraversors = CustomTraversorPool.empty + assocTraversor

  def assocTraversor = new CustomTraversor[AssocAny] {
    def apply[B <: Assoc[_ <: Persistent] : TypeKey](input: B): JValue = {
      if (!input.isPersisted) {
        throw new AssocIsUnpersistedException(input)
      }
      JString(input.asInstanceOf[CassandraId[_ <: Persistent]].uuid.toString)
    }
  }

}
