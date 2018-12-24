package ca.warp7.rt.core.app

import ca.warp7.rt.core.env.EnvUser
import ca.warp7.rt.core.env.EnvUtils
import ca.warp7.rt.core.feature.FeatureIcon
import ca.warp7.rt.core.feature.FeatureItemTab
import ca.warp7.rt.core.feature.FeatureLink
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.paint.Color

internal object AppElement {

    @JvmStatic
    val teamColor: Color = Color.valueOf("1e2e4a")

    @JvmStatic
    fun tabUIFromAction(tab: AppTab): HBox {
        val action = tab.featureItemTab
        val outer = HBox()
        val inner = HBox()
        inner.prefWidth = 24.0
        inner.alignment = Pos.CENTER
        val icon = FeatureIcon(action.iconLiteral)
        icon.iconColor = teamColor
        inner.children.add(icon)
        tab.icon = icon
        outer.alignment = Pos.CENTER_LEFT
        outer.spacing = 10.0
        outer.children.add(inner)
        outer.children.add(Label(action.title))
        return outer
    }

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
        outer.children.add(Label(link.title))
        return outer
    }


    @JvmStatic
    fun getTitle(tab: FeatureItemTab): String {
        val title: String
        if (tab.tabGroup == FeatureItemTab.Group.SingleTab)
            title = String.format("%s", tab.title)
        else {
            val id = tab.featureId
            val capId = id.substring(0, 1).toUpperCase() + id.substring(1)
            title = String.format("%s - %s", tab.title, capId)
        }
        return title
    }

    @JvmStatic
    fun getUserExplicitName(): String {
        val current = EnvUser["app.userName", EnvUtils.user]
        val name = AppUtils.getString("Setup", "Enter name (First Last):",
                current, String::isNotEmpty) ?: current
        EnvUser["app.userName"] = name
        return name
    }

    @JvmStatic
    fun getUserExplicitDevice(): String {
        val current = EnvUser["app.deviceName", EnvUtils.computerName]
        val name = AppUtils.getString("Setup", "Enter device:",
                current, String::isNotEmpty) ?: current
        EnvUser["app.deviceName"] = name
        return name
    }

    @JvmStatic
    fun updateUserAndDevice(userName: Label, deviceName: Label) {
        userName.text = when {
            !EnvUser["app.userName"] -> getUserExplicitName()
            else -> EnvUser["app.userName", "Unknown User"]
        }
        deviceName.text = when {
            !EnvUser["app.deviceName"] -> getUserExplicitDevice()
            else -> EnvUser["app.deviceName", "Unknown Device"]
        }
        EnvUser.save()
    }
}
