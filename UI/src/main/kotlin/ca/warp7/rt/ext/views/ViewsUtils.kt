package ca.warp7.rt.ext.views

import javafx.event.ActionEvent
import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import org.kordamp.ikonli.javafx.FontIcon

typealias Combo = KeyCodeCombination

fun menuItem(t: String,
             icon: String?,
             combo: KeyCombination,
             onAction: (event: ActionEvent) -> Unit): MenuItem {

    val item = MenuItem()
    item.text = t
    if (icon != null) {
        val fontIcon = FontIcon()
        fontIcon.iconLiteral = icon
        item.graphic = fontIcon
    }
    item.isMnemonicParsing = true
    item.accelerator = combo
    item.setOnAction { onAction.invoke(it) }
    return item
}

