package ca.warp7.rt.ext.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.SelectionMode
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination.*
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
                menuItem("Sort Ascending", "fas-sort-amount-up:16:1e2e4a", Combo(KeyCode.EQUALS, ALT_DOWN)) {
                },
                menuItem("Sort Descending", "fas-sort-amount-down:16:1e2e4a", Combo(KeyCode.MINUS, ALT_DOWN)) {
                },
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
                menuItem("Extract View", "fas-table:16:1e2e4a", Combo(KeyCode.V, SHORTCUT_DOWN, SHIFT_DOWN)) {
                },
                menuItem("Create Pivot Table", null, Combo(KeyCode.B, SHORTCUT_DOWN, SHIFT_DOWN)) {
                },
                SeparatorMenuItem(),
                menuItem("Configure", "fas-code:16:1e2e4a", Combo(KeyCode.S, ALT_DOWN)) {
                }
        )
        sheet.isShowColumnHeader = true
        sheet.isShowRowHeader = true
        sheet.isEditable = false
        tableContainer.center = sheet
    }
}

