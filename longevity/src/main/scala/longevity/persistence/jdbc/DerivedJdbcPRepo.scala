package longevity.persistence.jdbc

import longevity.persistence.PState
import longevity.model.realized.RealizedPropComponent
import longevity.model.realized.RealizedKey

private[persistence] trait DerivedJdbcPRepo[F[_], M, P, Poly >: P] extends JdbcPRepo[F, M, P] {

  protected val polyRepo: JdbcPRepo[F, M, Poly]

  override protected[jdbc] val tableName: String = polyRepo.tableName

  override protected[persistence] def hasPrimaryKey = polyRepo.hasPrimaryKey

  override protected def jsonStringForP(p: P): String = {
    // we use the poly type key here so we get the discriminator in the JSON
    import org.json4s.native.JsonMethods._
    compact(render(emblematicToJsonTranslator.translate[Poly](p)(polyRepo.pTypeKey)))
  }

  override protected[jdbc] lazy val primaryKeyComponents: Seq[RealizedPropComponent[_ >: P, _, _]] =
    polyRepo.primaryKeyComponents

  override protected[jdbc] lazy val indexedComponents: Set[RealizedPropComponent[_ >: P, _, _]] =
    super.indexedComponents ++ polyRepo.indexedComponents

  override protected[persistence] def createSchemaBlocking(): Unit = {
    createActualizedPropColumns()
    createUniqueIndexes()
    createNonUniqueIndexes()
  }

  private def createActualizedPropColumns(): Unit = {
    actualizedComponents.map {
      prop => addColumn(columnName(prop), componentToJdbcType(prop))
    }
  }

  override protected[persistence] def dropSchemaBlocking(): Unit = ()

  override protected def updateColumnNames(isCreate: Boolean = true): Seq[String] = {
    super.updateColumnNames(isCreate) :+ "discriminator"
  }

  override protected def updateColumnValues(state: PState[P], isCreate: Boolean = true): Seq[AnyRef] = {
    val discriminatorValue = state.get.getClass.getSimpleName
    super.updateColumnValues(state, isCreate) :+ discriminatorValue
  }

  override protected def keyValSelectStatementConjunction(key: RealizedKey[M, P, _]): String =
    super.keyValSelectStatementConjunction(key) + s"\nAND\n  discriminator = '$discriminatorValue'"

  override protected def queryWhereClause(filterInfo: FilterInfo): String =
    super.queryWhereClause(filterInfo) + s"\nAND\n  discriminator = '$discriminatorValue'"

  private def discriminatorValue = pTypeKey.name

}