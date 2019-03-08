package ca.warp7.rt.ext.views

import krangl.DataFrame
import krangl.dataFrameOf
import krangl.eq
import krangl.mean

fun averageDf(df: DataFrame, by: String, colNames: List<String>): DataFrame = dataFrameOf(
        listOf(by) + colNames)(
        df[by].values().toSet().map { team ->
            listOf(team) + colNames.map { colName -> df.filter { it[by] eq team!! }[colName].mean(removeNA = false) }
        }.flatten()
)

