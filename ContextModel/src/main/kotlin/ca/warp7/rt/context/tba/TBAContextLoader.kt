package ca.warp7.rt.context.tba

import ca.warp7.rt.context.AnyMetric
import ca.warp7.rt.context.ContextLoader
import ca.warp7.rt.context.ContextReference

class TBAContextLoader : ContextLoader {
    override fun searchByMetrics(vararg metric: AnyMetric): Iterable<ContextReference> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchByContext(contextReference: ContextReference): Iterable<ContextReference> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadContext(vararg metric: AnyMetric): ContextReference {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}