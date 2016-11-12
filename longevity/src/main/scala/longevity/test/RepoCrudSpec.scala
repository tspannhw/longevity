package longevity.test

import emblem.typeBound.TypeBoundPair
import emblem.TypeKey
import longevity.context.LongevityContext
import longevity.persistence.BaseRepo
import longevity.persistence.Deleted
import longevity.persistence.PState
import longevity.persistence.RepoPool
import longevity.subdomain.KeyVal
import longevity.subdomain.Persistent
import longevity.subdomain.PolyPType
import longevity.subdomain.realized.RealizedKey
import org.scalatest.FlatSpec
import org.scalatest.GivenWhenThen
import org.scalatest.OptionValues.convertOptionToValuable
import org.scalatest.Tag
import scala.concurrent.ExecutionContext

/** a [[http://www.scalatest.org/ ScalaTest]] fixture to test a
 * [[longevity.persistence.RepoPool RepoPool]]. instances of this test are
 * provided in your [[longevity.context.LongevityContext LongevityContext]] via
 * methods `repoCrudSpec` and `inMemRepoCrudSpec`. these methods are added by an
 * implicit conversion from `LongevityContext` to
 * [[longevity.context.TestContext.ScalaTestSpecs ScalaTestSpecs]].
 *
 * the repo CRUD spec exercises create/retrieve/update/delete for all the repos
 * in your repo pool.
 *
 * pardon the nasty ScalaDocs for this class. we haven't figured out how to
 * remove the methods inherited from ScalaTest classes yet.
 * 
 * @param context the longevity context
 * 
 * @param repoPool the repo pool under test. this may be different than the
 * `longevityContext.repoPool`, as users may want to test against other repo
 * pools. (for instance, they may want a spec for in-memory repo pools if other
 * parts of their test suite rely on them.)
 * 
 * @param suiteNameSuffix a short string to add to the suite name, to help
 * differentiate between suites for longevity contexts with the same name, when
 * reading scalatest output
 *
 * @param executionContext the execution context
 */
class RepoCrudSpec private[longevity] (
  protected val longevityContext: LongevityContext,
  protected val repoPool: RepoPool,
  suiteNameSuffix: Option[String] = None)(
  protected implicit val executionContext: ExecutionContext)
extends FlatSpec with LongevityIntegrationSpec with GivenWhenThen {

  private def subdomainName = longevityContext.subdomain.name
  override val suiteName = s"RepoCrudSpec for ${subdomainName}${suiteNameSuffix match {
    case Some(suffix) => suffix
    case None => ""
  }}"

  repoPool.baseRepoMap.foreach { pair =>
    def repoSpec[P <: Persistent](pair: TypeBoundPair[Persistent, TypeKey, BaseRepo, P]): Unit = {
      new RepoSpec(pair._2, pair._1)
    }
    repoSpec(pair)
  }

  override def afterAll = repoPool.closeSession().futureValue

  private class RepoSpec[P <: Persistent](
    private val repo: BaseRepo[P],
    private val pTypeKey: TypeKey[P]) {

    private val pName = pTypeKey.name

    object Create extends Tag("Create")
    object Retrieve extends Tag("Retrieve")
    object Update extends Tag("Update")
    object Delete extends Tag("Delete")

    s"${pName}Repo.create" should s"produce a persisted $pName" taggedAs(Create) in {
      val p = randomP()
      val created: PState[P] = repo.create(p).futureValue
      created.get should equal (p)

      repo.realizedPType.keySet.foreach { key =>
        val retrieved: PState[P] = retrieveByKey(key, created.get).value
        retrieved.get should equal (p)
      }
    }

    s"${pName}Repo.retrieve" should s"should produce the same persisted $pName" taggedAs(Retrieve) in {
      val p = randomP()
      val created = repo.create(p).futureValue

      repo.realizedPType.keySet.foreach { key =>
        val retrieved: PState[P] = retrieveByKey(key, created.get).value
        retrieved.get should equal (p)
      }
    }

    s"${pName}Repo.update" should s"should produce an updated persisted $pName" taggedAs(Update) in {
      val key = randomPTypeKey
      val originalP = randomP(key)
      val modifiedP = repo.realizedPType.keySet.foldLeft(randomP(key)) { (modified, key) =>
        def updateByOriginalKeyVal[V <: KeyVal[P, V]](key: RealizedKey[P, V]) = {
          val originalKeyVal = key.keyValForP(originalP)
          key.updateKeyVal(modified, originalKeyVal)
        }
        updateByOriginalKeyVal(key)
      }

      val created: PState[P] = repo.create(originalP).futureValue

      val modified: PState[P] = created.map(e => modifiedP)
      val updated: PState[P] = repo.update(modified).futureValue

      updated.get should equal (modifiedP)

      repo.realizedPType.keySet.foreach { key =>
        val retrieved: PState[P] = retrieveByKey(key, modifiedP).value
        retrieved.get should equal (modifiedP)
      }
    }

    s"${pName}Repo.delete" should s"delete a persisted $pName" taggedAs(Delete) in {
      val p = randomP()
      val created: PState[P] = repo.create(p).futureValue

      val deleted: Deleted[P] = repo.delete(created).futureValue
      deleted.get should equal (p)

      repo.realizedPType.keySet.foreach { key =>
        val retrieved: Option[PState[P]] = retrieveByKey(key, created.get)
        retrieved.isEmpty should be (true)
      }
    }

    private def randomPTypeKey(): TypeKey[_ <: P] = {
      repo.pType match {
        case polyPType: PolyPType[P] =>
          val union = longevityContext.subdomain.emblematic.unions(pTypeKey)
          val derivedTypeKeys = union.constituentKeys.toSeq
          val randomIndex = math.abs(longevityContext.testDataGenerator.generate[Int]) % derivedTypeKeys.size
          derivedTypeKeys(randomIndex)
        case _ =>
          pTypeKey
      }
    }

    private def randomP(key: TypeKey[_ <: P] = pTypeKey): P = longevityContext.testDataGenerator.generate(key)

    private def retrieveByKey[V <: KeyVal[P, V]](key: RealizedKey[P, V], p: P): Option[PState[P]] = {
      val keyValForP = key.keyValForP(p)
      implicit val keyValTypeKey = key.keyValTypeKey
      repo.retrieve(key.keyValForP(p)).futureValue
    }

  }
 
}
