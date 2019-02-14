package ca.warp7.rt.ext.predictor

abstract class Alliance(open val teams: List<Team>)

data class TeamStats(var hatch: Int, var cargo: Int)

class Alliance2019(override val teams: List<Team2019>) : Alliance(teams) {
    private fun chooseStrat(panelL1: Double, panelL2: Double, panelL3: Double,
                            cargoL1: Double, cargoL2: Double, cargoL3: Double,
                            l1State: Pair<Boolean, Boolean>, l2State: Pair<Boolean, Boolean>, l3State: Pair<Boolean, Boolean>)
            : Pair<Int, Pair<Boolean, Boolean>> {
        val efficiencies: MutableList<Double> = mutableListOf(
                2 / panelL1, 2 / panelL2, 2 / panelL3,
                3 / cargoL1, 3 / cargoL2, 3 / cargoL3,
                5 / (panelL1 + cargoL1), 5 / (panelL2 + cargoL2), 5 / (panelL3 + cargoL3)
        )
        listOf(l1State, l2State, l3State).forEachIndexed { i, it ->
            when (it) {
                Pair(false, false) -> {
                    efficiencies[0 + i] = 0.0
                    efficiencies[3 + i] = 0.0
                    efficiencies[6 + i] = 0.0
                }
                Pair(false, true) -> {
                    efficiencies[0 + i] = 0.0
                    efficiencies[6 + i] = 0.0
                }
                Pair(true, false) -> {
                    efficiencies[3 + i] = 0.0
                }
            }
        }

        val strategy = efficiencies.indexOf(efficiencies.max())

        return Pair(strategy % 3, Pair((strategy / 3).toInt() != 1, (strategy / 3).toInt() != 0))
    }


    fun simMatch(): Pair<Double, List<TeamStats>> {
        var score = teams.map { team -> team.autoPoints.sample() + team.climbPoints.sample() }.sum()
        val stats = teams.map { TeamStats(0, 0) }

        val teamLocalTimes: MutableList<Double> = teams.map { Math.random() }.toMutableList()

        val panelPoints = 2
        val cargoPoints = 3

        val l = mutableListOf(Pair(12, 12), Pair(4, 4), Pair(4, 4))

        var curTeam: Team2019
        while (true in teamLocalTimes.map { it < 135 }) {
            val tIndex = teamLocalTimes.indexOf(teamLocalTimes.min())
            curTeam = teams[tIndex]

            val availableLevels = (0..2).map { Pair(l[it].first > 0, l[it].second > 0 && (l[it].first < l[it].second)) }
            val choice = chooseStrat(
                    curTeam.tele.first[0].average, curTeam.tele.first[1].average, curTeam.tele.first[2].average,
                    curTeam.tele.second[0].average, curTeam.tele.second[1].average, curTeam.tele.second[2].average,
                    availableLevels[0], availableLevels[1], availableLevels[2]
            )
            when {
                choice.second.first -> {
                    teamLocalTimes[tIndex] += curTeam.tele.first[choice.first].sample()
                    if (teamLocalTimes[tIndex] <= 135) {
                        score += panelPoints
                        l[choice.first] = Pair(l[choice.first].first - 1, l[choice.first].second)
                        stats[tIndex].hatch++
                    }
                }
                choice.second.second -> {
                    teamLocalTimes[tIndex] += curTeam.tele.second[choice.first].sample()
                    if (teamLocalTimes[tIndex] <= 135) {
                        score += cargoPoints
                        l[choice.first] = Pair(l[choice.first].first, l[choice.first].second - 1)
                        stats[tIndex].cargo++
                    }
                }
            }
        }

        println(score.toString() + " " + stats.toString())
        return Pair(score, stats)
    }
}

fun main() {
    val t865 = Team2019(
            865,
            Discrete(mapOf()),
            Gaussian(16.0, 4.0),
            Gaussian(16.0, 5.0),
            Gaussian(16.0, 5.0),
            Gaussian(13.0, 4.0),
            Gaussian(14.0, 3.0),
            Gaussian(14.0, 3.0),
            Discrete(mapOf())
    )
    val t1114 = Team2019(
            1114,
            Discrete(mapOf()),
            Gaussian(12.0, 3.0),
            Gaussian(12.0, 3.0),
            Gaussian(12.0, 4.0),
            Gaussian(13.0, 2.0),
            Gaussian(13.0, 2.0),
            Gaussian(13.0, 2.0),
            Discrete(mapOf())
    )
    val t4039 = Team2019(
            4039,
            Discrete(mapOf()),
            Gaussian(12.0, 3.0),
            Gaussian(136.0, 0.0),
            Gaussian(136.0, 0.0),
            Gaussian(12.0, 5.0),
            Gaussian(136.0, 0.0),
            Gaussian(136.0, 0.0),
            Discrete(mapOf())
    )

    val r = Alliance2019(listOf(t4039,t865))

    var s: MutableList<Double> = mutableListOf()
    val n = 100
    val stats: MutableList<List<Int>> = mutableListOf()
    for (i in 1..n) {
        val (m, stat) = r.simMatch()
        //println(m)
        s.add(m)
        stats.add(stat.map{listOf(it.hatch,it.cargo)}.flatten())
    }
    println(s.sum() / n)

    println(stats.forEach { match ->
        match.forEach { stat ->
            print(stat)
            print("\t")
        }
        println()
    })
}