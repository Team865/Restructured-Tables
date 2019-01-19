package ca.warp7.rt.core.app

import ca.warp7.rt.context.api.SearchActionButton
import ca.warp7.rt.context.api.SearchActionItem
import ca.warp7.rt.context.api.SearchFlavour
import ca.warp7.rt.context.api.SearchResult
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.control.ScrollPane
import javafx.scene.control.TextField
import javafx.scene.input.ScrollEvent
import javafx.scene.layout.HBox
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
                searchResults.children.add(createResultUI(SearchResult(title = "Team 865",
                        header = "WARP7", summary = mapOf("Location" to "Toronto", "Event Rank" to "5"),
                        actionItems = listOf(SearchActionItem("Open in The Blue Alliance") { HBox() }),
                        actionButtons = listOf(SearchActionButton("data", { null }, SearchFlavour.Green, "fas-table", 18)))))

            } else {
                searchResults.children.clear()
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
