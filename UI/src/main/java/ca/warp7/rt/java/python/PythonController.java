package ca.warp7.rt.java.python;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.almibe.codeeditor.CodeMirrorEditor;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

public class PythonController {
    @FXML
    BorderPane codeRoot;
    @FXML
    Button themeChange;
    @FXML
    VBox container;
    @FXML
    ToolBar toolbar;

    PythonFeature feature;

    private String template;

    public PythonController() {
        InputStream inputStream = getClass().getResourceAsStream("/ca/warp7/rt/res/docs.py");
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, (Charset) null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        template = writer.toString();
    }

    private int themeIndex;
    private static final String[] themes = new String[]{
            "idea",
            "darcula",
            "monokai",
            "material"
    };
    private CodeMirrorEditor codeMirrorEditor = new CodeMirrorEditor();

    public void initialize() {
        themeChange.setOnAction(event -> {
            if (codeMirrorEditor.isEditorInitialized()) {
                themeIndex = (themeIndex + 1) % themes.length;
                codeMirrorEditor.setTheme(themes[themeIndex]);
            }
        });
        codeRoot.setCenter(codeMirrorEditor.getWidget());
    }

    void setTabItemParams(String itemParams) {
        String t = String.format(template, itemParams);
        if (codeMirrorEditor.isEditorInitialized()) codeMirrorEditor.setContent(t, true);
        else codeMirrorEditor.init(
                () -> {
                    codeMirrorEditor.setMode("python");
                    themeIndex = 0;
                    codeMirrorEditor.setTheme(themes[0]);
                    codeMirrorEditor.setContent(t, true);
                }
        );
    }

    public void newScript() {
        feature.newScript();
    }

    void setFocused(boolean focused) {
        if (focused) container.getChildren().remove(toolbar);
        else container.getChildren().add(0, toolbar);
    }
}
