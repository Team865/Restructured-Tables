package ca.warp7.rt.context.api

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.layout.HBox
import java.io.IOException

fun loadParent(resFile: String): Parent {
    return try {
        val loader = FXMLLoader()
        loader.location = caller.getResource(resFile)
        loader.load()
    } catch (e: IOException) {
        e.printStackTrace()
        HBox()
    }
}

private val caller: Class<*>
    get() {
        val caller = Thread.currentThread().stackTrace[3]
        return try {
            Class.forName(caller.className)
        } catch (e: ClassNotFoundException) {
            Context::class.java
        }
    }

fun <T> loadParent(resFile: String, controllerCallback: (controller: T) -> Unit): Parent {
    return try {
        val loader = FXMLLoader()
        loader.location = caller.getResource(resFile)
        val parent = loader.load<Parent>()
        controllerCallback.invoke(loader.getController())
        parent
    } catch (e: IOException) {
        e.printStackTrace()
        HBox()
    }
}

