package ca.warp7.rt.context

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.File
import java.net.URI
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.AccessController
import java.security.PrivilegedAction
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.JarFile

internal fun loadRoot(args: Array<String>) = loadRootImpl(
        os = System.getProperty("os.name").toLowerCase(),
        args = args)


internal fun loadRootImpl(os: String,
                          args: Array<String>) = when {
    os.contains("win") -> System.getProperty("user.home")
            .let {
                loadRootImpl(
                        config = "$it/warp7/meta.json",
                        contextPath = "$it/AppData/Local/Warp7/",
                        pluginPath = "$it/warp7/plugins/",
                        args = args)
            }
    os.contains("darwin") -> System.getProperty("user.home")
            .let {
                loadRootImpl(
                        config = "$it/Warp7/meta.json",
                        contextPath = "$it/Library/Application Support/Warp7/",
                        pluginPath = "$it/Warp7/plugins/",
                        args = args)
            }
    else -> rootImpl
}


internal fun loadRootImpl(config: String,
                          contextPath: String,
                          pluginPath: String,
                          args: Array<String>) = if (args.isEmpty())
    loadRootImpl(
            config = config,
            contextPath = contextPath,
            pluginPath = pluginPath,
            default = "") else rootImpl


internal fun loadRootImpl(config: String,
                          contextPath: String,
                          pluginPath: String,
                          default: String) = File(config)
        .apply { parentFile.mkdirs() }
        .apply { createNewFile() }
        .let {
            loadRootImpl(
                    config = it,
                    contextPath = contextPath,
                    pluginPath = pluginPath,
                    default = default)
        }


internal fun loadRootImpl(config: File,
                          contextPath: String,
                          pluginPath: String,
                          default: String) = loadRootImpl(
        config = config,
        data = Parser().parse(StringBuilder(config.readText().trim())) as JsonObject,
        contextPath = contextPath,
        pluginPath = pluginPath,
        default = default)


internal fun loadRootImpl(config: File,
                          data: JsonObject,
                          contextPath: String,
                          pluginPath: String,
                          default: String) = loadRootImpl(
        config = config,
        data = data,
        contextPath = Paths.get(data.string(Metadata.contextPath) ?: contextPath),
        pluginPath = Paths.get(data.string(Metadata.pluginPath) ?: pluginPath),
        default = default)


internal fun loadRootImpl(config: File,
                          data: JsonObject,
                          contextPath: Path,
                          pluginPath: Path,
                          default: String) = loadRootImpl(
        config = config,
        data = data,
        contextPath = contextPath,
        plugins = pluginPath.also { if (!Files.exists(it)) Files.createDirectories(it) }.run {
            Files
                    .list(this)
                    .iterator()
                    .asSequence()
                    .toList()
                    .filter { it.toString().endsWith(".jar") }
                    .map { it.toUri() }
                    .sorted().map { loadPlugin(it).filterNotNull() }
                    .flatten()
                    .toTypedArray()
        },
        default = default)

internal fun loadRootImpl(config: File,
                          data: JsonObject,
                          contextPath: Path,
                          plugins: Array<ContextPlugin>,
                          default: String): ContextRoot {
    return RootImpl(config, data, contextPath, plugins, default)
}

internal fun loadPlugin(uri: URI) = loadPlugin(uri, AccessController.doPrivileged(PrivilegedAction {
    URLClassLoader(arrayOf(uri.toURL()), ClassLoader.getSystemClassLoader());
}))

internal fun loadPlugin(uri: URI, classLoader: ClassLoader) = JarFile(File(uri)).use { jar ->
    jar
            .stream()
            .iterator()
            .asSequence()
            .toList()
            .filter { it.name.endsWith(".class") }
            .map { it.name.replace('/', '.') }
            .map { it.substring(0, it.length - 6) }
            .map { Class.forName(it, false, classLoader) }
            .filter { it.isAssignableFrom(ContextPlugin::class.java) }
            .mapNotNull { it.newInstance() as? ContextPlugin }
}


internal class RootImpl(private val config: File,
                        private val internalData: JsonObject,
                        private val contextPath: Path,
                        private val plugins: Array<ContextPlugin>,
                        private val defaultContext: String) : ContextRoot {

    override val default: Context?
        get() = TODO("not implemented")

    override val available: Iterator<Context>
        get() = TODO("not implemented")

    override val data: MutableMap<String, Any?>
        get() = internalData

    override val active: Context?
        get() = TODO("not implemented")

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
    override val active: Context? = null
    override fun save() = Unit
}