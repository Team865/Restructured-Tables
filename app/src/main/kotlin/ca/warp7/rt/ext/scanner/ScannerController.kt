package ca.warp7.rt.ext.scanner

import com.github.sarxos.webcam.Webcam
import com.github.sarxos.webcam.WebcamResolution
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.client.j2se.BufferedImageLuminanceSource
import com.google.zxing.common.HybridBinarizer
import javafx.application.Platform
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.embed.swing.SwingFXUtils
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.layout.VBox
import java.awt.image.BufferedImage
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class ScannerController {

    lateinit var streamImageView: ImageView
    lateinit var imageContainer: VBox
    lateinit var resultLabel: Label
    lateinit var comments: TextArea
    lateinit var scanList: ListView<ScannerEntry>
    lateinit var cameraStateChanger: Button

    private lateinit var resultProperty: StringProperty

    private var isStreaming: Boolean = false
    private val webcam = Webcam.getDefault()
    private val imageProperty = SimpleObjectProperty<Image>()
    private val scannerEntries = FXCollections.observableArrayList<ScannerEntry>()

    fun initialize() {
        resultProperty = resultLabel.textProperty()
        startCameraStream()
        scanList.items = scannerEntries
        initializeListFactory()
        streamImageView.fitWidthProperty().bind(imageContainer.widthProperty())
        streamImageView.fitHeightProperty().bind(imageContainer.heightProperty())
    }

    internal fun stopCameraStream() {
        isStreaming = false
        cameraStateChanger.text = "Start"
    }

    fun onCameraStateChange() {
        if (isStreaming) stopCameraStream()
        else startCameraStream()
    }

    private fun initializeListFactory() {
        scanList.setCellFactory {
            object : ListCell<ScannerEntry>() {
                override fun updateItem(item: ScannerEntry?, empty: Boolean) {
                    super.updateItem(item, empty)
                    prefHeight = 50.0
                    if (empty || item == null) {
                        text = null
                        graphic = null
                        return
                    }
                    graphic = ScannerElement.cellFromEntry(item)
                }
            }
        }
    }

    private fun startCameraStream() {
        cameraStateChanger.text = "Pause"
        if (webcam.isOpen) webcam.close()
        webcam.viewSize = WebcamResolution.VGA.size
        webcam.setCustomViewSizes(WebcamResolution.VGA.size)
        webcam.open()
        isStreaming = true
        val thread = Thread {
            val imgRef = AtomicReference<WritableImage>()
            var image: BufferedImage?
            var notFoundCount = 0
            var previousResultText = ""
            while (isStreaming) {
                image = webcam.image
                if (image != null) {
                    try {
                        val result = MultiFormatReader().decode(BinaryBitmap(
                                HybridBinarizer(BufferedImageLuminanceSource(image))))
                        notFoundCount = 0
                        val resultText = result.text
                        if (previousResultText != resultText) {
                            previousResultText = resultText
                            Platform.runLater { onQRCodeResult(resultText) }
                        }
                    } catch (ignored: NotFoundException) {
                        notFoundCount++
                        if (notFoundCount > 10) {
                            Platform.runLater {
                                resultLabel.styleClass.remove("qr-found")
                                resultProperty.set("No QR code found")
                            }
                            notFoundCount = 0
                        }
                    }

                    imgRef.set(SwingFXUtils.toFXImage(image, imgRef.get()))
                    image.flush()
                    Platform.runLater { imageProperty.set(imgRef.get()) }
                }
            }
            webcam.close()
        }
        thread.isDaemon = false
        thread.start()
        streamImageView.imageProperty().bind(imageProperty)
    }

    private fun onQRCodeResult(result: String) {
        resultProperty.set(result)
        resultLabel.styleClass.add("qr-found")
        val split = result.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (split.size > 5) {
            val sdfDate = SimpleDateFormat("HH:mm:ss")
            val now = Date()
            val timestamp = sdfDate.format(now)
            scannerEntries.add(ScannerEntry(true, split[1], split[4] + ":" + split[2], timestamp))
        }
    }
}
