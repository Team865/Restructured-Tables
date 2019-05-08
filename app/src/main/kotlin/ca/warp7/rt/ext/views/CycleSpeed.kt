import krangl.DataFrame
import krangl.dataFrameOf
import krangl.eq
import org.apache.commons.math3.exception.MathIllegalArgumentException
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression

fun DataFrame.calcCycles(by: String, colNames: List<String>): DataFrame {
    val teams = this[by].values().toSet().toList()

    val values = teams
            .map { team -> this.filter { it[by] eq team!! }.selectIf { it.name != by && it.name in colNames } }
            .map { df ->
                df.rows.map { row ->
                    row.values
                            .map {
                                when (it) {
                                    is Number -> it.toDouble()
                                    is String -> when {
                                        it matches Regex("-?\\d+(\\.\\d+)?") -> it.toDouble()
                                        else -> 0.0
                                    }
                                    else -> 0.0
                                }
                            }
                            .toDoubleArray()
                }
                        .filter { row -> !row.all { value -> value == 0.0 } }
                        .toTypedArray()
            }

    val results = values.mapIndexed { i, x ->
        listOf(teams[i]) + when {
            (if (x.isEmpty()) 0 else x[0].size + 1) <= x.size -> {
//                val trans = Array(x[0].size) { DoubleArray(x.size) }
//                for (k in 0 until x[0].size) {
//                    for (j in 0 until x.size) trans[k][j] = x[j][k]
//                }
//                val a = trans.map { false !in it.map { it == 0.0 } }
//                println(a)

//                val x = x.mapIndexedNotNull { j, value ->
//                    if (a[j]) value else null
//                }.toTypedArray()

                val y = x.map { 135.0 }.toDoubleArray()
                var res: List<Any>
                try {
                    val reg = OLSMultipleLinearRegression()
                    reg.isNoIntercept = true
                    reg.newSampleData(y, x)
                    res = reg.estimateRegressionParameters()!!.toList() + reg.estimateRegressionParametersStandardErrors()!!.toList()
                } catch (e: MathIllegalArgumentException) {
                    res = List(colNames.size * 2) { "" }
                }
                res
//                x.mapIndexed { j, value ->
//                    if (a[j]) res[j] else 9998.0
//                }
            }
            else -> List(colNames.size * 2) { "" }
        }
    }
    results.forEach { println(it.size) }
    return dataFrameOf(listOf(by) + colNames.map { "$it times" } + colNames.map { "+-$it" })(results.flatten().map {
        when (it) {
            is Number -> {
                println("num $it")
                it.toDouble()
            }
            is String -> {
                println("str $it")
                when (it.toDoubleOrNull()) {
                    null -> 9999.0
                    else -> it.toDoubleOrNull()
                }
            }
            else -> 9999.0
        }
    })
}

fun main() {
    val df: DataFrame = dataFrameOf(
            "Team", "Cargo", "Hatch", "Climb")(
            "865", 1.0, 2.0, 3.0,
            "865", 0.0, 2.0, 5.0,
            "865", 3.0, 1.0, 0.0,
            "865", 4.0, 0.0, 1.0,
            "865", 2.0, 1.0, 2.0,
            "865", 1.0, 2.0, 3.0,
            "2056", 0.0, 2.0, 5.0,
            "2056", 3.0, 1.0, 0.0,
            "2056", 4.0, 0.0, 1.0,
            "2056", 2.0, 1.0, 2.0
    )
    println(df.calcCycles("Team", listOf("Cargo", "Hatch")))
    println(df.calcCycles("Team", listOf("Cargo", "Hatch", "Climb")))
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