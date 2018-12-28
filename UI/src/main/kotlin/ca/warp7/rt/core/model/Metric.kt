@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ca.warp7.rt.core.model

import java.time.LocalDate

/**
 * Specifies a defining quality of contexts and tables
 */
open class Metric<T>(val column: String, val validator: (T) -> Boolean) {
    operator fun invoke(of: T): Pair<Metric<T>, T> = this to of
    operator fun contains(value: T): Boolean = validator.invoke(value)
    operator fun div(that: Metric<*>): MutableSet<Metric<*>> = mutableSetOf(this, that)
}

operator fun MutableSet<Metric<*>>.div(that: Metric<*>): MutableSet<Metric<*>> = this.apply { add(that) }

class IntMetric(column: String, validator: (Int) -> Boolean = { true }) : Metric<Int>(column, validator)
class StringMetric(column: String, validator: (String) -> Boolean = { true }) : Metric<String>(column, validator)

val teamNumber_ = IntMetric("Team") { it in 1..9999 }
val matchNumber_ = IntMetric("Match") { it in 1..199 }
val compLevel_ = StringMetric("Comp Level") { it in CompLevels.set }
val year_ = IntMetric("Year") { it in 1992..LocalDate.now().year }
val driverStation_ = IntMetric("Driver Station") { it in 1..3 }
val alliance_ = StringMetric("alliance_") { it == Alliance.Red || it == Alliance.Blue }
val event_ = StringMetric("Event")
val scout_ = StringMetric("Scout")
val dataSource_ = StringMetric("Data Source")
val user_ = StringMetric("User")

object CompLevels {
    const val QualificationMatch = "qm"
    const val Unknown = "ef"
    const val QuarterFinal = "qf"
    const val SemiFinal = "sf"
    const val Final = "f"
    val set = setOf(QualificationMatch, Unknown, QuarterFinal, SemiFinal, Final)
}

object Alliance {
    const val Red = "red"
    const val Blue = "blue"
}