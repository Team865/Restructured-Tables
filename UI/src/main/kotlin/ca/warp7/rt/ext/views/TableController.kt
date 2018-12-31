package ca.warp7.rt.ext.views

import javafx.application.Platform
import javafx.scene.layout.BorderPane
import krangl.DataFrame
import krangl.readDelim
import org.apache.commons.csv.CSVFormat

class TableController {

    lateinit var tableContainer: BorderPane

    fun initialize() {
        Thread {
            val inputStream = javaClass.getResourceAsStream("/ca/warp7/rt/res/test.csv")
            val df = DataFrame.readDelim(
                    inStream = inputStream,
                    format = CSVFormat.DEFAULT.withHeader().withNullString(""),
                    isCompressed = false,
                    colTypes = mapOf())
            val sheet = DataFrameView(df)
            Platform.runLater { tableContainer.center = sheet }
        }.start()
    }
}

