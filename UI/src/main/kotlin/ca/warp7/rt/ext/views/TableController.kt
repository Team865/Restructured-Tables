package ca.warp7.rt.ext.views

import ca.warp7.rt.core.feature.FeatureIcon
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
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
            val name = grid.columnHeaders[sheet.getModelColumn(index)]
                    .replace("[^A-Za-z0-9 ]".toRegex(), "")
            if (name in fixedMetrics) column.isFixed = true
            text.text = name
            column.setResizable(true)
            column.minWidth = text.layoutBounds.width + 20
//            column.setPrefWidth(text.layoutBounds.width + 20)
        }
        sheet.contextMenu.items.apply { clear() }.addAll(
                MenuItem("Copy cells", FeatureIcon("fas-copy:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN)
                },
                MenuItem("Copy table", FeatureIcon("fas-table:16:1e2e4a")).apply {
                    accelerator = KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN)
                },

                SeparatorMenuItem(),
                Menu("Sort Columns", FeatureIcon("fas-sort:16:1e2e4a")).apply {
                    items.addAll(
                            MenuItem("Set primary column 1-9, A-Z", FeatureIcon("fas-sort-amount-up:16:1e2e4a")),
                            MenuItem("Set primary column 9-1, Z-A", FeatureIcon("fas-sort-amount-down:16:1e2e4a")),
                            SeparatorMenuItem(),
                            MenuItem("Add secondary column 1-9, A-Z", FeatureIcon("fas-sort-amount-up:16:1e2e4a")),
                            MenuItem("Add secondary column 9-1, Z-A", FeatureIcon("fas-sort-amount-down:16:1e2e4a")),
                            SeparatorMenuItem(),
                            MenuItem("Clear all")
                    )
                },

                Menu("Colour scale", FeatureIcon("fas-paint-brush:16:1e2e4a")).apply {
                    items.addAll(
                            MenuItem("Toggle Direction"),
                            SeparatorMenuItem(),
                            MenuItem("Red"),
                            MenuItem("Green"),
                            MenuItem("Red to Green"),
                            MenuItem("None")
                    )
                },

                Menu("Filter Rows", FeatureIcon("fas-filter:16:1e2e4a")).apply {
                    items.addAll(
                            MenuItem("Add values for each column"),
                            MenuItem("Exclude values for each column"),
                            MenuItem("Clear and apply"),
                            MenuItem("Clear and exclude")
                    )
                },

                Menu("Highlight", FeatureIcon("fas-adjust:16:1e2e4a")).apply {
                    items.addAll(
                            MenuItem("Selected cells"),
                            MenuItem("Selected values"),
                            MenuItem("Rows with selected values")
                    )
                }
        )

        sheet.isShowColumnHeader = true
        sheet.isShowRowHeader = true
        sheet.isEditable = false
        tableContainer.center = sheet
    }
}
