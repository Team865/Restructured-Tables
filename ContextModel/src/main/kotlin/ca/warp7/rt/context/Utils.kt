@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

package ca.warp7.rt.context

open class Metric<T>(val name: String, val validator: (T) -> Boolean, val value: T? = null) {
    override fun equals(other: Any?): Boolean = name == other
    override fun hashCode(): Int = name.hashCode()
    operator fun invoke(of: T): Metric<T> = Metric(name, validator, value)
    operator fun contains(value: T): Boolean = validator.invoke(value)
    operator fun div(that: Metric<*>): MutableSet<Metric<*>> = mutableSetOf(this, that)
}

class IntMetric(name: String, validator: (Int) -> Boolean = { true }) : Metric<Int>(name, validator)
class StringMetric(name: String, validator: (String) -> Boolean = { true }) : Metric<String>(name, validator)
typealias AnyMetric = Metric<*>
typealias MetricsSet = Set<Metric<*>>
typealias ContextAdapter = PipelineScope.() -> Unit
typealias MappedColumn = Pair<String, PipelineMapScope.(PipelineVector) -> Any?>
private typealias M = Map<String, *>

operator fun MutableSet<AnyMetric>.div(that: AnyMetric): MutableSet<AnyMetric> = this.apply { add(that) }
fun metricsOf(vararg metrics: AnyMetric): MetricsSet = metrics.toSet()

infix fun String.to(that: PipelineMapScope.(PipelineVector) -> Any?) = Pair(this, that)
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

fun PipelineScope.stream(vararg metrics: AnyMetric) = stream(metrics.toSet())