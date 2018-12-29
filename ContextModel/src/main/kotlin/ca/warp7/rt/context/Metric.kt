@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ca.warp7.rt.context

import java.time.LocalDate

open class Metric<T>(val name: String, val validator: (T) -> Boolean, val value: T? = null) {
    operator fun invoke(of: T): Metric<T> = Metric(name, validator, value)
    operator fun contains(value: T): Boolean = validator.invoke(value)
    operator fun div(that: Metric<*>): MutableSet<Metric<*>> = mutableSetOf(this, that)
}

typealias AnyMetric = Metric<*>

operator fun MutableSet<Metric<*>>.div(that: Metric<*>): MutableSet<Metric<*>> = this.apply { add(that) }

class IntMetric(name: String, validator: (Int) -> Boolean = { true }) : Metric<Int>(name, validator)
class StringMetric(name: String, validator: (String) -> Boolean = { true }) : Metric<String>(name, validator)

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

private val currentYear = LocalDate.now().year

val teamNumber_ = IntMetric("Team") { it in 1..9999 }
val matchNumber_ = IntMetric("Match") { it in 1..199 }
val compLevel_ = StringMetric("Comp Level") { it in CompLevels.set }
val year_ = IntMetric("Year") { it in 1992..currentYear }
val driverStation_ = IntMetric("Driver Station") { it in 1..3 }
val alliance_ = StringMetric("alliance_") { it == Alliance.Red || it == Alliance.Blue }
val event_ = StringMetric("Event")
val scout_ = StringMetric("Scout")
val dataSource_ = StringMetric("Data Source")
val user_ = StringMetric("User")

infix fun String.to(that: (PipelineVector) -> Any?) = Pair(this, that)

val PipelineVector.teamNumber get() = this.getMetric(teamNumber_)
val PipelineVector.matchNumber get() = this.getMetric(matchNumber_)
val PipelineVector.compLevel get() = this.getMetric(compLevel_)
val PipelineVector.year get() = this.getMetric(year_)
val PipelineVector.driverStation get() = this.getMetric(driverStation_)
val PipelineVector.alliance get() = this.getMetric(alliance_)
val PipelineVector.event get() = this.getMetric(event_)
val PipelineVector.scout get() = this.getMetric(scout_)
val PipelineVector.dataSource get() = this.getMetric(dataSource_)
val PipelineVector.user get() = this.getMetric(user_)