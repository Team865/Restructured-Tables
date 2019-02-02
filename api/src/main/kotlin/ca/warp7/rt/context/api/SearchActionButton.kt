package ca.warp7.rt.context.api

import javafx.scene.Parent

data class SearchActionButton(val name: String,
                              val provider: () -> Parent?,
                              val flavour: SearchFlavour,
                              val iconCode: String,
                              val iconSize: Int)