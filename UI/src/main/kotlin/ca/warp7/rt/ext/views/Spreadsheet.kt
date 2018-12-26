package ca.warp7.rt.ext.views

import ca.warp7.rt.core.feature.FeatureIcon
import javafx.scene.control.ContextMenu
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.*
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridChange
import org.controlsfx.control.spreadsheet.SpreadsheetView
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.util.*

class Spreadsheet(grid: Grid) : SpreadsheetView(grid) {

    private lateinit var dataFormat: DataFormat

    override fun getSpreadsheetViewContextMenu(): ContextMenu {
        val contextMenu = ContextMenu()

        contextMenu.items.addAll(
                Menu("Copy", FeatureIcon("fas-copy:16:1e2e4a")).apply {
                    items.addAll(
                            MenuItem("Selected cells").apply {
                                accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN)
                            },
                            MenuItem("Selected cells with headers"),
                            MenuItem("Selected rows"),
                            MenuItem("Selected rows with headers"),
                            MenuItem("Selected columns"),
                            MenuItem("Selected columns with headers"),
                            MenuItem("Entire table"),
                            MenuItem("Entire table with headers")
                    )
                },

                SeparatorMenuItem(),

                MenuItem("Zoom In", FeatureIcon("fas-search-plus:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.PLUS, KeyCombination.SHORTCUT_DOWN)
                },
                MenuItem("Zoom Out", FeatureIcon("fas-search-plus:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.MINUS, KeyCombination.SHORTCUT_DOWN)
                },
                MenuItem("Reset Zoom").apply {
                    accelerator = KeyCodeCombination(KeyCode.DIGIT0, KeyCombination.SHORTCUT_DOWN)
                },

                SeparatorMenuItem()
        )

        return contextMenu
    }

    override fun copyClipboard() {
        dataFormat = DataFormat.lookupMimeType("SpreadsheetView") ?: DataFormat("SpreadsheetView")
        val list = ArrayList<GridChange>()
        val allRows = mutableSetOf<Int>()
        val allCols = mutableSetOf<Int>()
        for (p in selectionModel.selectedCells) {
            val modelRow = getModelRow(p.row)
            val modelCol = getModelColumn(p.column)
            allRows.add(modelRow)
            allCols.add(modelCol)
            val cell = grid.rows[modelRow][modelCol]
            for (row in 0 until getRowSpan(cell, p.row)) {
                for (col in 0 until getColumnSpan(cell)) {
                    try {
                        ObjectOutputStream(ByteArrayOutputStream()).writeObject(cell.item)
                        list.add(GridChange(p.row + row, p.column + col,
                                null, if (cell.item == null) null else cell.item))
                    } catch (exception: IOException) {
                        list.add(GridChange(p.row + row, p.column + col,
                                null, if (cell.item == null) null else cell.item.toString()))
                    }

                }
            }
        }
        val builder = StringBuilder()
        val minRow = allRows.min()
        val maxRow = allRows.max()
        val minCol = allCols.min()
        val maxCol = allCols.max()
        if (minRow != null && maxRow != null && minCol != null && maxCol != null) {
            for (i in minRow..maxRow) {
                for (j in minCol..(maxCol - 1)) {
                    builder.append(grid.rows[i][j].item)
                    builder.append("\t")
                }
                builder.append(grid.rows[i][maxCol].item)
                builder.append("\n")
            }
        }
        val content = ClipboardContent()
        content[dataFormat] = list
        content.putString(builder.toString())
        Clipboard.getSystemClipboard().setContent(content)
    }
}