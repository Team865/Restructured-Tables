package ca.warp7.rt.ext.predictor

import java.util.*

open class Team(open val teamNumber: Int, open var data: Map<String, Any>) {
    open val datapoints: List<String> = listOf()

    init {
        data = data.filterKeys { it in datapoints }
    }

    fun sampleAll(): Map<String, Double> {
        var results: MutableMap<String, Double> = mutableMapOf()
        data.forEach { key, value ->
            when (value) {
                is Probability -> results[key] = value.sample()
                is Probabilities -> results[key] = value.sample()
                is GaussianAverage -> results[key] = value.sample()
                is GaussianCycle -> results[key] = value.sample()
                is Number -> results[key] = value.toDouble()
                else -> throw Throwable("Unknown type")
            }
        }

        return results
    }
}

class Team2018(override val teamNumber: Int, override var data: Map<String, Any>) : Team(teamNumber, data) {
    override val datapoints = listOf(
            "autoLine", //Probability
            "autoScale", //Probabilities
            "autoSwitch", //Probabilities

            "scale", //Gaussian
            "nearSwitch", //Gaussian
            "farSwitch", //Gaussian
            "exchange", //Gaussian

            "climbing" //Probability
    )
}

class Team2019(override val teamNumber: Int, override var data: Map<String, Any>) : Team(teamNumber, data) {
    override val datapoints = listOf(
            "Auto points", //Probabilities

            "Hatches L1", //Gaussian
            "Hatches L2", //Gaussian
            "Hatches L3", //Gaussian

            "Cargo L1", //Gaussian
            "Cargo L2", //Gaussian
            "Cargo L3", //Gaussian

            "Climb points" //Probabilities
    )
}

class Probability(var p: Double) {
    fun sample(): Double {
        return if (p < Random().nextDouble()) 1.0 else 0.0
    }
}

class Probabilities(var p: Map<Double, Double>) {
    fun sample(): Double {
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
}

class GaussianAverage(var mu: Double, var sigma: Double) {
    fun sample(): Double {
        val k = (Random().nextGaussian() * sigma + mu)

        return when (k < 0) {
            true -> sample()
            false -> k
        }
    }
}

class GaussianCycle(var mu: Double,
                    var sigma: Double,
                    var time: Double,
                    var focus: Double,
                    var chance: Double) {
    fun sample(): Double {
        val k = (Random().nextGaussian() * sigma + mu) //cycle speed this sample

        return when (k < 0) {
            true -> sample()
            false -> chance * focus * time / k
        }
    }
}
