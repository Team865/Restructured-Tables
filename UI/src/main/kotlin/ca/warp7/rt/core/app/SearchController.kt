package ca.warp7.rt.core.app

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.VBox


class SearchController {
    lateinit var searchField: TextField
    lateinit var scrollPane: ScrollPane
    lateinit var vBox: VBox
    lateinit var searchResults: VBox

    fun initialize() {
        vBox.onScroll = EventHandler<ScrollEvent> { event ->
            val deltaY = event.deltaY * 6
            val width = scrollPane.content.boundsInLocal.width
            scrollPane.vvalue += -deltaY / width
        }
        val children = searchResults.children.toList()
        searchField.textProperty().addListener { _, _, newValue ->
            if (newValue.isNotEmpty()) {
                searchResults.children.clear()
            } else {
                searchResults.children.addAll(children)
            }
        }
    }

    fun focus() {
        Platform.runLater {
            searchField.requestFocus()
            searchField.positionCaret(searchField.text.length)
        }
    }
}
