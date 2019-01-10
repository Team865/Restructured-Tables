package ca.warp7.rt.core.app

import ca.warp7.rt.ext.ast.ASTFeature
import ca.warp7.rt.ext.dash.DashboardFeature
import ca.warp7.rt.ext.media.MediaFeature
import ca.warp7.rt.ext.predictor.PredictorFeature
import ca.warp7.rt.ext.python.PythonFeature
import ca.warp7.rt.ext.scanner.ScannerFeature
import ca.warp7.rt.ext.vc.VCFeature
import ca.warp7.rt.ext.views.ViewsFeature
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.VBox

@Suppress("unused")
fun unused() {
    PythonFeature()
    DashboardFeature()
}

internal val appFeatures = listOf(
        ViewsFeature(),
        ASTFeature(),
        PredictorFeature(),
        MediaFeature(),
        ScannerFeature(),
        VCFeature()
)

internal var utilsController: AppController? = null

fun userInputString(title: String, prompt: String, defVal: String,
                    validator: ((value: String) -> Boolean)? = null): String? {
    val dialog = Dialog<String>()
    dialog.title = title
    if (utilsController != null) dialog.initOwner(utilsController!!.appStage)
    val vBox = VBox()
    vBox.spacing = 10.0
    vBox.alignment = Pos.TOP_LEFT
    vBox.style = "-fx-padding: 10"
    val label = Label(prompt)
    label.isWrapText = true
    val field = TextField()
    field.text = defVal.trim { it <= ' ' }
    dialog.dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)
    if (validator != null) {
        val okButton = dialog.dialogPane.lookupButton(ButtonType.OK)
        okButton.isDisable = true
        field.textProperty().addListener { _, _, newValue ->
            okButton.isDisable = !validator.invoke(newValue.trim { it <= ' ' })
        }
    }
    vBox.children.addAll(label, field)
    vBox.minWidth = 400.0
    dialog.dialogPane.content = vBox
    Platform.runLater {
        field.requestFocus()
    }
    dialog.setResultConverter { buttonType ->
        if (buttonType == ButtonType.OK) field.text else null
    }
    val result = dialog.showAndWait()
    return result.orElse(null)
}

fun setAppStatus(statusMessage: String) {
    if (utilsController != null) {
        utilsController!!.statusMessageLabel.text = statusMessage
    }
}
