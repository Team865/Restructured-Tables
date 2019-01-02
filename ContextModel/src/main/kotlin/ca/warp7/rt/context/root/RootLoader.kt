package ca.warp7.rt.context.root

import ca.warp7.rt.context.Context
import ca.warp7.rt.context.ContextPlugin
import ca.warp7.rt.context.ContextRoot
import ca.warp7.rt.context.MetricsSet
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

internal fun loadRoot(args: Array<String>) = loadRootImpl(
        os = System.getProperty("os.name").toLowerCase(),
        args = args)


internal fun loadRootImpl(os: String,
                          args: Array<String>) = when {
    os.contains("win") -> System.getProperty("user.home")
            .let {
                loadRootImpl(
                        config = "$it/warp7.meta.json",
                        contextPath = "$it/AppData/Local/Warp7/",
                        args = args)
            }
    os.contains("darwin") -> System.getProperty("user.home")
            .let {
                loadRootImpl(
                        config = "$it/warp7.meta.json",
                        contextPath = "$it/Library/Application Support/Warp7/",
                        args = args)
            }
    else -> rootImpl
}


internal fun loadRootImpl(config: String,
                          contextPath: String,
                          args: Array<String>) = if (args.isEmpty())
    loadRootImpl(
            config = config,
            contextPath = contextPath,
            default = "") else rootImpl


internal fun loadRootImpl(config: String,
                          contextPath: String,
                          default: String) = File(config)
        .apply { parentFile.mkdirs() }
        .apply { createNewFile() }
        .let {
            loadRootImpl(
                    config = it,
                    contextPath = contextPath,
                    default = default)
        }


internal fun loadRootImpl(config: File,
                          contextPath: String,
                          default: String) = loadRootImpl(
        config = config,
        data = Parser().parse(StringBuilder(config.readText().trim())) as JsonObject,
        contextPath = contextPath,
        default = default)


internal fun loadRootImpl(config: File,
                          data: JsonObject,
                          contextPath: String,
                          default: String) = loadRootImpl(
        config = config,
        data = data,
        contextPath = data.string(Metadata.contextPath) ?: contextPath,
        pluginPath = data.string(Metadata.pluginPath) ?: "${System.getProperty("user.dir")}/plugins/",
        default = default)


internal fun loadRootImpl(config: File,
                          data: JsonObject,
                          contextPath: String,
                          pluginPath: String,
                          default: String): ContextRoot {
    return rootImpl
}

internal class RootImpl(private val config: File,
                        private val contextPath: String,
                        private val internalData: JsonObject,
                        private val plugins: Array<ContextPlugin>) : ContextRoot {

    override val default: Context?
        get() = TODO("not implemented")

    override val available: Iterator<Context>
        get() = TODO("not implemented")

    override val data: MutableMap<String, Any?>
        get() = internalData

    override fun load(metricsSet: MetricsSet): Context? {
        TODO("not implemented")
    }

    override fun search(metricsSet: MetricsSet): Iterator<Context> {
        TODO("not implemented")
    }

    override fun save() {
        internalData[Metadata.lastSaved] = SimpleDateFormat("dd/MM/yy hh:mm:ss").format(Date())
        config.writeText(internalData.toJsonString(prettyPrint = true))
    }
}

internal val rootImpl = object : ContextRoot {
    override fun load(metricsSet: MetricsSet): Context? = null
    override fun search(metricsSet: MetricsSet): Iterator<Context> = emptyArray<Context>().iterator()
    override val available: Iterator<Context> = emptyArray<Context>().iterator()
    override val data: MutableMap<String, Any?> = mutableMapOf()
    override val default: Context? = null
    override fun save() = Unit
}