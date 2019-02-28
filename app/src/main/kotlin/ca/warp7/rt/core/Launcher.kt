package ca.warp7.rt.core

import ca.warp7.rt.core.model.Contexts
import javafx.application.Application

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        Contexts.args = args
        Contexts.root
        Application.launch(Restructured::class.java)
    }
}
