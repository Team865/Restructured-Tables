package ca.warp7.rt.java.scanner;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

public class ScannerController {

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

    @FXML
    ImageView streamImageView;
    @FXML
    VBox imageContainer;
    @FXML
    Label resultLabel;
    @FXML
    TextArea comments;
    @FXML
    ListView<ScannerEntry> scanList;
    @FXML
    Button cameraStateChanger;

    private StringProperty resultProperty;
    private Webcam webcam;
    private boolean isStreaming;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private ObservableList<ScannerEntry> scannerEntries = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        resultProperty = resultLabel.textProperty();
        startCameraStream();
        scanList.setItems(scannerEntries);
        initializeListFactory();
        streamImageView.fitWidthProperty().bind(imageContainer.widthProperty());
        streamImageView.fitHeightProperty().bind(imageContainer.heightProperty());
    }

    void stopCameraStream() {
        isStreaming = false;
        cameraStateChanger.setText("Start");
    }

    @FXML
    void onCameraStateChange() {
        if (isStreaming) stopCameraStream();
        else startCameraStream();
    }

    private void initializeListFactory() {
        scanList.setCellFactory(listView -> new ListCell<ScannerEntry>() {

            @Override
            protected void updateItem(ScannerEntry item, boolean empty) {
                super.updateItem(item, empty);
                setPrefHeight(50);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }

                setGraphic(ScannerUI.cellFromEntry(item));
            }
        });
    }

    private void startCameraStream() {
        cameraStateChanger.setText("Pause");
        webcam = Webcam.getDefault();
        if (webcam.isOpen()) webcam.close();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.setCustomViewSizes(WebcamResolution.VGA.getSize());
        webcam.open();
        isStreaming = true;
        Thread thread = new Thread(() -> {
            final AtomicReference<WritableImage> imgRef = new AtomicReference<>();
            BufferedImage image;
            int notFoundCount = 0;
            String previousResultText = "";
            while (isStreaming) {
                image = webcam.getImage();
                if (image != null) {
                    try {
                        try {
                            Result result = new MultiFormatReader().decode(new BinaryBitmap(
                                    new HybridBinarizer(new BufferedImageLuminanceSource(image))));
                            notFoundCount = 0;
                            String resultText = result.getText();
                            if (!previousResultText.equals(resultText)) {
                                previousResultText = resultText;
                                Platform.runLater(() -> onQRCodeResult(resultText));
                            }
                        } catch (NotFoundException ignored) {
                            notFoundCount++;
                            if (notFoundCount > 15) {
                                Platform.runLater(() -> resultProperty.set("No QR code found"));
                                notFoundCount = 0;
                            }
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

    private void onQRCodeResult(String result) {
        resultProperty.set(result);
        String[] split = result.split("_");
        if (split.length > 5) {
            SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss");
            Date now = new Date();
            String timestamp = sdfDate.format(now);
            scannerEntries.add(new ScannerEntry(true, split[1],
                    split[4] + ":" + split[2], timestamp));
        }
    }
}
