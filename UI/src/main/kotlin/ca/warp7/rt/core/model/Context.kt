package ca.warp7.rt.core.model

/**
 * The Context of a set of data is the scope and state in which the data is relevant for a
 * specific analysis. For example, to find out how some teams are performing at a specific event,
 * the context would be that event, whereas to find out how they performed in the entire season,
 * the context would be the year. Contexts can be defined by many metrics (there is a list below).
 * For example, they could be analyzing a district, an alliance, a set of scouts, etc. A Context
 * will always have a Data Source, a Version, and a delegated Adapter, which are the key to
 * integrating different sets of data
 */
interface Context {
    fun hasTable(vararg metrics: Metric<*>): Boolean
    fun tableOf(vararg metrics: Metric<*>): ContextTable?
    fun metricsMap(): Map<String, String>
    fun metricOf(metric: IntMetric): Int
    fun metricOf(metric: StringMetric): String
    fun matches(other: Context): Boolean
}