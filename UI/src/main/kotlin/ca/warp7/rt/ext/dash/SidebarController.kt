package ca.warp7.rt.ext.dash

import ca.warp7.rt.context.model.*
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class SidebarController {
    lateinit var vBox: VBox

    fun initialize() {
        listOf(
                year_(2019),
                district_("ONT"),
                event_("onto3"),
                dataSource_("frc865"),
                user_("Yu Liu")
        ).forEach {
            vBox.children.add(
                    HBox().apply {
                        children.add(Label(it.name).apply {
                            style = "-fx-font-style: italic"
                            alignment = Pos.CENTER_RIGHT
                            minWidth = 100.0
                        })
                        children.add(Label(it.value.toString()).apply {
                            style = "-fx-font-weight: bold; -fx-text-fill: #1e2e4a"
                            minWidth = 100.0
                            alignment = Pos.CENTER_LEFT
                        })
                        //style = "-fx-padding: 5; -fx-background-radius: 5; -fx-background-color: #eee"
                        spacing = 10.0
                        alignment = Pos.CENTER
                    }
            )
        }
    }
}
