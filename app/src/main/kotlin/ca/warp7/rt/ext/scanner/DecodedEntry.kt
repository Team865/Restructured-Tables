package ca.warp7.rt.ext.scanner

import ca.warp7.android.scouting.v5.entry.Board
import ca.warp7.android.scouting.v5.entry.DataPoint
import ca.warp7.android.scouting.v5.entry.Entry
import ca.warp7.android.scouting.v5.entry.toBoard

data class DecodedEntry(override val encoded: String) : Entry {
    private val split = encoded.split(":")
    override val match: String = split[0]
    override val team: String = split[1]
    override val board: Board = split[3].toBoard() ?: Board.R1
    override val timestamp: Int = split[4].toInt(16)
    override val undone: Int = split[5].toInt()
    override val scout: String = split[2]
    override val dataPoints: List<DataPoint>
        get() = TODO("not implemented")
    override val comments: String
        get() = ""

    override fun count(type: Int): Int {
        TODO("not implemented")
    }

    override fun lastValue(type: Int): DataPoint? {
        TODO("not implemented")
    }

    override fun focused(type: Int): Boolean {
        TODO("not implemented")
    }
}
