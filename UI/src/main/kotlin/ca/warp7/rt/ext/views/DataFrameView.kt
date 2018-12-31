package ca.warp7.rt.ext.views

import javafx.scene.control.SelectionMode
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.*
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import krangl.DataFrame
import krangl.eq
import krangl.not

class DataFrameView(private var initialFrame: DataFrame) : CopyableSpreadsheet(toGrid(initialFrame)) {

    private val sortColumns: MutableList<SortColumn> = mutableListOf()
    private val filterRows: MutableList<FilterRow> = mutableListOf()

    private var spreadsheetFrame: DataFrame = initialFrame
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
                menuItem("Sort Ascending", "fas-sort-amount-up:16:1e2e4a", Combo(KeyCode.EQUALS, ALT_DOWN)) {
                    addSort(SortType.Ascending, getSelectedColumns())
                    applySort()
                },
                menuItem("Sort Descending", "fas-sort-amount-down:16:1e2e4a", Combo(KeyCode.MINUS, ALT_DOWN)) {
                    addSort(SortType.Descending, getSelectedColumns())
                    applySort()
                },
                menuItem("Clear sort", null, Combo(KeyCode.DIGIT0, ALT_DOWN)) {
                    sortColumns.clear()
                    resetDisplay()
                },
                SeparatorMenuItem(),
                menuItem("Filter By", "fas-filter:16:1e2e4a", Combo(KeyCode.I, SHORTCUT_DOWN)) {
                    addFilter(getSelectedColumnsValues(), true)
                    applyFilter()
                },
                menuItem("Filter Out", null, Combo(KeyCode.I, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    addFilter(getSelectedColumnsValues(), false)
                    applyFilter()
                },
                menuItem("Clear Filters", null, Combo(KeyCode.U, SHORTCUT_DOWN)) {
                    filterRows.clear()
                    resetDisplay()
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

    private fun addSort(sortType: SortType, columns: Set<String>) {
        sortColumns.addAll(columns.map { SortColumn(sortType, it) })
        columns.forEach {
            var foundExisting = false
            sortColumns.withIndex().forEach { (i, sortColumn) ->
                if (sortColumn.columnName == it) {
                    sortColumns[i] = SortColumn(sortType, sortColumn.columnName)
                    foundExisting = true
                }
            }
            if (!foundExisting) sortColumns.add(SortColumn(sortType, it))
        }
    }

    private fun applySort() {
        spreadsheetFrame = initialFrame.comboSort(sortColumns)
        for (sortColumn in sortColumns) {
            for ((i, columnHeader) in grid.columnHeaders.withIndex()) {
                if (columnHeader.startsWith(sortColumn.columnName)) {
                    grid.columnHeaders[i] = sortColumn.columnName + when (sortColumn.sortType) {
                        SortType.Ascending -> " \u25B4"
                        SortType.Descending -> " \u25be"
                    }
                }
            }
        }
    }

    private fun addFilter(valuesByColumn: Set<Pair<String, Any>>, whitelist: Boolean) {
        filterRows.addAll(valuesByColumn.map { FilterRow(it.first, it.second, whitelist) })
    }

    private fun applyFilter() {
        var df = spreadsheetFrame
        for (filter in filterRows) {
            df = when (filter.whitelist) {
                true -> df.filter { it[filter.label] eq filter.value }
                false -> df.filter { (it[filter.label] eq filter.value).not() }
            }
        }
        spreadsheetFrame = df
    }

    private fun resetDisplay() {
        spreadsheetFrame = initialFrame
        applySort()
        applyFilter()
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
            val width = text.layoutBounds.width + 30
            column.minWidth = width
        }
    }

    private fun getSelectedColumns() = selectionModel.selectedCells
            .map { spreadsheetFrame.cols[it.column].name }.toSet()

    private fun getSelectedColumnsValues() = selectionModel.selectedCells
            .map { grid.columnHeaders[it.column] to grid.rows[it.row][it.column].item }.toSet()
}