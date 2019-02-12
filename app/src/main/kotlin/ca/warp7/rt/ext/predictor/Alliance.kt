package ca.warp7.rt.ext.predictor

abstract class Alliance(open val teams: List<Team>)

class Alliance2018(override val teams: List<Team2018>) : Alliance(teams)


class Alliance2019(override val teams: List<Team2019>) : Alliance(teams) {
    fun chooseStrat(panelL1: Double, panelL2: Double, panelL3: Double,
                    cargoL1: Double, cargoL2: Double, cargoL3: Double,
                    l1State: Pair<Boolean, Boolean>, l2State: Pair<Boolean, Boolean>, l3State: Pair<Boolean, Boolean>)
            : Pair<Int, Pair<Boolean, Boolean>> {
        val points: MutableList<Int> = mutableListOf(
                2, 2, 2,
                3, 3, 3,
                5, 5, 5
        )

        val time: MutableList<Double> = mutableListOf(
                panelL1, panelL2, panelL3,
                cargoL1, cargoL2, cargoL3,
                panelL1 + cargoL1, panelL2 + cargoL2, panelL3 + cargoL3
        )

        val efficiencies: MutableList<Double> = mutableListOf(
                2 / panelL1,
                2 / panelL2,
                2 / panelL3,
                3 / cargoL1,
                3 / cargoL2,
                3 / cargoL3,
                5 / (panelL1 + cargoL1),
                5 / (panelL2 + cargoL2),
                5 / (panelL3 + cargoL3)
        )
        listOf(l1State, l2State, l3State).forEachIndexed { i, it ->
            when (it) {
                Pair(false, false) -> {
                    efficiencies[i + 0] = 0.0
                    efficiencies[i + 3] = 0.0
                    efficiencies[i + 6] = 0.0
                }
                Pair(true, false) -> {
                    efficiencies[i + 3] = 0.0
                    efficiencies[i + 6] = 0.0
                }
                Pair(false, true) -> {
                    efficiencies[i + 0] = 0.0
                    efficiencies[i + 6] = 0.0
                }
            }
        }

        val strategy = efficiencies.indexOf(efficiencies.max())

        return Pair(strategy % 3, Pair((strategy / 3).toInt() != 1, (strategy / 3).toInt() != 0))
    }


    fun simMatch(): Double {
        var score = 0.0

        val teamLocalTimes: MutableList<Double> = teams.map { 0.0 }.toMutableList()

        val panelPoints = 2
        val cargoPoints = 3

        var l = mutableListOf(Pair(12, 12), Pair(4, 4), Pair(4, 4))

        var curTeam: Team2019
        while (true in teamLocalTimes.map { it < 135 }) {
            var tIndex =  teamLocalTimes.indexOf(teamLocalTimes.min())
            curTeam = teams[tIndex]

            var availableLevels = (0..2).map { Pair(l[it].first > 0, l[it].second > 0 && (l[it].first > l[it].second)) }
            val choice = chooseStrat(
                    curTeam.tele.first[0].average,curTeam.tele.first[1].average, curTeam.tele.first[2].average,
                    curTeam.tele.second[0].average,curTeam.tele.second[1].average, curTeam.tele.second[2].average,
                    availableLevels[0], availableLevels[1], availableLevels[2]
            )

            score += (if (choice.second.first) panelPoints else 0)
            score += (if (choice.second.second) cargoPoints else 0)

            l[choice.first] = Pair(
                    l[choice.first].first - if (choice.second.first) 1 else 0,
                    l[choice.first].second - if (choice.second.second) 1 else 0
            )

            teamLocalTimes[tIndex] += if (choice.second.first) curTeam.tele.first[choice.first].sample() else 0.0
            teamLocalTimes[tIndex] += if (choice.second.first) curTeam.tele.first[choice.first].sample() else 0.0

        }

        return 2.0
    }
}
