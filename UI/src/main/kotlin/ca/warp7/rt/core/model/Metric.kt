@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ca.warp7.rt.core.model

import java.time.LocalDate

/**
 * Specifies a defining quality of contexts and tables
 */
open class Metric<T>(val column: String, val validator: (T) -> Boolean) {
    operator fun invoke(of: T): Pair<Metric<T>, T> = this to of
    operator fun contains(value: T): Boolean = validator.invoke(value)
}

class IntMetric(column: String, validator: (Int) -> Boolean = { true }) : Metric<Int>(column, validator)
class StringMetric(column: String, validator: (String) -> Boolean = { true }) : Metric<String>(column, validator)

object Metrics {
    val teamNumber = IntMetric("Team") { it in 1..9999 }
    val matchNumber = IntMetric("Match") { it in 1..199 }
    val compLevel = StringMetric("Comp Level") { it in CompLevels.set }
    val year = IntMetric("Year") { it in 1992..LocalDate.now().year }
    val driverStation = IntMetric("Driver Station") { it in 1..3 }
    val alliance = StringMetric("alliance") { it == Alliance.Red || it == Alliance.Blue }
    val event = StringMetric("Event")
    val scout = StringMetric("Scout")
    val dataSource = StringMetric("Data Source")
    val user = StringMetric("User")
}

object CompLevels {
    const val QualificationMatch = "qm"
    const val EliminationFinal = "ef"
    const val QuarterFinal = "qf"
    const val SemiFinal = "sf"
    const val Final = "f"
    val set = setOf(QualificationMatch, EliminationFinal, QuarterFinal, SemiFinal, Final)
}

object Alliance {
    const val Red = "red"
    const val Blue = "blue"
}