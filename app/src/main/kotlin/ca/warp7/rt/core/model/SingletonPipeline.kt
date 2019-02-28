package ca.warp7.rt.core.model

import ca.warp7.rt.api.ContextAdapter
import ca.warp7.rt.api.ContextMonitor
import ca.warp7.rt.api.ContextReceiver
import ca.warp7.rt.api.Pipeline
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat

object SingletonPipeline : Pipeline {
    override val name: String get() = "Singleton"
    override val adapter: ContextAdapter? = null
    override val monitor: ContextMonitor? = null
    override fun lastUpdated(): Long = System.currentTimeMillis() / 1000

    private var df: DataFrame? = null
    private var open = false

    override fun open() {
        Thread {
            val inputStream = javaClass.getResourceAsStream("/ca/warp7/rt/res/test.csv")
            df = DataFrame.readDelim(
                    inStream = inputStream,
                    format = CSVFormat.DEFAULT.withHeader().withNullString(""),
                    isCompressed = false,
                    colTypes = mapOf())
            open = true
        }.start()
    }

    override fun close() {
    }

    override fun merge(other: Pipeline) {
    }

    override fun <T> convert(receiver: ContextReceiver<T>): T {
        return receiver.invoke(SingletonReceiverScope())
    }
}