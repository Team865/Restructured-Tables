package ca.warp7.tables.controller;

import ca.warp7.tables.controller.utils.StageController;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

public class ScannerController implements StageController {

    @FXML
    ImageView streamImageView;

    @FXML
    Label resultLabel;

    @FXML
    TextArea comments;

    @FXML
    ListView scanList;

    @FXML
    Label red1Team;

    @FXML
    Label red2Team;

    @FXML
    Label red3Team;

    @FXML
    Label blue1Team;

    @FXML
    Label blue2Team;

    @FXML
    Label blue3Team;

    @FXML
    Label red1Scout;

    @FXML
    Label red2Scout;

    @FXML
    Label red3Scout;

    @FXML
    Label blue1Scout;

    @FXML
    Label blue2Scout;

    @FXML
    Label blue3Scout;

    @FXML
    Label currentMatch;

    private StringProperty resultProperty;
    private Stage stage;
    private Webcam webcam;
    private boolean isStreaming;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    @FXML
    void initialize() {
        resultProperty = resultLabel.textProperty();
        webcam = Webcam.getDefault();
        webcam.setCustomViewSizes(WebcamResolution.VGA.getSize());
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();
        isStreaming = true;
        Thread thread = new Thread(() -> {
            final AtomicReference<WritableImage> imgRef = new AtomicReference<>();
            BufferedImage image;
            while (isStreaming) {
                image = webcam.getImage();
                if (image != null) {
                    try {
                        try {
                            Result result = new MultiFormatReader().decode(new BinaryBitmap(
                                    new HybridBinarizer(new BufferedImageLuminanceSource(image))));
                            Platform.runLater(() -> resultProperty.set(result.getText()));
                        } catch (NotFoundException ignored) {
                        }

                        imgRef.set(SwingFXUtils.toFXImage(image, imgRef.get()));
                        image.flush();
                        Platform.runLater(() -> imageProperty.set(imgRef.get()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            webcam.close();
        });
        thread.setDaemon(true);
        thread.start();
        streamImageView.imageProperty().bind(imageProperty);
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setOnCloseRequest(Event::consume);
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                onDiscard();
            }
        });
    }

    @FXML
    void onDiscard() {
        isStreaming = false;
        stage.close();
    }
}
