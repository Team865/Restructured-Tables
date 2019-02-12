package ca.warp7.rt.ext.predictor

abstract class Team(open val teamNumber: Int)

class Team2018(override val teamNumber: Int,
               var autoLine: Discrete, var autoScale: Discrete, var autoSwitch: Discrete,
               var scale: Gaussian, var nearSwitch: Gaussian, var farSwitch: Gaussian, var exchange: Gaussian,
               var climbing: Discrete) : Team(teamNumber)

class Team2019(override val teamNumber: Int,
               var autoPoints: Discrete,
               var panelL1: Gaussian, var panelL2: Gaussian, var panelL3: Gaussian,
               var cargoL1: Gaussian, var cargoL2: Gaussian, var cargoL3: Gaussian,
               var climbPoints: Discrete) : Team(teamNumber) {
    var tele = Pair(
            listOf(panelL1, panelL2, panelL3),
            listOf(cargoL1, cargoL2, cargoL3)
    )
}




