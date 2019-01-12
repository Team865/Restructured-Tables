package ca.warp7.rt.context.api

import javafx.scene.Parent

interface Feature {
    val link: FeatureLink
    fun onOpen(): Pair<Parent?, Parent?> = null to null
    fun onClose(): Boolean = true
    fun setFocused(focused: Boolean) = Unit
}
