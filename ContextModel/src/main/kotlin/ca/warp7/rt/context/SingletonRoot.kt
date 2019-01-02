package ca.warp7.rt.context

import com.beust.klaxon.JsonObject
import java.io.File
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

internal class SingletonRoot(private val config: File,
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