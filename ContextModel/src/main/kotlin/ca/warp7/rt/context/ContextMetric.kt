@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

package ca.warp7.rt.context

import java.time.LocalDate

open class Metric<T>(val name: String, val validator: (T) -> Boolean, val value: T? = null) {
    override fun equals(other: Any?): Boolean = name == other
    override fun hashCode(): Int = name.hashCode()
    operator fun invoke(of: T): Metric<T> = Metric(name, validator, value)
    operator fun contains(value: T): Boolean = validator.invoke(value)
    operator fun div(that: Metric<*>): MutableSet<Metric<*>> = mutableSetOf(this, that)
}

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

typealias AnyMetric = Metric<*>
typealias MetricsSet = Set<Metric<*>>

private typealias V = PipelineVector
private typealias M = Map<String, *>

operator fun MutableSet<AnyMetric>.div(that: AnyMetric): MutableSet<AnyMetric> = this.apply { add(that) }
fun metricsOf(vararg metrics: AnyMetric): MetricsSet = metrics.toSet()

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

val V.teamNumber get() = this.getMetric(teamNumber_)
val V.matchNumber get() = this.getMetric(matchNumber_)
val V.compLevel get() = this.getMetric(compLevel_)
val V.year get() = this.getMetric(year_)
val V.driverStation get() = this.getMetric(driverStation_)
val V.alliance get() = this.getMetric(alliance_)
val V.event get() = this.getMetric(event_)
val V.scout get() = this.getMetric(scout_)
val V.dataSource get() = this.getMetric(dataSource_)
val V.user get() = this.getMetric(user_)

fun M.int(s: String, default: Int = 0) = this[s].let { if (it is Number) it.toInt() else default }
fun M.double(s: String, default: Double = 0.0) = this[s].let { if (it is Number) it.toDouble() else default }
fun M.str(s: String, default: String = "") = this[s] as? String ?: default
operator fun M.get(s: String, default: Int) = int(s, default)
operator fun M.get(s: String, default: Double = 0.0) = double(s, default)
operator fun M.get(s: String, default: String = "") = str(s, default)
fun M.count(s: String) = this[s].let {
    when (it) {
        is Collection<*> -> it.count()
        is Map<*, *> -> it.size
        else -> 0
    }
}