package ca.warp7.rt.core.app

import ca.warp7.rt.core.env.EnvUtils
import ca.warp7.rt.core.env.UserConfig
import ca.warp7.rt.core.env.UserEnv
import ca.warp7.rt.core.feature.FeatureLink
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

internal object AppElement {

    val teamColor: Color = Color.valueOf("1e2e4a")

    fun tabUIFromLink(link: FeatureLink): HBox {
        val outer = HBox()
        val inner = HBox()
        inner.prefWidth = 24.0
        inner.alignment = Pos.CENTER
        link.icon.iconColor = teamColor
        inner.children.add(link.icon)
        outer.alignment = Pos.CENTER_LEFT
        outer.spacing = 10.0
        outer.children.add(inner)
        val label = Label(link.title)
        label.style = "-fx-font-size:16"
        outer.children.add(label)
        return outer
    }

    fun getUserExplicitName(): String {
        val current = UserEnv["app.userName", EnvUtils.user]
        val name = userInputString("Setup", "Enter name (First Last):",
                current, String::isNotEmpty) ?: current
        UserEnv[UserConfig.appUserName] = name
        return name
    }

    fun getUserExplicitDevice(): String {
        val current = UserEnv["app.deviceName", EnvUtils.computerName]
        val name = userInputString("Setup", "Enter device:",
                current, String::isNotEmpty) ?: current
        UserEnv[UserConfig.appUserDevice] = name
        return name
    }
}
