package ca.warp7.rt.ext.predictor

abstract class Team(open val teamNumber: Int)

class Team2018(override val teamNumber: Int,
               var autoLine: Discrete, var autoScale: Discrete, var autoSwitch: Discrete,
               var scale: Gaussian, var nearSwitch: Gaussian, var farSwitch: Gaussian, var exchange: Gaussian,
               var climbing: Discrete) : Team(teamNumber)

class Team2019(override val teamNumber: Int,
               var autoPanel: Discrete,
               var autoCargo: Discrete,
               panelL1: Gaussian, panelL2: Gaussian, panelL3: Gaussian,
               cargoL1: Gaussian, cargoL2: Gaussian, cargoL3: Gaussian,
               var climbPoints: Discrete) : Team(teamNumber) {
    var tele = Pair(
            listOf(panelL1, panelL2, panelL3),
            listOf(cargoL1, cargoL2, cargoL3)
    )
}




