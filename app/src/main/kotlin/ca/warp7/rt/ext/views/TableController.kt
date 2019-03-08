package ca.warp7.rt.ext.views

import ca.warp7.rt.ext.humber.Humber
import javafx.application.Platform
import javafx.scene.layout.BorderPane

class TableController {

    lateinit var tableContainer: BorderPane

    fun initialize() {
        Thread {
            val inputStream = javaClass.getResourceAsStream("/ca/warp7/rt/res/test.csv")
//            val df = DataFrame.readDelim(
//                    inStream = inputStream,
//                    format = CSVFormat.DEFAULT.withHeader().withNullString(""),
//                    isCompressed = false,
//                    colTypes = mapOf())
            val sheet = DataFrameView(Humber.process(Humber.getData()))

            Platform.runLater { tableContainer.center = sheet }
        }.start()
    }
}

