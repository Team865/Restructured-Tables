
import koma.matrix.Matrix
import krangl.DataFrame
import krangl.dataFrameOf
import krangl.eq

fun main() {
    val df: DataFrame = dataFrameOf(
            "Team", "Cargo", "Hatch")(
            "865", "1", "2",
            "865", "2", "2",
            "865", "3", "1",
            "2056", "2", "3",
            "2056", "2", "4"
    )

    val by = "Team"
    val colNames: List<String> = listOf("Cargo", "Hatch")

    println(df[by].values().toSet().map { team -> colNames.map { colName -> df.filter { it[by] eq team!! } } })


    val data: List<List<Double>> = listOf(
            listOf(0.0, 0.0, 6.0),
            listOf(2.0, 2.0, 3.0)
    )

    val x: Matrix<Double> = Matrix.doubleFactory.create(data.map { it.toDoubleArray() }.toTypedArray())
    val y: Matrix<Double> = Matrix.doubleFactory.create(data.map { doubleArrayOf(135.0) }.toTypedArray())

    println((x.T * x).pinv() * x.T * y)
}