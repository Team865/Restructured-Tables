package ca.warp7.rt.core.feature

import org.kordamp.ikonli.javafx.FontIcon

data class FeatureLink(val title: String, val iconCode: String, val iconSize: Int) {
    var icon = FontIcon()

    init {
        icon.iconLiteral = "$iconCode:$iconSize:black"
    }
}