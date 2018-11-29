package ca.warp7.tables.view;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;


/**
 * This example demonstrates how to use Webcam Capture API in a JavaFX application.
 *
 * @author Rakesh Bhatt (rakeshbhatt10)
 */
@SuppressWarnings("Duplicates")
public class WebCamWindow {

    private FlowPane bottomCameraControlPane;
    private ImageView imgWebCamCapturedImage;
    private Webcam webCam = null;
    private boolean stopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();
    private BorderPane webCamPane;
    private Button btnCameraStop;
    private Button btnCameraStart;

    public void start() {
        Stage stage = new Stage();

        stage.setTitle("Connecting Camera Device Using Webcam Capture API");

        BorderPane root = new BorderPane();

        FlowPane topPane = new FlowPane();
        topPane.setAlignment(Pos.CENTER);
        topPane.setHgap(20);
        topPane.setOrientation(Orientation.HORIZONTAL);
        topPane.setPrefHeight(40);
        root.setTop(topPane);

        webCamPane = new BorderPane();
        webCamPane.setStyle("-fx-background-color: #ccc;");
        imgWebCamCapturedImage = new ImageView();
        webCamPane.setCenter(imgWebCamCapturedImage);
        root.setCenter(webCamPane);

        int webCamCounter = 0;
        Label lbInfoLabel = new Label("Select Your WebCam Camera");
        ObservableList<WebCamInfo> options = FXCollections.observableArrayList();

        topPane.getChildren().add(lbInfoLabel);

        for (Webcam webcam : Webcam.getWebcams()) {
            WebCamInfo webCamInfo = new WebCamInfo();
            webCamInfo.setWebCamIndex(webCamCounter);
            webCamInfo.setWebCamName(webcam.getName());
            options.add(webCamInfo);
            webCamCounter++;
        }

        ComboBox<WebCamInfo> cameraOptions = new ComboBox<>();
        cameraOptions.setItems(options);
        String cameraListPromptText = "Choose Camera";
        cameraOptions.setPromptText(cameraListPromptText);
        cameraOptions.getSelectionModel().selectedItemProperty().addListener((observableValue, webCamInfo, camInfo) -> {
            if (camInfo != null) {
                System.out.println("WebCam Index: " + camInfo.getWebCamIndex() + ": WebCam Name:" + camInfo.getWebCamName());
                if (webCam != null) disposeWebCamCamera();
                webCam = Webcam.getWebcams().get(camInfo.getWebCamIndex());
                webCam.setCustomViewSizes(WebcamResolution.VGA.getSize(), WebcamResolution.HD.getSize());
                webCam.open();
                startWebCamStream();
                bottomCameraControlPane.setDisable(false);
                btnCameraStart.setDisable(true);
            }
        });
        topPane.getChildren().add(cameraOptions);

        bottomCameraControlPane = new FlowPane();
        bottomCameraControlPane.setOrientation(Orientation.HORIZONTAL);
        bottomCameraControlPane.setAlignment(Pos.CENTER);
        bottomCameraControlPane.setHgap(20);
        bottomCameraControlPane.setVgap(10);
        bottomCameraControlPane.setPrefHeight(40);
        bottomCameraControlPane.setDisable(true);

        btnCameraStop = new Button();
        btnCameraStop.setOnAction(arg0 -> stopWebCamCamera());
        btnCameraStop.setText("Stop Camera");
        btnCameraStart = new Button();
        btnCameraStart.setOnAction(arg0 -> startWebCamCamera());
        btnCameraStart.setText("Start Camera");

        Button btnCameraDispose = new Button();
        btnCameraDispose.setText("Dispose Camera");
        btnCameraDispose.setOnAction(arg0 -> disposeWebCamCamera());
        bottomCameraControlPane.getChildren().add(btnCameraStart);
        bottomCameraControlPane.getChildren().add(btnCameraStop);
        bottomCameraControlPane.getChildren().add(btnCameraDispose);

        root.setBottom(bottomCameraControlPane);

        stage.setScene(new Scene(root));
        stage.setHeight(800);
        stage.setWidth(1000);
        stage.centerOnScreen();
        stage.show();

        stage.setOnCloseRequest(event -> disposeWebCamCamera());

        Platform.runLater(this::setImageViewSize);
    }

    private void disposeWebCamCamera() {
        stopCamera = true;
        webCam.close();
        btnCameraStart.setDisable(true);
        btnCameraStop.setDisable(true);
    }

    private void startWebCamCamera() {
        stopCamera = false;
        startWebCamStream();
        btnCameraStop.setDisable(false);
        btnCameraStart.setDisable(true);
    }

    private void stopWebCamCamera() {
        stopCamera = true;
        btnCameraStart.setDisable(false);
        btnCameraStop.setDisable(true);
    }

    private void setImageViewSize() {

        double height = webCamPane.getHeight();
        double width = webCamPane.getWidth();

        imgWebCamCapturedImage.setFitHeight(height);
        imgWebCamCapturedImage.setFitWidth(width);
        imgWebCamCapturedImage.prefHeight(width);
        imgWebCamCapturedImage.prefWidth(height);
        imgWebCamCapturedImage.setPreserveRatio(true);

    }

    private void startWebCamStream() {

        stopCamera = false;

        Runnable task = () -> {
            final AtomicReference<WritableImage> ref = new AtomicReference<>();
            BufferedImage img;

            while (!stopCamera) {
                try {
                    if ((img = webCam.getImage()) != null) {//fixme

                        try {
                            Result result = new MultiFormatReader().decode(new BinaryBitmap(
                                    new HybridBinarizer(new BufferedImageLuminanceSource(img))));
                            Graphics graphics = img.getGraphics();
                            graphics.setColor(Color.BLUE);
                            ResultPoint[] resultPoints = result.getResultPoints();
                            double px = resultPoints[0].getX();
                            double py = resultPoints[0].getY();
                            double ox = px;
                            double oy = py;
                            for (int i = 1; i < resultPoints.length; i++) {
                                double nx = resultPoints[i].getX();
                                double ny = resultPoints[i].getY();
                                graphics.drawLine((int) px, (int) py, (int) nx, (int) ny);
                                px = nx;
                                py = ny;
                            }
                            graphics.drawLine((int) px, (int) py, (int) ox, (int) oy);
                        } catch (NotFoundException ignored) {
                        }
                        ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                        img.flush();
                        Platform.runLater(() -> imageProperty.set(ref.get()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);
    }

    static class WebCamInfo {

        private String webCamName;
        private int webCamIndex;

        String getWebCamName() {
            return webCamName;
        }

        void setWebCamName(String webCamName) {
            this.webCamName = webCamName;
        }

        int getWebCamIndex() {
            return webCamIndex;
        }

        void setWebCamIndex(int webCamIndex) {
            this.webCamIndex = webCamIndex;
        }

        @Override
        public String toString() {
            return webCamName;
        }
    }
}