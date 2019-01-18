package ca.warp7.rt.context.model

import ca.warp7.rt.context.api.*
import com.beust.klaxon.JsonObject
import java.io.File
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.util.*

@Suppress("unused")
internal class SingletonRoot(private val config: File,
                             private val internalData: JsonObject,
                             private val contextPath: Path,
                             private val plugins: Array<ContextPlugin>,
                             private val defaultContext: String) : ContextRoot {

    private val context = Context("Entries", teamNumber_ / scout_ / board_ / match_,
            SingletonPipeline, this)

    override val default: Context?
        get() = context

    override val available: Array<Context>
        get() = arrayOf(context)

    override val data: MutableMap<String, Any?>
        get() = internalData

    override val active: Context? = null

    override fun load(metricsSet: MetricsSet): Context? {
        return when {
            context.metrics.matches(metricsSet) -> context
            else -> null
        }
    }

    override fun search(metricsSet: MetricsSet): Array<Context> = arrayOf(context)

    override fun save() {
        internalData[Metadata.lastSaved] = SimpleDateFormat("dd/MM/yy hh:mm:ss").format(Date())
        config.writeText(internalData.toJsonString(prettyPrint = true))
    }
}