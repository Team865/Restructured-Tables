@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package ca.warp7.rt.core.model

import ca.warp7.rt.context.api.IntMetric
import ca.warp7.rt.context.api.PipelineStreamVector
import ca.warp7.rt.context.api.StringMetric
import java.time.LocalDate

private typealias V = PipelineStreamVector

object CompLevels {
    const val QualificationMatch = "qm"
    const val Unknown = "ef"
    const val QuarterFinal = "qf"
    const val SemiFinal = "sf"
    const val Final = "f"
    val set = setOf(QualificationMatch, Unknown, QuarterFinal, SemiFinal, Final)
}

object Alliance {
    const val Red = "red"
    const val Blue = "blue"
}

private val currentYear = LocalDate.now().year
val teamNumber_ = IntMetric("Team") { it in 1..9999 }
val matchNumber_ = IntMetric("Match") { it in 1..199 }
val compLevel_ = StringMetric("Comp Level") { it in CompLevels.set }
val year_ = IntMetric("Year") { it in 1992..currentYear }
val driverStation_ = IntMetric("Driver Station") { it in 1..3 }
val alliance_ = StringMetric("Alliance") { it == Alliance.Red || it == Alliance.Blue }
val district_ = StringMetric("District")
val event_ = StringMetric("Event")
val scout_ = StringMetric("Scout")
val dataSource_ = StringMetric("Data Source")
val user_ = StringMetric("User")

val match_ = matchNumber_ / compLevel_
val board_ = driverStation_ / alliance_

val V.teamNumber get() = this.getMetric(teamNumber_)
val V.matchNumber get() = this.getMetric(matchNumber_)
val V.compLevel get() = this.getMetric(compLevel_)
val V.year get() = this.getMetric(year_)
val V.driverStation get() = this.getMetric(driverStation_)
val V.alliance get() = this.getMetric(alliance_)
val V.district get() = this.getMetric(district_)
val V.event get() = this.getMetric(event_)
val V.scout get() = this.getMetric(scout_)
val V.dataSource get() = this.getMetric(dataSource_)
val V.user get() = this.getMetric(user_)