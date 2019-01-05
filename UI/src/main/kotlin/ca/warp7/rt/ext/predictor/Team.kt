package ca.warp7.rt.ext.predictor

import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.round

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
                is GaussianAverage -> results[key] = value.sample()
                is GaussianCycle -> results[key] = value.sample()
                is Number -> results[key] = value.toDouble()
            }
        }
        return results
    }


}


class Team2018(override val teamNumber: Int, override var data: Map<String, Any>):Team(teamNumber,data){
    override val datapoints = listOf(
            "autoLine",
            "autoScale",
            "autoSwitch",
            "scale",
            "nearSwitch",
            "farSwitch",
            "exchange",
            "climbing"
    )
}


class Probability(var p: Double) {
    fun sample(): Double {
        return if (p < Random().nextDouble()) 1.0 else 0.0
    }
}

class GaussianAverage(var mu: Double,
                      var sigma: Double) {
    fun sample(): Double {
        val k = (Random().nextGaussian() * sigma + mu)

        return when(k<0){
            true -> sample()
            false -> k
        }
    }
}

class GaussianCycle(var mu: Double,
                    var sigma: Double,
                    var time:Double,
                    var focus:Double,
                    var chance:Double) {
    fun sample(): Double {
        val k = (Random().nextGaussian() * sigma + mu) //cycle speed this sample

        return when(k<0){
            true -> sample()
            false -> chance*focus*time/k
        }
    }
}