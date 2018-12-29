package ca.warp7.rt.ext.views

import javafx.scene.control.SelectionMode
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.*
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import krangl.DataFrame

class DataFrameView(frame: DataFrame) : CopyableSpreadsheet(toGrid(frame)) {

    @Suppress("unused")
    private var df: DataFrame = frame
        set(value) {
            field = value
            grid = toGrid(field)
            updateNewData()
        }

    init {
        updateNewData()
        selectionModel.selectionMode = SelectionMode.MULTIPLE
        isShowColumnHeader = true
        isShowRowHeader = true
        isEditable = false
        contextMenu.items.addAll(
                SeparatorMenuItem(),
                menuItem("Sort Ascending", "fas-sort-amount-up:16:1e2e4a", Combo(KeyCode.EQUALS, ALT_DOWN)){
                    println(getSelectedColumns().joinToString())
                },
                menuItem("Sort Descending", "fas-sort-amount-down:16:1e2e4a", Combo(KeyCode.MINUS, ALT_DOWN)){},
                menuItem("Clear sort", null, Combo(KeyCode.DIGIT0, ALT_DOWN)) {
                },
                SeparatorMenuItem(),
                menuItem("Filter By", "fas-filter:16:1e2e4a", Combo(KeyCode.I, SHORTCUT_DOWN)) {
                },
                menuItem("Filter Out", null, Combo(KeyCode.I, SHORTCUT_DOWN, SHIFT_DOWN)) {
                },
                menuItem("Clear Filters", null, Combo(KeyCode.U, SHORTCUT_DOWN)) {
                },
                SeparatorMenuItem(),
                menuItem("Toggle Formatting", "fas-toggle-on:16:1e2e4a", Combo(KeyCode.SLASH, SHORTCUT_DOWN)) {
                },
                menuItem("Colour Scale Up", "fas-caret-up:16:1e2e4a",
                        Combo(KeyCode.EQUALS, SHORTCUT_DOWN, ALT_DOWN)) {
                },
                menuItem("Colour Scale Down", "fas-caret-down:16:1e2e4a",
                        Combo(KeyCode.MINUS, SHORTCUT_DOWN, ALT_DOWN)) {
                },
                menuItem("Clear Colour Scales", null, Combo(KeyCode.DIGIT0, SHORTCUT_DOWN, ALT_DOWN)) {
                },
                menuItem("Highlight Cells", "fas-adjust:16:1e2e4a", Combo(KeyCode.H, ALT_DOWN)) {
                },
                menuItem("Clear Highlighting", null, Combo(KeyCode.SLASH, ALT_DOWN)) {
                },
                SeparatorMenuItem(),
                menuItem("Data Summary", "fas-calculator:16:1e2e4a", Combo(KeyCode.C, ALT_DOWN)) {
                },
                menuItem("Configure", "fas-code:16:1e2e4a", Combo(KeyCode.S, ALT_DOWN)) {
                },
                SeparatorMenuItem(),
                menuItem("Extract View", "fas-table:16:1e2e4a", Combo(KeyCode.V, SHORTCUT_DOWN, SHIFT_DOWN)) {
                },
                menuItem("Make Pivot Table", null, Combo(KeyCode.B, SHORTCUT_DOWN, SHIFT_DOWN)) {
                }
        )
    }

    private fun updateNewData() {
        val text = Text()
        text.font = Font.font("sans-serif", FontWeight.BOLD, 14.0)
        val fixedMetrics = listOf("Team", "Match", "Match Type", "Alliance", "Scout", "Event", "Year")
        columns.forEachIndexed { index, column ->
            val modelCol = getModelColumn(index)
            val name = grid.columnHeaders[modelCol].replace("[^A-Za-z0-9 ]".toRegex(), "")
            if (name in fixedMetrics) column.isFixed = true
            text.text = name
            val width = text.layoutBounds.width + 20
            column.minWidth = width
        }
    }

    private fun getSelectedColumns(): List<Int> {
        var columns= mutableListOf<Int>()
        for (cell in selectionModel.selectedCells) {
            if (cell.column !in columns) {
                columns.add(cell.column)
            }
        }
        return columns
    }
}