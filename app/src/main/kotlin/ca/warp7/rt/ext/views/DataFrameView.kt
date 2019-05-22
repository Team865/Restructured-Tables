package ca.warp7.rt.ext.views

import ca.warp7.rt.core.app.utilsController
import calcCycles
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
import krangl.*
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
                menuItem("Everything", null, Combo(KeyCode.DIGIT1)) {
                    model.columnHeaders = initialFrame.cols.map { col -> col.name }.toMutableList()
                    resetDisplay()
                },
                menuItem("Auto list", null, Combo(KeyCode.DIGIT2) ){
                    selectView(setOf("Match", "Team", "Alliance", "Action 1", "Action 2", "Action 3", "Action 4", "Action 5"))
                },
                menuItem("Sort Ascending", "fas-sort-amount-up:16:1e2e4a", Combo(KeyCode.EQUALS, ALT_DOWN)) {
                    model.addSort(selectedColumns, SortType.Ascending)
                    resetDisplay()
                },
                menuItem("Sort Descending", "fas-sort-amount-down:16:1e2e4a", Combo(KeyCode.MINUS, ALT_DOWN)) {
                    model.addSort(selectedColumns, SortType.Descending)
                    resetDisplay()
                },
                menuItem("Clear sort", null, Combo(KeyCode.DIGIT0, ALT_DOWN)) {
                    model.sortColumns.clear()
                    resetDisplay()
                },
                SeparatorMenuItem(),
                menuItem("Filter By", "fas-filter:16:1e2e4a", Combo(KeyCode.I, SHORTCUT_DOWN)) {
                    model.addFilter(selectedColumnsValues, true)
                    resetDisplay()
                },
                menuItem("Filter Out", null, Combo(KeyCode.I, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    model.addFilter(selectedColumnsValues, false)
                    resetDisplay()
                },
                menuItem("Clear Filters", null, Combo(KeyCode.U, SHORTCUT_DOWN)) {
                    model.filterRows.clear()
                    resetDisplay()
                },
                SeparatorMenuItem(),
                menuItem("Colour Scale Up", "fas-caret-up:16:1e2e4a",
                        Combo(KeyCode.EQUALS, SHORTCUT_DOWN, ALT_DOWN)) {
                    model.addColorScale(selectedColumns, true)
                    resetDisplay()
                },
                menuItem("Colour Scale Down", "fas-caret-down:16:1e2e4a",
                        Combo(KeyCode.MINUS, SHORTCUT_DOWN, ALT_DOWN)) {
                    model.addColorScale(selectedColumns, false)
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

                        val pureValues = selectionModel.selectedCells.map { grid.rows[it.row][it.column].item }
                        val values: MutableList<Double> = mutableListOf()
                        var blanks = 0
                        pureValues.forEach {
                            when (it) {
                                is Number -> values += it.toDouble()
                                else -> blanks++
                            }
                        }

                        val wrapper = VBox(
                                Text("Count: ${values.size}"),
                                Text("Non-numbers: $blanks"),
                                Text("Sum: ${values.sum()}"),
                                Text("Mean: ${values.mean()}"),
                                Text("Max: ${values.max()}"),
                                Text("Min: ${values.min()}")
                        )
                        wrapper.minWidth = 175.0
                        wrapper.minHeight = 125.0
                        scene = Scene(wrapper)
                        opacity = 0.95
                        scene.fill = Color.TRANSPARENT
                        show()
                    }
                },
                menuItem("Configure", "fas-code:16:1e2e4a", Combo(KeyCode.S, ALT_DOWN)) {},
                SeparatorMenuItem(),
                menuItem("Select View", "fas-table:16:1e2e4a", Combo(KeyCode.V, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    selectView()
                    resetDisplay()
                },
                menuItem("Deselect View", null, Combo(KeyCode.B, SHORTCUT_DOWN, SHIFT_DOWN)) {
                    model.columnHeaders = initialFrame.cols.map { col -> col.name }.toMutableList()
                    resetDisplay()
                },
                menuItem("Averages", null, Combo(KeyCode.A, ALT_DOWN)) {
                    Stage().apply {
                        title = "Averages"
                        initStyle(StageStyle.UTILITY)
                        initOwner(utilsController?.appStage)

                        val df = try {
                            spreadsheetFrame.averageBy("Team", selectedColumns.toList())
                        } catch (e: Exception) {
                            emptyDataFrame()
                        }

                        val wrapper = DataFrameView(df, df.names)
                        wrapper.minWidth = 100.0
                        wrapper.minHeight = 100.0

                        scene = Scene(wrapper).apply {
                            stylesheets.add("/ca/warp7/rt/res/style.css")
                        }
                        opacity = 0.95
                        scene.fill = Color.TRANSPARENT
                        show()
                    }
                },
                menuItem("Cycle matrix", null, Combo(KeyCode.X, ALT_DOWN)) {
                    Stage().apply {
                        title = "Cycle matrix"
                        initStyle(StageStyle.UTILITY)
                        initOwner(utilsController?.appStage)

                        val df: DataFrame = spreadsheetFrame.calcCycles("Team", selectedColumns.toList())

                        val wrapper = DataFrameView(df, df.names)
                        wrapper.minWidth = 100.0
                        wrapper.minHeight = 100.0

                        scene = Scene(wrapper).apply {
                            stylesheets.add("/ca/warp7/rt/res/style.css")
                        }
                        opacity = 0.95
                        scene.fill = Color.TRANSPARENT
                        show()
                    }
                },
                menuItem("Good matrix", null, Combo(KeyCode.Z, ALT_DOWN)) {
                    Stage().apply {
                        title = "Cycle matrix"
                        initStyle(StageStyle.UTILITY)
                        initOwner(utilsController?.appStage)

                        val columns = listOf(
                                "Total Hatch Acquired",
                                "Total Cargo Acquired",
                                "Total Defended Time"
                        )
                        val df: DataFrame = spreadsheetFrame.calcCycles("Team", columns)
//                        df.rows.forEachIndexed { i,it ->
//                            df[i]["Total Hatch Acquired times"]
//                        }
                        //TODO divide hatch and cargo by their success rates

                        val wrapper = DataFrameView(df, df.names)
                        wrapper.minWidth = 100.0
                        wrapper.minHeight = 100.0

                        scene = Scene(wrapper).apply {
                            stylesheets.add("/ca/warp7/rt/res/style.css")
                        }
                        opacity = 0.95
                        scene.fill = Color.TRANSPARENT
                        show()
                    }
                }
        )
    }

    private fun applyColorScale() {
        val c = model.colorScaleColumns
                .filter { spreadsheetFrame[it.columnName][0] is Number }
                .map {
                    ColorScaleColumnInput(
                            model.columnHeaders.indexOf(it.columnName),
                            spreadsheetFrame[it.columnName].max(true)!!,
                            spreadsheetFrame[it.columnName].min(true)!!,
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

    private fun selectView(columns: Set<String> = selectedColumns) {
        model.sortColumns.removeIf { it.columnName !in columns }
        model.filterRows.removeIf { it.columnName !in columns }
        model.colorScaleColumns.removeIf { it.columnName !in columns }
        model.columnHeaders = columns.toMutableList()
        updateNewData()
        resetDisplay()
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
        val fixedMetrics = listOf("Team", "Alliance", "Match")//, "Match Type", "Alliance", "Scout", "Event", "Year")
        columns.forEachIndexed { index, column ->
            val modelCol = getModelColumn(index)
            val name = model.initialFrame.cols[modelCol].name.replace("[^A-Za-z0-9 ]".toRegex(), "")
            if (name in fixedMetrics) column.isFixed = true
            text.text = name
            val width = text.layoutBounds.width + 30
            column.minWidth = width
        }
    }

    private val selectedColumns
        get() = selectionModel.selectedCells
                .map { model.columnHeaders[it.column] }.toSet()

    private val selectedColumnsValues
        get() = selectionModel.selectedCells
                .map { model.columnHeaders[it.column] to grid.rows[it.row][it.column].item }.toSet()
}