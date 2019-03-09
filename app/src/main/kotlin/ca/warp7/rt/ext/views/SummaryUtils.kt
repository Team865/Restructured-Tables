package ca.warp7.rt.ext.views

import krangl.DataFrame
import krangl.dataFrameOf
import krangl.eq
import krangl.mean

fun DataFrame.averageBy(by: String, colNames: List<String>): DataFrame = dataFrameOf(
        listOf(by) + colNames)(
        this[by].values().toSet().map { team ->
            listOf(team) + colNames.map { colName -> this.filter { it[by] eq team!! }[colName].mean(removeNA = false) }
        }.flatten()
)

