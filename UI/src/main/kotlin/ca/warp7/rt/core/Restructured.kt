package ca.warp7.rt.core

import ca.warp7.rt.core.env.EnvUtils
import ca.warp7.rt.core.feature.FeatureUtils
import javafx.application.Application
import javafx.stage.Stage

class Restructured : Application() {
    override fun start(stage: Stage) {
        EnvUtils.ensureWindowsOS()
        FeatureUtils.showStage("/ca/warp7/rt/core/app/App.fxml", "Restructured Tables")
    }
}
