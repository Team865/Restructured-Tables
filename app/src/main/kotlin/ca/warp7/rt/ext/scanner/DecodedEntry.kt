package ca.warp7.rt.ext.scanner

import ca.warp7.android.scouting.v5.entry.Board
import ca.warp7.android.scouting.v5.entry.DataPoint
import ca.warp7.android.scouting.v5.entry.V5Entry
import ca.warp7.android.scouting.v5.entry.toBoard
import javax.xml.bind.DatatypeConverter

data class DecodedEntry(override val encoded: String) : V5Entry {
    private val split = encoded.split(":")
    override val match = split[0]
    override val team = split[1]
    override val scout = split[2]
    override val board = split[3].toBoard() ?: Board.R1
    override val timestamp = split[4].toInt(16)
    override val undone = split[5].toInt()
    override val comments = split[7]
    override val dataPoints = DatatypeConverter.parseBase64Binary(split[6]).map { it.toInt() }
            .run { (0 until size / 3).map { DataPoint(get(it * 3), get(it * 3 + 1), get(it * 3 + 2)) } }
    private val length = dataPoints.size

    override fun count(type: Int) = dataPoints.subList(0, length).filter { it.type == type }.size
    override fun lastValue(type: Int) = dataPoints.subList(0, length).lastOrNull { it.type == type }
    override fun focused(type: Int, time: Int) = dataPoints.any { it.type == type && it.time == time }
}
