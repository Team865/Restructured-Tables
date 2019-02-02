package ca.warp7.rt.core

import ca.warp7.rt.core.app.showStage
import javafx.application.Application
import javafx.stage.Stage

class Restructured : Application() {
    override fun start(stage: Stage) = showStage("/ca/warp7/rt/core/app/App.fxml", "")
}
