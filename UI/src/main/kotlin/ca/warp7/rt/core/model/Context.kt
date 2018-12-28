package ca.warp7.rt.core.model

interface Context {
    fun hasTable(vararg metrics: Metric<*>): Boolean
    fun tableOf(vararg metrics: Metric<*>): ContextTable?
    fun metricsMap(): Map<String, String>
    fun metricOf(metric: IntMetric): Int
    fun metricOf(metric: StringMetric): String
    fun matches(other: Context): Boolean
}