package ca.warp7.rt.context.tba

import ca.warp7.rt.context.ContextLoader
import ca.warp7.rt.context.ContextReference
import ca.warp7.rt.context.MetricsSet

class TBAContextLoader : ContextLoader {
    override fun loadContext(metricsSet: MetricsSet): ContextReference {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchByMetrics(metricsSet: MetricsSet): Iterable<ContextReference> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchByContext(contextReference: ContextReference): Iterable<ContextReference> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}