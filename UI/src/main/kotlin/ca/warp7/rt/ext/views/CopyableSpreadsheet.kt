package ca.warp7.rt.ext.views

import javafx.scene.control.ContextMenu
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DataFormat
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.SHIFT_DOWN
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import org.controlsfx.control.spreadsheet.Grid
import org.controlsfx.control.spreadsheet.GridChange
import org.controlsfx.control.spreadsheet.SpreadsheetView
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectOutputStream
import java.util.*

open class CopyableSpreadsheet(grid: Grid?) : SpreadsheetView(grid) {

    private lateinit var dataFormat: DataFormat

    override fun getSpreadsheetViewContextMenu(): ContextMenu {
        val contextMenu = ContextMenu()

        contextMenu.items.addAll(
                menuItem("_Copy", "far-copy:16:1e2e4a", Combo(KeyCode.C, SHORTCUT_DOWN)) {
                    copyClipboard()
                },
                menuItem("Copy with _headers", null, Combo(KeyCode.C, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    copyClipboardWithHeaders()
                },
                SeparatorMenuItem(),
                menuItem("Zoom _In", "fas-search-plus:16:1e2e4a", Combo(KeyCode.EQUALS, SHORTCUT_DOWN)) {
                    incrementZoom()
                },
                menuItem("Zoom _Out", "fas-search-minus:16:1e2e4a", Combo(KeyCode.MINUS, SHORTCUT_DOWN)) {
                    decrementZoom()
                },
                menuItem("_Reset Zoom", null, Combo(KeyCode.DIGIT0, SHORTCUT_DOWN)) {
                    zoomFactor = 1.0
                }
        )

        return contextMenu
    }

    private fun copyClipboardWithHeaders() {
        val allRows = mutableSetOf<Int>()
        val allCols = mutableSetOf<Int>()
        for (p in selectionModel.selectedCells) {
            val modelRow = getModelRow(p.row)
            val modelCol = getModelColumn(p.column)
            allRows.add(modelRow)
            allCols.add(modelCol)
        }
        val builder = StringBuilder()
        val minRow = allRows.min()
        val maxRow = allRows.max()
        val minCol = allCols.min()
        val maxCol = allCols.max()
        if (minRow != null && maxRow != null && minCol != null && maxCol != null) {
            for (col in minCol..(maxCol - 1)) {
                builder.append(grid.columnHeaders[col])
                builder.append("\t")
            }
            builder.append(grid.columnHeaders[maxCol])
            builder.append("\n")
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
        content.putString(builder.toString())
        Clipboard.getSystemClipboard().setContent(content)
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