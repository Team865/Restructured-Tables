package ca.warp7.rt.context.tba

import ca.warp7.rt.context.ContextLoader
import krangl.dataFrameOf

class TBAContextLoader : ContextLoader {
    init {
        dataFrameOf("f")().sortedBy()
    }
}