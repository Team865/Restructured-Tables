package ca.warp7.rt.ext.views

import ca.warp7.rt.core.app.utilsController
import javafx.scene.Scene
import javafx.scene.control.SelectionMode
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.*
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import javafx.stage.Stage
import javafx.stage.StageStyle
import krangl.DataFrame
import krangl.max
import krangl.min
import java.util.Collections.emptyList

class DataFrameView(initialFrame: DataFrame, viewColumns: List<String> = emptyList()) : CopyableSpreadsheet(toGrid(initialFrame)) {

    private val model = ViewsModel(initialFrame, viewColumns)

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
                    model.addSort(getSelectedColumns(), SortType.Ascending)
                    resetDisplay()
                },
                menuItem("Sort Descending", "fas-sort-amount-down:16:1e2e4a", Combo(KeyCode.MINUS, ALT_DOWN)) {
                    model.addSort(getSelectedColumns(), SortType.Descending)
                    resetDisplay()
                },
                menuItem("Clear sort", null, Combo(KeyCode.DIGIT0, ALT_DOWN)) {
                    model.sortColumns.clear()
                    resetDisplay()
                },
                SeparatorMenuItem(),
                menuItem("Filter By", "fas-filter:16:1e2e4a", Combo(KeyCode.I, SHORTCUT_DOWN)) {
                    model.addFilter(getSelectedColumnsValues(), true)
                    resetDisplay()
                },
                menuItem("Filter Out", null, Combo(KeyCode.I, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    model.addFilter(getSelectedColumnsValues(), false)
                    resetDisplay()
                },
                menuItem("Clear Filters", null, Combo(KeyCode.U, SHORTCUT_DOWN)) {
                    model.filterRows.clear()
                    resetDisplay()
                },
                SeparatorMenuItem(),
                menuItem("Colour Scale Up", "fas-caret-up:16:1e2e4a",
                        Combo(KeyCode.EQUALS, SHORTCUT_DOWN, ALT_DOWN)) {
                    model.addColorScale(getSelectedColumns(), true)
                    resetDisplay()
                },
                menuItem("Colour Scale Down", "fas-caret-down:16:1e2e4a",
                        Combo(KeyCode.MINUS, SHORTCUT_DOWN, ALT_DOWN)) {
                    model.addColorScale(getSelectedColumns(), false)
                    resetDisplay()
                },
                menuItem("Clear Colour Scales", null, Combo(KeyCode.DIGIT0, SHORTCUT_DOWN, ALT_DOWN)) {
                    model.colorScaleColumns.clear()
                    resetDisplay()
                },
                menuItem("Highlight Cells", "fas-adjust:16:1e2e4a", Combo(KeyCode.H, ALT_DOWN)) {},
                menuItem("Clear Highlighting", null, Combo(KeyCode.SLASH, ALT_DOWN)) {},
                SeparatorMenuItem(),
                menuItem("Data Summary", "fas-calculator:16:1e2e4a", Combo(KeyCode.C, ALT_DOWN)) {
                    Stage().apply {
                        title = "Data Summary"
                        initStyle(StageStyle.UTILITY)
                        initOwner(utilsController?.appStage)

                        val values = selectionModel.selectedCells.map { grid.rows[it.row][it.column].item }

                        val wrapper = VBox(
                                Text("Count ${values.size}"),
                                Text("Sum ${
                                values.map {
                                    when (it) {
                                        is Number -> it.toDouble()
                                        else -> 0.0
                                    }
                                }.sum()}"),
                                Text("Mean ${
                                values.map {
                                    when (it) {
                                        is Number -> it.toDouble()
                                        else -> 0.0
                                    }
                                }.sum() / values.size}")
                        )
                        wrapper.minWidth = 150.0
                        wrapper.minHeight = 100.0
                        scene = Scene(wrapper)
                        opacity = 0.95
                        scene.fill = Color.TRANSPARENT
                        show()
                    }
                    println("Dataa")
                },
                menuItem("Configure", "fas-code:16:1e2e4a", Combo(KeyCode.S, ALT_DOWN)) {},
                SeparatorMenuItem(),
                menuItem("Select View", "fas-table:16:1e2e4a", Combo(KeyCode.V, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    selectView()
                    resetDisplay()
                },
                menuItem("Deselect View", "fas-table:16:1e2e4a", Combo(KeyCode.V, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    model.columnHeaders = initialFrame.cols.map { col -> col.name }.toMutableList()
                    resetDisplay()
                },
                menuItem("Make Pivot Table", null, Combo(KeyCode.B, SHORTCUT_DOWN, SHIFT_DOWN)) { }
        )
    }

    private fun applyColorScale() {
        val c = model.colorScaleColumns
                .filter { spreadsheetFrame[it.columnName][0] is Number }
                .map {
                    ColorScaleColumnInput(
                            model.columnHeaders.indexOf(it.columnName),
                            model.initialFrame[it.columnName].max(true)!!,
                            model.initialFrame[it.columnName].min(true)!!,
                            it.isGood
                    )
                }

        grid.rows.forEach { row ->
            c.forEach {
                val item = row[it.index].item
                if (item is Number) {
                    val scale = (item.toDouble() - it.minVal) / it.maxVal
                    val color: String
                    color = if (it.isGood) {
                        colorScale(scale, 0, 192, 0)
                    } else {
                        colorScale(scale, 255, 0, 0)
                    }
                    row[it.index].let { it0 ->
                        it0.styleClass.add("reload-fix") // this reloads the style
                        it0.style = "-fx-background-color: #$color"
                    }
                }
            }
        }
    }

    private fun selectView() {
        model.columnHeaders = getSelectedColumns().toMutableList()
        model.sortColumns.removeIf { it.columnName !in model.columnHeaders }
        model.filterRows.removeIf { it.columnName !in model.columnHeaders }
        model.colorScaleColumns.removeIf { it.columnName !in model.columnHeaders }
    }

    private fun resetDisplay() {
        spreadsheetFrame = model.mutateFrame()
        addSortArrows()
        applyColorScale()
    }

    private fun addSortArrows() {
        for (sortColumn in model.sortColumns) {
            for ((i, columnHeader) in model.columnHeaders.withIndex()) {
                if (columnHeader == sortColumn.columnName) {
                    grid.columnHeaders[i] = sortColumn.columnName + when (sortColumn.sortType) {
                        SortType.Ascending -> " \u25B4"
                        SortType.Descending -> " \u25be"
                    }
                }
            }
        }
    }

    private fun updateNewData() {
        val text = Text()
        text.font = Font.font("sans-serif", FontWeight.BOLD, 14.0)
        val fixedMetrics = listOf("Team", "Match", "Match Type", "Alliance", "Scout", "Event", "Year")
        columns.forEachIndexed { index, column ->
            val modelCol = getModelColumn(index)
            val name = model.initialFrame.cols[modelCol].name.replace("[^A-Za-z0-9 ]".toRegex(), "")
            if (name in fixedMetrics) column.isFixed = true
            text.text = name
            val width = text.layoutBounds.width + 30
            column.minWidth = width
        }
    }

    private fun getSelectedColumns() = selectionModel.selectedCells
            .map { model.columnHeaders[it.column] }.toSet()

    private fun getSelectedColumnsValues() = selectionModel.selectedCells
            .map { model.columnHeaders[it.column] to grid.rows[it.row][it.column].item }.toSet()
}