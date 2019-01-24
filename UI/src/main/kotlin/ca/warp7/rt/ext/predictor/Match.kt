package ca.warp7.rt.ext.predictor

abstract class Match(open val red: Alliance, open val blue: Alliance) {
    val datapoints = red.datapoints

    open fun simMatch(): Boolean {
        return true
    }
}

class Match2019(override val red: Alliance2019, override val blue: Alliance2019) : Match(red, blue) {
    override fun simMatch(): Boolean {


        val teleMatchTime = 135



        return true
    }
}