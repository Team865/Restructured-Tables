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

class DataFrameView(frame: DataFrame) : CopyableSpreadsheet(toGrid(frame)) {

    var frame=frame
    var sorts: MutableList<Pair<String, Boolean>> = mutableListOf() // Column label, ascending
    var filters: MutableSet<Triple<String, Any, Boolean>> = mutableSetOf() // Column label, value, whitelist

    var dfSpreadsheet: DataFrame = frame
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
                    addSort(getSelectedColumns(), false)
                    dfSpreadsheet = applySort(sorts, dfSpreadsheet)
                },
                menuItem("Sort Descending", "fas-sort-amount-down:16:1e2e4a", Combo(KeyCode.MINUS, ALT_DOWN)) {
                    addSort(getSelectedColumns(), true)
                    dfSpreadsheet = applySort(sorts, dfSpreadsheet)
                },
                menuItem("Clear sort", null, Combo(KeyCode.DIGIT0, ALT_DOWN)) {
                    sorts=mutableListOf()
                    resetDisplay()
                },
                SeparatorMenuItem(),
                menuItem("Filter By", "fas-filter:16:1e2e4a", Combo(KeyCode.I, SHORTCUT_DOWN)) {
                    addFilter(getSelectedColumnsValues(), true)
                    dfSpreadsheet = applyFilter(filters, dfSpreadsheet)
                },
                menuItem("Filter Out", null, Combo(KeyCode.I, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    addFilter(getSelectedColumnsValues(), false)
                    dfSpreadsheet = applyFilter(filters, dfSpreadsheet)
                },
                menuItem("Clear Filters", null, Combo(KeyCode.U, SHORTCUT_DOWN)) {
                    filters= mutableSetOf()
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

    private fun addSort(columns: Set<String>, descending: Boolean) {
        columns.forEach {
            sorts.add(Pair(it, descending))
        }
    }

    private fun applySort(listOfSorts: List<Pair<String, Boolean>>, df: DataFrame): DataFrame {
        var df = df
        listOfSorts.forEach {
            when (it.second) {
                //true -> df = df.sortedByDescending(it.first)
                false -> df = df.sortedBy(it.first)
            }
        }
        return df
    }

    private fun addFilter(valuesByColumn: Set<Pair<String, Any>>, whitelist: Boolean) {
        valuesByColumn.forEach {
            filters.add(Triple(it.first, it.second, whitelist))
        }
    }

    private fun applyFilter(setOfFilters: Set<Triple<String, Any, Boolean>>, df: DataFrame): DataFrame {
        var df = df
        for (filter in setOfFilters) {
            when (filter.third) {
                true -> df = df.filter { it[filter.first] eq filter.second }
                //false -> df = df.filter { it[filter.first] ne filter.second }
            }
        }
        return df
    }

    private fun resetDisplay(){
        dfSpreadsheet=frame
        dfSpreadsheet=applySort(sorts,dfSpreadsheet)
        dfSpreadsheet=applyFilter(filters,dfSpreadsheet)
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

    private fun getSelectedColumns(): Set<String> {
        return selectionModel.selectedCells.map {
            grid.columnHeaders[it.column]
        }.toSet()
    }

    private fun getSelectedValues(): Set<Any> {
        return selectionModel.selectedCells.map {
            grid.rows[it.row][it.column].item
        }.toSet()
    }

    private fun getSelectedColumnsValues(): Set<Pair<String, Any>> {
        return selectionModel.selectedCells.map {
            Pair(grid.columnHeaders[it.column], grid.rows[it.row][it.column].item)
        }.toSet()
    }
}