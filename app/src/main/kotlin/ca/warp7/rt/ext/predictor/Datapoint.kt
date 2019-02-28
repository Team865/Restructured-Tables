package ca.warp7.rt.ext.predictor

import java.util.*

abstract class Datapoint {
    abstract fun sample(): Double
    abstract val average: Double
}

class Discrete(var p: Map<Double, Double>) : Datapoint() {
    override fun sample(): Double {
        val r = Random().nextDouble()
        var s = 0.0
        this.p.forEach {
            s += it.key
            if (s > r) {
                return it.value
            }
        }
        return 0.0
    }


    override val average: Double
        get() = p.maxBy { it.value }!!.key
}

class Gaussian(var mu: Double,
               var sigma: Double) : Datapoint() {

    override fun sample(): Double {
        val x = Random().nextGaussian() * sigma + mu

        return when (x < 0) {
            true -> sample()
            false -> x
        }
    }

    override val average: Double
        get() = mu
}

fun emptyDiscrete() = Discrete(mapOf())
fun emptyCycle() = Gaussian(136.0, 0.0)