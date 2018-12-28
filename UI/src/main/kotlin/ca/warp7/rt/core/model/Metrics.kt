@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ca.warp7.rt.core.model

import java.time.LocalDate

class Metrics<T>(val columnName: String, val validator: (T) -> Boolean = { true }) {
    operator fun invoke(of: T): Pair<Metrics<T>, T> = this to of
    operator fun contains(value: T): Boolean = validator.invoke(value)

    companion object {
        val teamNumber = Metrics<Int>("Team") { it in 1..9999 }
        val matchNumber = Metrics<Int>("Match") { it in 1..199 }
        val compLevel = Metrics<String>("Comp Level") { it in CompLevels.set }
        val year = Metrics<Int>("Year") { it in 1992..LocalDate.now().year }
        val driverStation = Metrics<Int>("Driver Station") { it in 1..3 }
        val alliance = Metrics<String>("alliance") { it == Alliance.Red || it == Alliance.Blue }
        val event = Metrics<String>("Event")
        val scout = Metrics<String>("Scout")
        val dataSource = Metrics<String>("Data Source")
        val user = Metrics<String>("User")
    }
}

object CompLevels {
    const val QualificationMatch = "qm"
    const val EliminationFinal = "ef"
    const val QuarterFinal = "qf"
    const val SemiFinal = "sf"
    const val Final = "f"
    val set = setOf(QualificationMatch, EliminationFinal, QuarterFinal, SemiFinal, Final)
}

object Alliance {
    const val Red = "red"
    const val Blue = "blue"
}