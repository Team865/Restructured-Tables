@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

package ca.warp7.rt.context

typealias AnyMetric = Metric<*>
typealias MetricsSet = Set<Metric<*>>
typealias ContextAdapter = PipelineScope.() -> Unit
typealias MappedColumn = Pair<String, PipelineMapScope.(PipelineVector) -> Any?>
private typealias M = Map<String, *>
internal typealias MutableMetrics = MutableSet<AnyMetric>

operator fun MutableMetrics.div(that: AnyMetric): MutableMetrics = this.apply { add(that) }
operator fun MutableMetrics.div(that: MetricsSet): MutableMetrics = this.apply { addAll(that) }
fun metricsOf(vararg metrics: AnyMetric): MetricsSet = metrics.toSet()
infix fun String.to(that: PipelineMapScope.(PipelineVector) -> Any?) = Pair(this, that)
fun PipelineScope.stream(vararg metrics: AnyMetric) = stream(metrics.toSet())
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

operator fun MetricsSet.get(metric: IntMetric, default: Int = 0): Int = this
        .filterIsInstance<IntMetric>()
        .firstOrNull { it == metric }
        ?.let { it.value ?: default } ?: default

operator fun MetricsSet.get(metric: StringMetric, default: String = ""): String = this
        .filterIsInstance<StringMetric>()
        .firstOrNull { it == metric }
        ?.let { it.value ?: default } ?: default

// FIXME
fun MetricsSet.matches(that: MetricsSet) = this.all { i -> that.firstOrNull { it == i }?.value == i.value }