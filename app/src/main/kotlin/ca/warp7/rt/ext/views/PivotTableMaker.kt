package ca.warp7.rt.ext.views

import krangl.DataFrame
import krangl.asStrings
import krangl.dataFrameOf
import krangl.eq

fun averageDf(df: DataFrame, by: String, colNames: List<String>): DataFrame = dataFrameOf(
        listOf(by) + colNames)(
        df[by].values().toSet().map { team ->
            listOf(team) + colNames.map { colName ->
                val a = df.filter { it[by] eq team!! }[colName].asStrings().toList().map {
                    var q = 0.0
                    if (it != null && it.toDoubleOrNull() != null) {
                        println(it.toDouble())
                        q = it.toDouble()
                    }
                    q
                }
                a.sum() / a.size
            }
        }.flatten()
)
