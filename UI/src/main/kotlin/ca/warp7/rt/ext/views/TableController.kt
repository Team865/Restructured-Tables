package ca.warp7.rt.ext.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.layout.BorderPane
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.Text
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCell
import org.controlsfx.control.spreadsheet.SpreadsheetCellType
import org.controlsfx.control.spreadsheet.SpreadsheetView
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
                    is Double -> SpreadsheetCellType.DOUBLE.createCell(i, j, 1, 1, value)
                    is Int -> SpreadsheetCellType.INTEGER.createCell(i, j, 1, 1, value)
                    is LocalDate -> SpreadsheetCellType.DATE.createCell(i, j, 1, 1, value)
                    is String -> SpreadsheetCellType.STRING.createCell(i, j, 1, 1, value)
                    else -> SpreadsheetCellType.STRING.createCell(i, j, 1, 1, value.toString())
                })
            }
            grid.rows.add(rowList)
        }

        grid.columnHeaders.addAll(df.cols.map { it.name })

        val spreadsheetView = SpreadsheetView(grid)
        val text = Text()
        text.font = Font.font("sans-serif", FontWeight.BOLD, 14.0)
        val fixedMetrics = listOf("Team", "Match", "Entry", "Alliance", "Scout", "Event", "Year")
        spreadsheetView.columns.forEachIndexed { index, column ->
            val name = grid.columnHeaders[index].replace("[^A-Za-z0-9 ]".toRegex(), "")
            println(name)
            if (name in fixedMetrics) column.isFixed = true
            text.text = name
            val width = text.layoutBounds.width
            column.minWidth = width + 20
        }
        spreadsheetView.isShowColumnHeader = true
        spreadsheetView.isShowRowHeader = true
        spreadsheetView.isEditable = false
        tableContainer.center = spreadsheetView
    }
}
