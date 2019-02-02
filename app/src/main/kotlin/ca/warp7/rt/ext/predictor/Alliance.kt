package ca.warp7.rt.ext.predictor

abstract class Alliance(open val teams: List<Team>) {
    val datapoints = teams[0].datapoints

    fun sampleAll(): Map<String, Double> {
        var a: MutableMap<String, Double> = mutableMapOf()
        datapoints.forEach { a[it] = 0.0 }
        teams.forEach { team ->
            val sample = team.sampleAll()
            sample.forEach { k, v -> a[k] = a[k]!! + v }
        }
        return a.toMap()
    }

    open fun simMatch(): Double {
        throw Throwable("Unspecified behavior")
    }
}

class Alliance2018(override val teams: List<Team2018>) : Alliance(teams) {}


class Alliance2019(override val teams: List<Team2019>) : Alliance(teams) {
    override fun simMatch(): Double {
        val teamLocalTimes: MutableList<Double> = teams.map { 0.0 }.toMutableList()

        val panelPoints = 2
        val cargoPoints = 3

        val fL1 = Pair(12, 12)
        val fL2 = Pair(4, 4)
        val fL3 = Pair(4, 4)

        while (true in teamLocalTimes.map { it < 135 }) {
            teams.forEach { team ->
                val teamData = team.sampleAll()
                // what are you good at
                val rL1Efficiency = Pair(2 * teamData["Panel L1"]!!, 3 * teamData["Cargo L1"]!!)
                val rL2Efficiency = Pair(2 * teamData["Panel L2"]!!, 3 * teamData["Cargo L2"]!!)
                val rL3Efficiency = Pair(2 * teamData["Panel L3"]!!, 3 * teamData["Cargo L3"]!!)
                // decide

            }
        }

        return 2.0
    }
}
