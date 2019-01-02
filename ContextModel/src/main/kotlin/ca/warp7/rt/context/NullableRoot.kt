package ca.warp7.rt.context

object NullableRoot : ContextRoot {
    override fun load(metricsSet: MetricsSet): Context? = null
    override fun search(metricsSet: MetricsSet): Array<Context> = emptyArray()
    override val available: Array<Context> = emptyArray()
    override val data: MutableMap<String, Any?> = mutableMapOf()
    override val default: Context? = null
    override val active: Context? = null
    override fun save() = Unit
}