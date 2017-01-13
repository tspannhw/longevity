package longevity.persistence.sqlite

import longevity.exceptions.persistence.WriteConflictException
import longevity.persistence.PState
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import scala.concurrent.blocking

/** implementation of SQLiteRepo.update */
private[sqlite] trait SQLiteUpdate[P] {
  repo: SQLiteRepo[P] =>

  override def update(state: PState[P])(implicit context: ExecutionContext): Future[PState[P]] =
    Future {
      logger.debug(s"calling SQLiteRepo.update: $state")
      validateStablePartitionKey(state)
      val newState = state.update(persistenceConfig.optimisticLocking)
      val rowCount = blocking {
        bindUpdateStatement(newState, state.rowVersionOrNull).executeUpdate()
      }
      if (persistenceConfig.optimisticLocking && rowCount != 1) {
        throw new WriteConflictException(state)
      }
      logger.debug(s"done calling SQLiteRepo.update: $newState")
      newState
    }

  private def bindUpdateStatement(state: PState[P], rowVersion: AnyRef) = {
    val columnBindings = if (persistenceConfig.optimisticLocking) {
      updateColumnValues(state, isCreate = false) ++: whereBindings(state) :+ rowVersion
    } else {
      updateColumnValues(state, isCreate = false) ++: whereBindings(state)
    }
    logger.debug(s"invoking SQL: $updateSql with bindings: $columnBindings")
    val preparedStatement = connection.prepareStatement(updateSql)
    columnBindings.zipWithIndex.foreach { case (binding, index) =>
      preparedStatement.setObject(index, binding)
    }
    preparedStatement
  }

  private val updateSql = if (persistenceConfig.optimisticLocking) {
    withLockingUpdateSql
  } else {
    withoutLockingUpdateSql
  }

  private def columnAssignments = updateColumnNames(isCreate = false).map(c => s"$c = :$c").mkString(",\n  ")

  private def withoutLockingUpdateSql = s"""|
  |UPDATE $tableName
  |SET
  |  $columnAssignments
  |WHERE
  |  $whereAssignments
  |""".stripMargin

  private def withLockingUpdateSql = s"""|$withoutLockingUpdateSql
  |AND
  |  row_version = :row_version
  |""".stripMargin

}