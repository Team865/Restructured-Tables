package ca.warp7.rt.core.feature

import javafx.scene.Node

interface Feature1 {
    fun getLink(): FeatureLink
    fun onOpen(): Pair<Node?, Node?> = null to null
    fun onClose(): Boolean = true
    fun setFocused(focused: Boolean) = Unit
}