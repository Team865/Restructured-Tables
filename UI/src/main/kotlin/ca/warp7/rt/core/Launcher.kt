package ca.warp7.rt.core

import ca.warp7.rt.context.root.Contexts
import javafx.application.Application

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        Contexts.loadRoot(args)
        Application.launch(Restructured::class.java)
    }
}
