@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

package ca.warp7.rt.context

typealias AnyMetric = Metric<*>
typealias MetricsSet = Set<Metric<*>>
typealias ContextAdapter = PipelineScope.() -> Unit
typealias MappedColumn = Pair<String, PipelineMapScope.(PipelineVector) -> Any?>
private typealias M = Map<String, *>

operator fun MutableSet<AnyMetric>.div(that: AnyMetric): MutableSet<AnyMetric> = this.apply { add(that) }
fun metricsOf(vararg metrics: AnyMetric): MetricsSet = metrics.toSet()
infix fun String.to(that: PipelineMapScope.(PipelineVector) -> Any?) = Pair(this, that)
fun PipelineScope.stream(vararg metrics: AnyMetric) = stream(metrics.toSet())
operator fun MetricsSet.get(metric: IntMetric): Int = 0 // TODO
operator fun MetricsSet.get(metric: StringMetric): String = "" // TODO
fun MetricsSet.matches(other: MetricsSet) = true // TODO
fun M.int(s: String, default: Int = 0) = this[s].let { if (it is Number) it.toInt() else default }
fun M.double(s: String, default: Double = 0.0) = this[s].let { if (it is Number) it.toDouble() else default }
fun M.str(s: String, default: String = "") = this[s] as? String ?: default
operator fun M.get(s: String, default: Int) = int(s, default)
operator fun M.get(s: String, default: Double = 0.0) = double(s, default)
operator fun M.get(s: String, default: String = "") = str(s, default)
fun M.count(s: String) = this[s].let {
    when (it) {
        is Collection<*> -> it.size
        is Map<*, *> -> it.size
        else -> 0
    }
}
