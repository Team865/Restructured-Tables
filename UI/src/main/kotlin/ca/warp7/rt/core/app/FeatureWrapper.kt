package ca.warp7.rt.core.app

import ca.warp7.rt.context.api.Feature
import org.kordamp.ikonli.javafx.FontIcon

class FeatureWrapper(private val feature: Feature) : Feature {
    override val link = feature.link
    var icon = FontIcon().apply { iconLiteral = "${link.iconCode}:${link.iconSize}:black" }
    override fun onOpen() = feature.onOpen()
    override fun onClose() = feature.onClose()
    override fun setFocused(focused: Boolean) = feature.setFocused(focused)
}