package ca.warp7.rt.ext.predictor

abstract class Match(open val red: Alliance, open val blue: Alliance) {
    var redScore = 0.0
    var blueScore = 0.0

    abstract fun simMatch()
}

class Match2019(override val red: Alliance2019, override val blue: Alliance2019) : Match(red, blue) {
    override fun simMatch() {
        redScore = red.simMatch().first
        blueScore = blue.simMatch().first
    }
}