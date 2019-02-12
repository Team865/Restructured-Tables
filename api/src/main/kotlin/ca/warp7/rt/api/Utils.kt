@file:Suppress("unused", "MemberVisibilityCanBePrivate", "UNUSED_PARAMETER")

package ca.warp7.rt.api

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.HBox
import java.io.IOException

typealias AnyMetric = BaseMetric<*>
typealias MetricsSet = Set<BaseMetric<*>>
typealias ContextAdapter = PipelineAdapterScope.() -> Unit
typealias ContextReceiver<T> = PipelineReceiverScope.() -> T
typealias ContextMonitor = PipelineMonitorScope.() -> Unit
typealias MappedColumn = Pair<String, PipelineMappingScope.(PipelineStreamVector) -> Any?>
internal typealias M = Map<String, *>
internal typealias MutableMetrics = MutableSet<AnyMetric>

operator fun MutableMetrics.div(that: AnyMetric): MutableMetrics = this.apply { add(that) }
operator fun MutableMetrics.div(that: MetricsSet): MutableMetrics = this.apply { addAll(that) }
fun metricsOf(vararg metrics: AnyMetric): MetricsSet = metrics.toSet()
infix fun String.to(that: PipelineMappingScope.(PipelineStreamVector) -> Any?) = Pair(this, that)
fun PipelineAdapterScope.stream(vararg metrics: AnyMetric) = stream(metrics.toSet())
fun M.int(s: String, default: Int = 0) = this[s].let { if (it is Number) it.toInt() else default }
fun M.double(s: String, default: Double = 0.0) = this[s].let { if (it is Number) it.toDouble() else default }
fun M.str(s: String, default: String = "") = this[s] as? String ?: default
operator fun M.get(s: String, default: Int) = int(s, default)
operator fun M.get(s: String, default: Double = 0.0) = double(s, default)
operator fun M.get(s: String, default: String = "") = str(s, default)

fun M.count(s: String) = this[s].let {
    when (it) {
        is Collection<*> -> it.size
        is Map<*, *> -> it.size
        else -> 0
    }
}

operator fun MetricsSet.get(metric: IntMetric, default: Int = 0): Int = this
        .filterIsInstance<IntMetric>()
        .firstOrNull { it == metric }
        ?.let { it.value ?: default } ?: default

operator fun MetricsSet.get(metric: StringMetric, default: String = ""): String = this
        .filterIsInstance<StringMetric>()
        .firstOrNull { it == metric }
        ?.let { it.value ?: default } ?: default

// FIXME
fun MetricsSet.matches(that: MetricsSet) = this.all { i -> that.firstOrNull { it == i }?.value == i.value }

fun loadParent(resFile: String): Parent {
    return try {
        val loader = FXMLLoader()
        loader.location = caller.getResource(resFile)
        loader.load()
    } catch (e: IOException) {
        e.printStackTrace()
        HBox()
    }
}

private val caller: Class<*>
    get() {
        val caller = Thread.currentThread().stackTrace[3]
        return try {
            Class.forName(caller.className)
        } catch (e: ClassNotFoundException) {
            Context::class.java
        }
    }

fun <T> loadParent(resFile: String, controllerCallback: (controller: T) -> Unit): Parent {
    return try {
        val loader = FXMLLoader()
        loader.location = caller.getResource(resFile)
        val parent = loader.load<Parent>()
        controllerCallback.invoke(loader.getController())
        parent
    } catch (e: IOException) {
        e.printStackTrace()
        HBox()
    }
}