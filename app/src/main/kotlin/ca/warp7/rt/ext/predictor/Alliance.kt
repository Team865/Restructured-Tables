package ca.warp7.rt.ext.predictor

abstract class Alliance(open val teams: List<Team>)

data class TeamStats(var hatch: Int, var cargo: Int)

class Alliance2019(override val teams: List<Team2019>) : Alliance(teams) {
    private fun chooseStrat(panelL1: Double, panelL2: Double, panelL3: Double,
                            cargoL1: Double, cargoL2: Double, cargoL3: Double,
                            l1State: Pair<Boolean, Boolean>, l2State: Pair<Boolean, Boolean>, l3State: Pair<Boolean, Boolean>)
            : Pair<Int, Pair<Boolean, Boolean>> {

        val efficiencies: MutableList<MutableList<Double>> = mutableListOf(
                mutableListOf(2 / panelL1, 2 / panelL2, 2 / panelL3),
                mutableListOf(3 / cargoL1, 3 / cargoL2, 3 / cargoL3),
                mutableListOf(5 / (panelL1 + cargoL1), 5 / (panelL2 + cargoL2), 5 / (panelL3 + cargoL3))
        )

        listOf(l1State, l2State, l3State).forEachIndexed { i, it ->
            when (it) {
                Pair(false, false) -> {
                    efficiencies[0][i] = 0.0
                    efficiencies[1][i] = 0.0
                    efficiencies[2][i] = 0.0
                }
                Pair(false, true) -> {
                    efficiencies[0][i] = 0.0
                    efficiencies[2][i] = 0.0
                }
                Pair(true, false) -> {
                    efficiencies[1][i] = 0.0
                }
            }
        }
        val m = efficiencies.flatten().indexOf(efficiencies.flatten().max())
        val strategy = Pair(m%3,(m-m%3)/3)
        return Pair(strategy.first, Pair(strategy.second in listOf(0,2), strategy.second in listOf(1,2)))
   }


    fun simMatch(): Pair<Double, List<TeamStats>> {
        var score = teams.map { team -> team.climbPoints.sample() }.sum()
        val stats = teams.map { TeamStats(0, 0) }

        val teamLocalTimes: MutableList<Double> = teams.map { Math.random() }.toMutableList()

        val panelPoints = 2
        val cargoPoints = 3

        val l = mutableListOf(Pair(12, 12), Pair(4, 4), Pair(4, 4))

        teams.forEach { team ->
            val panel = team.autoPanel.sample().toInt()
            val cargo = team.autoCargo.sample().toInt()

            l[0] = Pair(l[0].first - panel, l[0].second)
            l[0] = Pair(l[0].first - cargo, l[0].second - cargo)

            score += panel * (panelPoints + cargoPoints)
            score += cargo * (cargoPoints)
        }


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

        println("$score $stats")
        return Pair(score, stats)
    }
}

fun main() {
    val t865 = Team2019(
            865,
            Discrete(mapOf(Pair(0.6, 2.0))),
            emptyDiscrete(),
            Gaussian(16.0, 4.0),
            Gaussian(16.0, 5.0),
            Gaussian(16.0, 5.0),
            Gaussian(13.0, 4.0),
            Gaussian(14.0, 3.0),
            Gaussian(14.0, 3.0),
            emptyDiscrete()
    )
    val t1114 = Team2019(
            1114,
            Discrete(mapOf(Pair(0.8, 2.0))),
            emptyDiscrete(),
            Gaussian(12.0, 3.0),
            Gaussian(12.0, 3.0),
            Gaussian(12.0, 4.0),
            Gaussian(13.0, 2.0),
            Gaussian(13.0, 2.0),
            Gaussian(13.0, 2.0),
            emptyDiscrete()
    )
    val t4039 = Team2019(
            4039,
            emptyDiscrete(),
            Discrete(mapOf(Pair(0.4, 1.0))),
            Gaussian(20.0, 3.0),
            emptyCycle(),
            emptyCycle(),
            Gaussian(20.0, 5.0),
            emptyCycle(),
            emptyCycle(),
            emptyDiscrete()
    )

    val r = Alliance2019(listOf(t1114, t865, t4039))

    val s: MutableList<Double> = mutableListOf()
    val n = 10
    val stats: MutableList<List<Any>> = mutableListOf()
    for (i in 1..n) {
        val (m, stat) = r.simMatch()
        //println(m)
        s.add(m)
        stats.add(listOf(stat))//stat.map{listOf(it.hatch,it.Cargo)}.flatten())
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