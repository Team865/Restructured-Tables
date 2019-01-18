package ca.warp7.rt.core

import ca.warp7.rt.core.app.getUserExplicitName
import ca.warp7.rt.core.app.showStage
import ca.warp7.rt.core.env.UserConfig
import ca.warp7.rt.core.env.UserEnv
import javafx.application.Application
import javafx.stage.Stage

class Restructured : Application() {
    override fun start(stage: Stage) {
        if (UserConfig.appUserName !in UserEnv) getUserExplicitName()
        showStage("/ca/warp7/rt/core/app/App.fxml", "Restructured Tables")
    }
}
