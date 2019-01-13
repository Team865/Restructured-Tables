package ca.warp7.rt.ext.predictor

open class Alliance(open val teams: List<Team>) {
    val datapoints = teams[0].datapoints

    fun sampleAll(): Map<String, Double> {
        var a: MutableMap<String, Double> = mutableMapOf()
        datapoints.forEach { a[it] = 0.0 }
        teams.forEach { team ->
            val sample = team.sampleAll()
            sample.forEach { k,v -> a[k] = a[k]!! + v }
        }
        return a.toMap()
    }
}

class Alliance2018(override val teams: List<Team2018>) : Alliance(teams) {}

class Alliance2019(override val teams: List<Team2019>) : Alliance(teams) {}
