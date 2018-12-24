package ca.warp7.rt.ext.views

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.layout.BorderPane
import krangl.DataFrame
import krangl.dataFrameOf
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCell
import org.controlsfx.control.spreadsheet.SpreadsheetCellType
import org.controlsfx.control.spreadsheet.SpreadsheetView
import java.time.LocalDate

class TableController {

    lateinit var tableContainer: BorderPane

    private fun getCreateCellList(): ObservableList<SpreadsheetCell> = FXCollections.observableArrayList<SpreadsheetCell>()

    fun initialize() {

        val df: DataFrame = dataFrameOf(
                "first_name", "last_name", "age", "weight")(
                "Max", "Doe", 23, 55,
                "Franz", "Smith", 23, 88,
                "Horst", "Keanes", 12, 82
        )

        val grid = GridBase(3, 4)
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
        spreadsheetView.columns.forEach { it.minWidth = 80.0 }
        spreadsheetView.isShowColumnHeader = true
        spreadsheetView.isShowRowHeader = false
        spreadsheetView.isEditable = true
        tableContainer.center = spreadsheetView
    }
}
