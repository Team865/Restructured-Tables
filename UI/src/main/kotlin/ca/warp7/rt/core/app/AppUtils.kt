package ca.warp7.rt.core.app

import ca.warp7.rt.context.api.get
import ca.warp7.rt.context.model.Contexts
import ca.warp7.rt.context.model.Metadata
import ca.warp7.rt.core.Restructured
import ca.warp7.rt.ext.ast.ASTFeature
import ca.warp7.rt.ext.predictor.PredictorFeature
import ca.warp7.rt.ext.python.PythonFeature
import ca.warp7.rt.ext.scanner.ScannerFeature
import ca.warp7.rt.ext.views.ViewsFeature
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Screen
import javafx.stage.Stage
import javafx.stage.WindowEvent
import java.io.IOException

@Suppress("unused")
fun unused() {
    PythonFeature()
}

internal val appFeatures = listOf(
        ViewsFeature(),
        ASTFeature(),
        PredictorFeature(),
        ScannerFeature()
)

internal var utilsController: AppController? = null

private val teamColor: Color = Color.valueOf("1e2e4a")

internal fun tabUIFromLink(wrapper: FeatureWrapper): HBox {
    val outer = HBox()
    val inner = HBox()
    inner.prefWidth = 20.0
    inner.alignment = Pos.CENTER
    wrapper.icon.iconColor = teamColor
    wrapper.icon.iconSize -= 2
    inner.children.add(wrapper.icon)
    outer.alignment = Pos.CENTER_LEFT
    outer.spacing = 12.0
    outer.children.add(inner)
    val label = Label(wrapper.link.title)
    label.style = "-fx-font-size:16; -fx-font-weight:bold"
    outer.children.add(label)
    return outer
}

fun setAppStatus(statusMessage: String) {
    if (utilsController != null) utilsController!!.statusMessageLabel.text = statusMessage
}

fun getAndSaveUserSettings() {
    val dialog = Dialog<SettingsData>()

    dialog.title = "Settings"
    dialog.initOwner(utilsController?.appStage)

    val vBox = VBox()
    vBox.spacing = 10.0
    vBox.alignment = Pos.TOP_LEFT
    vBox.style = "-fx-padding: 10"

    val userLabel = Label("App User (First Last)")

    val userField = TextField()
    userField.text = Contexts.metadata[Metadata.appUser, System.getProperty("user.name")]

    vBox.children.addAll(userLabel, userField)

    val dsLabel = Label("Data Source (e.g. Team0000)")
    val dsField = TextField()
    dsField.text = Contexts.metadata[Metadata.dataSource, "Team865"]

    vBox.children.addAll(dsLabel, dsField)

    val tbaLabel = Label("The Blue Alliance API Key")
    val tbaField = TextField()
    tbaField.text = Contexts.metadata[Metadata.tbaKey, ""]

    vBox.children.addAll(tbaLabel, tbaField)

    vBox.minWidth = 640.0

    dialog.dialogPane.content = vBox
    dialog.dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)

    val window = dialog.dialogPane.scene.window
    window.addEventHandler(WindowEvent.WINDOW_SHOWN) {
        val screenBounds = Screen.getPrimary().visualBounds
        window.x = (screenBounds.width - window.width) / 2
        window.y = (screenBounds.height - window.height) / 2
    }

    val okButton = dialog.dialogPane.lookupButton(ButtonType.OK)
    okButton.isDisable = true
    userField.textProperty().addListener { _, _, newValue ->
        okButton.isDisable = newValue.isEmpty()
    }

    Platform.runLater {
        userField.requestFocus()
    }

    dialog.setResultConverter {
        if (it == ButtonType.OK) {
            SettingsData(userField.text, dsField.text, tbaField.text)
        } else null
    }

    val settings = dialog.showAndWait().orElse(null)

    settings?.apply {
        Contexts.apply {
            metadata[Metadata.appUser] = user
            metadata[Metadata.dataSource] = dataSource
            metadata[Metadata.tbaKey] = tbaKey
            root.save()
        }
    }
}

fun showStage(resFile: String, windowTitle: String) {
    val stage = Stage()
    val loader = FXMLLoader()
    loader.location = Restructured::class.java.getResource(resFile)
    stage.title = windowTitle
    stage.icons.add(Image(Restructured::class.java.getResourceAsStream("/ca/warp7/rt/res/app-icon.png")))
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