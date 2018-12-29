@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

package ca.warp7.rt.context

import java.time.LocalDate

open class Metric<T>(val name: String, val validator: (T) -> Boolean, val value: T? = null) {
    operator fun invoke(of: T): Metric<T> = Metric(name, validator, value)
    operator fun contains(value: T): Boolean = validator.invoke(value)
    operator fun div(that: Metric<*>): MutableSet<Metric<*>> = mutableSetOf(this, that)
}

typealias AnyMetric = Metric<*>
typealias MetricsSet = Set<Metric<*>>

operator fun MutableSet<AnyMetric>.div(that: AnyMetric): MutableSet<AnyMetric> = this.apply { add(that) }

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

private typealias PV = PipelineVector

val PV.teamNumber get() = this.getMetric(teamNumber_)
val PV.matchNumber get() = this.getMetric(matchNumber_)
val PV.compLevel get() = this.getMetric(compLevel_)
val PV.year get() = this.getMetric(year_)
val PV.driverStation get() = this.getMetric(driverStation_)
val PV.alliance get() = this.getMetric(alliance_)
val PV.event get() = this.getMetric(event_)
val PV.scout get() = this.getMetric(scout_)
val PV.dataSource get() = this.getMetric(dataSource_)
val PV.user get() = this.getMetric(user_)

fun Map<String, *>.int(s: String) = 0
fun Map<String, *>.str(s: String) = ""
fun Map<String, *>.double(s: String) = 0.0
fun Map<String, *>.count(s: String) = 0