package ca.warp7.rt.ext.views

import ca.warp7.rt.core.feature.FeatureIcon
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.SelectionMode
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination.ALT_DOWN
import javafx.scene.input.KeyCombination.SHORTCUT_DOWN
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCell
import org.controlsfx.control.spreadsheet.SpreadsheetCellType.*
import java.time.LocalDate

class TableController {

    lateinit var tableContainer: BorderPane

    private fun getCreateCellList(): ObservableList<SpreadsheetCell> = FXCollections.observableArrayList<SpreadsheetCell>()

    fun initialize() {

        val inputStream = javaClass.getResourceAsStream("/ca/warp7/rt/res/test.csv")

        val df: DataFrame = DataFrame.readDelim(
                inStream = inputStream,
                format = CSVFormat.DEFAULT.withHeader(),
                isCompressed = false,
                colTypes = mapOf())

        val grid = GridBase(df.nrow, df.ncol)
        df.rows.forEachIndexed { i, row ->
            val rowList = getCreateCellList()
            row.values.forEachIndexed { j, value ->
                rowList.add(when (value) {
                    null -> null
                    is Double -> DOUBLE.createCell(i, j, 1, 1, value)
                    is Int -> INTEGER.createCell(i, j, 1, 1, value)
                    is LocalDate -> DATE.createCell(i, j, 1, 1, value)
                    is String -> STRING.createCell(i, j, 1, 1, value)
                    else -> STRING.createCell(i, j, 1, 1, value.toString())
                })
            }
            grid.rows.add(rowList)
        }
        grid.columnHeaders.addAll(df.cols.map { it.name })
        val sheet = Spreadsheet(grid)
        val text = Text()
        text.font = Font.font("sans-serif", FontWeight.BOLD, 14.0)
        val fixedMetrics = listOf("Team", "Match", "Match Type", "Entry", "Alliance", "Scout", "Event", "Year")
        sheet.columns.forEachIndexed { index, column ->
            val calIx = sheet.getModelColumn(index)
            val name = grid.columnHeaders[calIx].replace("[^A-Za-z0-9 ]".toRegex(), "")
            if (name in fixedMetrics) column.isFixed = true
            text.text = name
            column.setResizable(true)
            column.minWidth = text.layoutBounds.width + 20
        }
        sheet.selectionModel.selectionMode = SelectionMode.MULTIPLE
        sheet.contextMenu.items.addAll(

                Menu("Copy", FeatureIcon("fas-copy:16:1e2e4a")).apply {
                    items.addAll(
                            MenuItem("Selected cells").apply {
                                accelerator = KeyCodeCombination(KeyCode.C, SHORTCUT_DOWN)
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
                    accelerator = KeyCodeCombination(KeyCode.PLUS, SHORTCUT_DOWN)
                },
                MenuItem("Zoom Out", FeatureIcon("fas-search-plus:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.MINUS, SHORTCUT_DOWN)
                },
                MenuItem("Reset Zoom").apply {
                    accelerator = KeyCodeCombination(KeyCode.DIGIT0, SHORTCUT_DOWN)
                },

                SeparatorMenuItem(),

                MenuItem("Sort Ascending", FeatureIcon("fas-sort-amount-up:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.PLUS, ALT_DOWN)
                },
                MenuItem("Sort Descending", FeatureIcon("fas-sort-amount-down:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.MINUS, ALT_DOWN)
                },
                MenuItem("Clear sort").apply {
                    accelerator = KeyCodeCombination(KeyCode.DIGIT0, ALT_DOWN)
                },

                SeparatorMenuItem(),

                MenuItem("Filter", FeatureIcon("fas-filter:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.I, SHORTCUT_DOWN)
                },

                MenuItem("Filter Out").apply {
                    accelerator = KeyCodeCombination(KeyCode.O, SHORTCUT_DOWN)
                },

                MenuItem("Clear Filters").apply {
                    accelerator = KeyCodeCombination(KeyCode.U, SHORTCUT_DOWN)
                },

                SeparatorMenuItem(),

                MenuItem("Toggle Formatting", FeatureIcon("fas-toggle-on:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.SLASH, SHORTCUT_DOWN)
                },

                MenuItem("Colour Scale Up", FeatureIcon("fas-caret-up:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.PLUS, SHORTCUT_DOWN, ALT_DOWN)
                },
                MenuItem("Colour Scale Down", FeatureIcon("fas-caret-down:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.MINUS, SHORTCUT_DOWN, ALT_DOWN)
                },
                MenuItem("Clear Colour Scales").apply {
                    accelerator = KeyCodeCombination(KeyCode.DIGIT0, SHORTCUT_DOWN, ALT_DOWN)
                },

                MenuItem("Highlight Cells", FeatureIcon("fas-adjust:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.H, ALT_DOWN)
                },

                MenuItem("Clear Highlighting").apply {
                    accelerator = KeyCodeCombination(KeyCode.SLASH, ALT_DOWN)
                },

                SeparatorMenuItem(),

                MenuItem("Data Summary", FeatureIcon("fas-calculator:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.C, ALT_DOWN)
                },

                SeparatorMenuItem(),

                Menu("Extract new View", FeatureIcon("fas-table:16:1e2e4a")).apply {
                    items.addAll(
                            MenuItem("Selected Cells"),
                            MenuItem("Selected Rows"),
                            MenuItem("Selected Columns"),
                            MenuItem("Pivot Table")
                    )
                },

                MenuItem("Configure", FeatureIcon("fas-code:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.S, ALT_DOWN)
                }
        )
        sheet.isShowColumnHeader = true
        sheet.isShowRowHeader = true
        sheet.isEditable = false
        tableContainer.center = sheet
    }
}
