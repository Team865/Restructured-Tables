@file:Suppress("MemberVisibilityCanBePrivate")

package ca.warp7.rt.context

open class BaseMetric<T>(val name: String, val validator: (T) -> Boolean, val value: T? = null) {
    override fun equals(other: Any?): Boolean = name == other
    override fun hashCode(): Int = name.hashCode()
    operator fun invoke(of: T): BaseMetric<T> = BaseMetric(name, validator, of)
    operator fun contains(value: T): Boolean = validator.invoke(value)
    operator fun div(that: AnyMetric): MutableMetrics = mutableSetOf(this, that)
    operator fun div(that: MetricsSet): MutableMetrics = mutableSetOf(this as AnyMetric).apply { addAll(that) }
}