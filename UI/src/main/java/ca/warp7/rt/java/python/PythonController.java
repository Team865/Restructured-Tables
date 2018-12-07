package ca.warp7.rt.java.python;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
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

    private boolean darkTheme;
    private CodeMirrorEditor codeMirrorEditor = new CodeMirrorEditor();

    @FXML
    void initialize() {
        darkTheme = true;
        themeChange.setOnAction(event -> {
            if (codeMirrorEditor.isEditorInitialized()) {
                darkTheme = !darkTheme;
                codeMirrorEditor.setTheme(darkTheme ? "monokai" : "idea");
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
                    codeMirrorEditor.setTheme("monokai");
                    codeMirrorEditor.setContent(t, true);
                }
        );
    }
}
