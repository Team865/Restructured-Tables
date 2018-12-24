package ca.warp7.rt.core.feature

import javafx.scene.Parent

interface Feature {
    val link: FeatureLink
    val loadedTabs: List<FeatureItemTab>
    val featureId: String
    fun onOpenTab(tab: FeatureItemTab): Parent? = null
    fun onClose(): Boolean = true
    fun setFocused(focused: Boolean) = Unit
    fun onOpen(): Pair<Parent?, Parent?> = null to null
}
