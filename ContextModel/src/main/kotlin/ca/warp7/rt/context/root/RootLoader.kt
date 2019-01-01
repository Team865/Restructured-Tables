package ca.warp7.rt.context.root

import ca.warp7.rt.context.Context
import ca.warp7.rt.context.ContextRoot
import ca.warp7.rt.context.MetricsSet
import java.io.File

internal fun loadRootImpl(args: Array<String>) = loadRootImpl(System.getProperty("os.name").toLowerCase(), args)

internal fun loadRootImpl(os: String, args: Array<String>) = when {
    os.contains("win") -> System.getProperty("user.home")
            .let { loadRootImpl("$it/warp7.meta.json", "$it/AppData/Local/", args) }
    os.contains("darwin") -> System.getProperty("user.home")
            .let { loadRootImpl("$it/warp7.meta.json", "$it/Library/Application Support", args) }
    else -> loadRootImpl()
}

internal fun loadRootImpl() = object : ContextRoot {
    override fun load(metricsSet: MetricsSet): Context? = null
    override fun search(metricsSet: MetricsSet): Iterator<Context> = emptyArray<Context>().iterator()
    override val available: Iterator<Context> = emptyArray<Context>().iterator()
    override val data: MutableMap<String, Any?> = mutableMapOf()
    override val default: Context? = null
}

internal fun loadRootImpl(config: String, contextPath: String, args: Array<String>) = if (args.isEmpty())
    loadRootImpl(config, contextPath) else loadRootImpl()

internal fun loadRootImpl(config: String, contextPath: String) = File(config)
        .apply { parentFile.mkdirs() }
        .apply { createNewFile() }
        .let { loadRootImpl(it, contextPath) }

internal fun loadRootImpl(config: File, contextPath: String) = loadRootImpl()
