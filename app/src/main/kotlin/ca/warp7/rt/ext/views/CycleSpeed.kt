import krangl.DataFrame
import krangl.dataFrameOf
import krangl.eq
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression

fun DataFrame.calcCycles(by: String, colNames: List<String>): DataFrame {
    val teams = this[by].values().toSet().toList()

    val values = teams
            .map { team -> this.filter { it[by] eq team!! }.selectIf { it.name != by && it.name in colNames } }
            .map { df ->
                df.rows.map { row ->
                    row.values.map {
                        when (it) {
                            is Number -> it.toDouble()
                            is String -> if (it.matches("-?\\d+(\\.\\d+)?".toRegex())) {
                                it.toDouble()
                            } else {
                                0.0
                            }
                            else -> 0.0
                        }
                    }.toDoubleArray()
                }.toTypedArray()
            }
    println(values)
    val results = values.mapIndexed { i, x ->
        listOf(teams[i]) + when {
            x[0].size + 1 <= x.size -> {
                val y = x.map { 135.0 }.toDoubleArray()

                val reg = OLSMultipleLinearRegression()
                reg.isNoIntercept = true
                reg.newSampleData(y, x)

                reg.estimateRegressionParameters().toList() + reg.estimateRegressionParametersStandardErrors().toList()
            }
            else -> List(colNames.size * 2) { -1.0 }
        }
    }
    return dataFrameOf(listOf(by) + colNames.map { it + " times" } + colNames.map { "+-" + it })(results.flatten())
}

fun main() {
    val df: DataFrame = dataFrameOf(
            "Team", "Cargo", "Hatch", "Climb")(
            "865", "1", 2.0, 3.0,
            "865", "0.0", 2.0, 5.0,
            "865", "3.0", 1.0, 0.0,
            "865", "2.0", 3.0, 1.0,
            "2056", "2.0", 4.0, 1.0
    )
    println(df.calcCycles("Team", listOf("Cargo", "Hatch")))
/*
    val by = "Team"

    val values = df[by].values().toSet()
            .map { team -> df.filter { it[by] eq team!! }.selectIf { it.name != by } }
            .map { df ->
                df.rows.map { row ->
                    row.values.map {
                        when (it) {
                            is Number -> it.toDouble()
                            else -> 0.0
                        }
                    }.toDoubleArray()
                }.toTypedArray()
            }

    println(values.map { x ->
        if (x[0].size + 1 <= x.size) {
            val y = x.map { 135.0 }.toDoubleArray()

            val reg = OLSMultipleLinearRegression()
            reg.isNoIntercept = true
            reg.newSampleData(y, x)

            listOf(reg.estimateRegressionParameters().toList(),
                    reg.estimateRegressionParametersStandardErrors().toList())
        } else {
            listOf(emptyList())
        }
    })*/
}