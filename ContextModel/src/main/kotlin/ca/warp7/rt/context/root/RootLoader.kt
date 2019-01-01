package ca.warp7.rt.context.root

import ca.warp7.rt.context.Context
import ca.warp7.rt.context.ContextRoot
import ca.warp7.rt.context.MetricsSet

internal fun loadRootImpl(args: Array<String>) = System.getProperty("os.name").toLowerCase().run {
    when {
        contains("win") -> System.getProperty("user.home").let {
            loadRootImpl("$it/warp7.meta.json", "$it/AppData/Local/", args)
        }
        contains("darwin") -> System.getProperty("user.home").let {
            loadRootImpl("$it/warp7.meta.json", "$it/Library/Application Support", args)
        }
        else -> loadRootImpl()
    }
}

internal fun loadRootImpl() = object : ContextRoot {
    override fun load(metricsSet: MetricsSet): Context? = null
    override fun search(metricsSet: MetricsSet): Iterator<Context> = emptyArray<Context>().iterator()
    override val available: Iterator<Context> = emptyArray<Context>().iterator()
    override val data: MutableMap<String, Any?> = mutableMapOf()
    override val default: Context? = null
}

internal fun loadRootImpl(config: String, contextPath: String, args: Array<String>): ContextRoot {
    return loadRootImpl()
}

