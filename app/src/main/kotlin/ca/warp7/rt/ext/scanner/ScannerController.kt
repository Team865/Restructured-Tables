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
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
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
    lateinit var scanList: ListView<DecodedEntry>

    private lateinit var resultProperty: StringProperty

    private var isStreaming: Boolean = false
    private val webcam = Webcam.getDefault()
    private val imageProperty = SimpleObjectProperty<Image>()
    private val scannerEntries = FXCollections.observableArrayList<DecodedEntry>()

    fun initialize() {
        resultProperty = resultLabel.textProperty()
        resultLabel.lineSpacing = 5.0
        startCameraStream()
        scanList.items = scannerEntries
        initializeListFactory()
        streamImageView.fitWidthProperty().bind(imageContainer.widthProperty())
        streamImageView.fitHeightProperty().bind(imageContainer.heightProperty())
        onNoQRCodeFound()
    }

    internal fun stopCameraStream() {
        isStreaming = false
    }

    @Suppress("unused")
    fun onCameraStateChange() {
        if (isStreaming) stopCameraStream()
        else startCameraStream()
    }

    private fun initializeListFactory() {
        scanList.setCellFactory {
            object : ListCell<DecodedEntry>() {
                override fun updateItem(item: DecodedEntry?, empty: Boolean) {
                    super.updateItem(item, empty)
                    prefHeight = 50.0
                    if (empty || item == null) {
                        text = null
                        graphic = null
                        return
                    }
                    graphic = cellFromEntry(item)
                }
            }
        }
    }

    private fun startCameraStream() {
        if (webcam.isOpen) webcam.close()
        webcam.viewSize = WebcamResolution.VGA.size
        webcam.setCustomViewSizes(WebcamResolution.VGA.size)
        webcam.open()
        isStreaming = true
        val thread = Thread {
            val imgRef = AtomicReference<WritableImage>()
            var image: BufferedImage?
            var notFoundCount = 0
            var lastEntry = ""
            while (isStreaming) {
                image = webcam.image
                if (image != null) {
                    try {
                        val result = MultiFormatReader().decode(BinaryBitmap(
                                HybridBinarizer(BufferedImageLuminanceSource(image))))
                        notFoundCount = 0
                        val resultText = result.text
                        if (resultText != lastEntry) {
                            Platform.runLater { onQRCodeResult(resultText) }
                            lastEntry = resultText
                        }
                    } catch (ignored: NotFoundException) {
                        notFoundCount++
                        if (notFoundCount > 3) {
                            Platform.runLater { onNoQRCodeFound() }
                            lastEntry = ""
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
        resultLabel.style = "-fx-background-color: lightgreen;-fx-padding: 10;"
        val split = result.split(":".toRegex()).toTypedArray()
        val sdfDate = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val time = Date(split[4].toLong(16) * 1000)
        val timestamp = sdfDate.format(time)
        if (split.size > 7) {
            resultProperty.set("""
Match: ${split[0]}
Team: ${split[1]}
Scout: ${split[2]}
Board: ${split[3]}
Time: $timestamp
Data Points: ${split[6].length / 4}
Comments: ${split[7]}""".trim())
            scannerEntries.add(DecodedEntry(result))
        }
    }

    private fun onNoQRCodeFound() {
        resultLabel.style = "\n" +
                "-fx-padding: 10;\n" +
                "-fx-background-color: #ddd;\n";
    }
}
