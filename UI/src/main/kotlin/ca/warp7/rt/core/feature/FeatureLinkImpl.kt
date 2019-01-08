package ca.warp7.rt.core.feature

import org.kordamp.ikonli.javafx.FontIcon

class FeatureLinkImpl(link: FeatureLink) {
    val title = link.title
    val iconCode = link.iconCode
    val iconSize = link.iconSize
    val icon = FontIcon()

    init {
        icon.iconLiteral = "$iconCode:$iconSize:black"
    }
}