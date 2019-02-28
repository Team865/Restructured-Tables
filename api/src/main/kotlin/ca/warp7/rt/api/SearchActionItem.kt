package ca.warp7.rt.api

import javafx.scene.Parent

data class SearchActionItem(val name: String, val provider: () -> Parent?)