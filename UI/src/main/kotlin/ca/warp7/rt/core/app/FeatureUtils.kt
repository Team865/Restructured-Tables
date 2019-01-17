package ca.warp7.rt.core.app

import ca.warp7.rt.context.api.Context
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.stage.Stage
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

fun showStage(resFile: String, windowTitle: String) {
    val stage = Stage()
    val loader = FXMLLoader()
    loader.location = caller.getResource(resFile)
    stage.title = windowTitle
    stage.icons.add(Image(caller.getResourceAsStream("/ca/warp7/rt/res/app-icon.png")))
    try {
        stage.scene = Scene(loader.load())
        val controller = loader.getController<Any>()
        if (controller is FeatureStage) {
            controller.setStage(stage)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    stage.show()
}