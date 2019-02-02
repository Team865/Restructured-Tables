package ca.warp7.rt.core

import ca.warp7.rt.core.model.Contexts
import javafx.application.Application

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        Contexts.args = args
        Contexts.version = "2019.1.1-alpha"
        Contexts.root
        Application.launch(Restructured::class.java)
    }
}
