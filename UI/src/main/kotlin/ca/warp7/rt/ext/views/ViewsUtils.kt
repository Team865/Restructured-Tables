package ca.warp7.rt.ext.views

import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.scene.control.MenuItem
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import krangl.DataFrame
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCell
import org.controlsfx.control.spreadsheet.SpreadsheetCellType
import org.kordamp.ikonli.javafx.FontIcon
import java.time.LocalDate

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


fun toGrid(df: DataFrame): Grid {
    val grid = GridBase(df.nrow, df.ncol)
    df.rows.forEachIndexed { i, row ->
        val rowList = FXCollections.observableArrayList<SpreadsheetCell>()
        row.values.forEachIndexed { j, value ->
            rowList.add(when (value) {
                null -> null
                is Double -> SpreadsheetCellType.DOUBLE.createCell(i, j, 1, 1, value)
                is Int -> SpreadsheetCellType.INTEGER.createCell(i, j, 1, 1, value)
                is LocalDate -> SpreadsheetCellType.DATE.createCell(i, j, 1, 1, value)
                is String -> SpreadsheetCellType.STRING.createCell(i, j, 1, 1, value)
                else -> SpreadsheetCellType.STRING.createCell(i, j, 1, 1, value.toString())
            })
        }
        grid.rows.add(rowList)
    }
    grid.columnHeaders.addAll(df.cols.map { it.name })
    return grid
}