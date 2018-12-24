package ca.warp7.rt.core

import ca.warp7.rt.core.app.AppElement
import ca.warp7.rt.core.env.UserConfig
import ca.warp7.rt.core.env.UserEnv
import ca.warp7.rt.core.env.ensureWindowsOS
import ca.warp7.rt.core.feature.showStage
import javafx.application.Application
import javafx.stage.Stage

class Restructured : Application() {
    override fun start(stage: Stage) {
        ensureWindowsOS()
        if (UserConfig.appUserName !in UserEnv) AppElement.getUserExplicitName()
        if (UserConfig.appUserDevice !in UserEnv) AppElement.getUserExplicitDevice()
        showStage("/ca/warp7/rt/core/app/App.fxml", "Restructured Tables")
    }
}
