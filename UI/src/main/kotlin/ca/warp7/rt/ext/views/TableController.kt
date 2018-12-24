package ca.warp7.rt.ext.views

import javafx.collections.FXCollections
import javafx.scene.layout.BorderPane
import krangl.DataFrame
import krangl.dataFrameOf
import org.controlsfx.control.spreadsheet.GridBase
import org.controlsfx.control.spreadsheet.SpreadsheetCellType
import org.controlsfx.control.spreadsheet.SpreadsheetView

class TableController {

    lateinit var tableContainer: BorderPane

    fun initialize() {

        val df: DataFrame = dataFrameOf(
                "first_name", "last_name", "age", "weight")(
                "Max", "Doe", 23, 55,
                "Franz", "Smith", 23, 88,
                "Horst", "Keanes", 12, 82
        )

        val grid = GridBase(50, 30)
//        df.cols.forEachIndexed { col, dataCol ->
//            dataCol.values().forEachIndexed { row, any ->
//                grid.setCellValue(row, col, any)
//            }
//        }

        for (i in 0..50) {
            grid.rows.add(FXCollections.observableArrayList())
            for (j in 0..df.ncol * 30) {
                grid.rows[i].add(SpreadsheetCellType.DOUBLE.createCell(i, j, 1, 1,
                        i.toDouble() * j))
            }
        }

        val spreadsheetView = SpreadsheetView(grid)
        spreadsheetView.columns.forEach { it.minWidth = 80.0 }
        spreadsheetView.grid.rowHeaders.apply {
            clear()
            add("hi")
            add("ho")
        }
        spreadsheetView.grid.columnHeaders.apply {
            add("Team")
        }
        spreadsheetView.isEditable = false
        tableContainer.center = spreadsheetView
    }
}
