package ca.warp7.tables.controller;

import ca.warp7.tables.controller.utils.StageController;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

public class ScannerController implements StageController {

    @FXML
    ImageView streamImageView;
    private Webcam webcam;
    private boolean isStreaming;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    @FXML
    Label result;
    private StringProperty resultProperty;

    private void onStageClose(WindowEvent event) {
        isStreaming = false;
    }

    @FXML
    void initialize() {
        resultProperty = result.textProperty();
        webcam = Webcam.getDefault();
        webcam.setCustomViewSizes(WebcamResolution.VGA.getSize(), WebcamResolution.HD.getSize());
        webcam.open();
        isStreaming = true;
        Thread thread = new Thread(() -> {
            final AtomicReference<WritableImage> imgRef = new AtomicReference<>();
            BufferedImage image;
            while (isStreaming) {
                image = webcam.getImage();
                if (image != null) {
                    try {
                        try{
                            Result result = new MultiFormatReader().decode(new BinaryBitmap(
                                    new HybridBinarizer(new BufferedImageLuminanceSource(image))));
                            Graphics graphics = image.getGraphics();
                            ResultPoint[] resultPoints = result.getResultPoints();
                            graphics.setColor(Color.BLUE);
                            double px = resultPoints[0].getX();
                            double py = resultPoints[0].getY();
                            double ox = px;
                            double oy = py;
                            for (int i = 1; i < resultPoints.length; i++) {
                                double nx = resultPoints[i].getX();
                                double ny = resultPoints[i].getY();
                                px = nx;
                                py = ny;
                                graphics.drawLine((int) px, (int) py, (int) nx, (int) ny);
                            }
                            graphics.drawLine((int) px, (int) py, (int) ox, (int) oy);
                            Platform.runLater(() -> resultProperty.set(result.getText()));
                        } catch (NotFoundException ignored){
                        }

                        imgRef.set(SwingFXUtils.toFXImage(image, imgRef.get()));
                        image.flush();
                        Platform.runLater(() -> imageProperty.set(imgRef.get()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            webcam.close();
        });
        thread.setDaemon(true);
        thread.start();
        streamImageView.imageProperty().bind(imageProperty);
    }

    @Override
    public void setStage(Stage stage) {
        stage.setOnCloseRequest(this::onStageClose);
    }
}
