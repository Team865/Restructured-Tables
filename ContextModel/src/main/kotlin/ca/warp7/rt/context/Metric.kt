package ca.warp7.rt.context

open class Metric<T>(val name: String, val validator: (T) -> Boolean, val value: T? = null) {
    override fun equals(other: Any?): Boolean = name == other
    override fun hashCode(): Int = name.hashCode()
    operator fun invoke(of: T): Metric<T> = Metric(name, validator, value)
    operator fun contains(value: T): Boolean = validator.invoke(value)
    operator fun div(that: Metric<*>): MutableSet<Metric<*>> = mutableSetOf(this, that)
}