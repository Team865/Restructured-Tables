package ca.warp7.rt.core.app

import javafx.application.Platform
import javafx.scene.control.TextField


class SearchController {
    lateinit var searchField: TextField

    fun focus() {
        Platform.runLater {

            searchField.requestFocus()
            searchField.positionCaret(searchField.text.length)
        }
    }
}
