package ca.warp7.rt.context

import ca.warp7.rt.context.api.ContextPlugin
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
    else -> NullableRoot
}


internal fun loadRootImpl(config: String,
                          contextPath: String,
                          pluginPath: String,
                          args: Array<String>) = if (args.isEmpty())
    loadRootImpl(
            config = config,
            contextPath = contextPath,
            pluginPath = pluginPath,
            default = "") else NullableRoot


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
        data = config.readText().trim().let {
            if (it.isEmpty()) JsonObject()
            else Parser().parse(StringBuilder(config.readText().trim())) as JsonObject
        },
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
                    .sorted().map { loadPlugin(it) }
                    .flatten()
                    .toTypedArray()
        },
        default = default)


internal fun loadPlugin(uri: URI) = loadPlugin(uri, AccessController.doPrivileged(PrivilegedAction {
    URLClassLoader(arrayOf(uri.toURL()), ClassLoader.getSystemClassLoader())
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

internal fun loadRootImpl(config: File,
                          data: JsonObject,
                          contextPath: Path,
                          plugins: Array<ContextPlugin>,
                          default: String) = SingletonRoot(config, data, contextPath, plugins, default)