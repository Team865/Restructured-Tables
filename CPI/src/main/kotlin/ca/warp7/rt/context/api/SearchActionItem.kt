package ca.warp7.rt.context.api

import javafx.scene.Parent

data class SearchActionItem(val name: String, val provider: () -> Parent)