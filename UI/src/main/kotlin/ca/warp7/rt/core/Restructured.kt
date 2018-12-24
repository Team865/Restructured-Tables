package ca.warp7.rt.core

import ca.warp7.rt.core.env.ensureWindowsOS
import ca.warp7.rt.core.feature.showStage
import javafx.application.Application
import javafx.stage.Stage

class Restructured : Application() {
    override fun start(stage: Stage) {
        ensureWindowsOS()
        showStage("/ca/warp7/rt/core/app/App.fxml", "Restructured Tables")
    }
}
