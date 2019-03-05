package ca.warp7.rt.ext.predictor

import java.util.*

abstract class Team(open val teamNumber: Int, open var data: Map<String, Any>) {
    abstract val datapoints: List<String>

    init {
        data = data.filterKeys { it in datapoints }
    }

    fun sample(key: String): Double {
        val value = data[key]
        return when (value) {
            is Datapoint -> value.sample()
            is Number -> value.toDouble()
            else -> throw Throwable("Unknown or unsupported type")
        }
    }

    fun average(key: String): Double {
        val value = data[key]
        return when (value) {
            is Datapoint -> value.average()
            is Number -> value.toDouble()
            else -> throw Throwable("Unknown or unsupported type")
        }
    }

    fun sampleAll(): Map<String, Double> {
        val results: MutableMap<String, Double> = mutableMapOf()
        data.forEach { key, value ->
            results[key] = sample(key)
        }
        return results
    }
}

class Team2018(override val teamNumber: Int, override var data: Map<String, Any>) : Team(teamNumber, data) {
    override val datapoints = listOf(
            "autoLine", //Probability
            "autoScale", //Discrete
            "autoSwitch", //Discrete

            "scale", //Gaussian
            "nearSwitch", //Gaussian
            "farSwitch", //Gaussian
            "exchange", //Gaussian

            "climbing" //Probability
    )
}

class Team2019(override val teamNumber: Int, override var data: Map<String, Any>) : Team(teamNumber, data) {
    override val datapoints = listOf(
            "Auto points", //Discrete

            "Panel L1", //Gaussian
            "Panel L2", //Gaussian
            "Panel L3", //Gaussian

            "Cargo L1", //Gaussian
            "Cargo L2", //Gaussian
            "Cargo L3", //Gaussian

            "Climb points" //Discrete
    )
}

abstract class Datapoint() {
    abstract fun sample(): Double
    abstract fun average(): Double
}

class DiscreteProbabilities(var p: Map<Double, Double>) : Datapoint() {
    override fun sample(): Double {
        val r = Random().nextDouble()
        var s: Double = 0.0
        p.forEach {
            s += it.value
            if (s > r) {
                return it.key
            }
        }
        return 0.0
    }

    override fun average(): Double {
        return p.maxBy { it.value }!!.key
    }
}

class GaussianAverage(var mu: Double,
                      var sigma: Double) : Datapoint() {
    override fun sample(): Double {
        val k = (Random().nextGaussian() * sigma + mu)

        return when (k < 0) {
            true -> sample()
            false -> k
        }
    }

    override fun average(): Double {
        return mu
    }
}

class GaussianCycle(var mu: Double,
                    var sigma: Double) : Datapoint() {
    override fun sample(): Double {
        val k = (Random().nextGaussian() * sigma + mu) //cycle speed this sample

        return when (k < 0) {
            true -> sample()
            false -> 135 / k
        }
    }

    override fun average(): Double {
        return mu
    }
}
