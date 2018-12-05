package ca.warp7.rt.java.python;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.almibe.codeeditor.CodeMirrorEditor;

public class PythonScriptsController {
    @FXML
    BorderPane codeRoot;
    @FXML
    Button themeChange;

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
        codeMirrorEditor.init(
                () -> {
                    codeMirrorEditor.setMode("python");
                    codeMirrorEditor.setTheme("monokai");
                    String builder = "from tables import table" +
                            "\nimport numpy as np" +
                            "\nimport pandas as pd" +
                            "\n\n\n" +
                            "@table(\"entry\", level=1)\n" +
                            "def table_name(data, entry):\n" +
                            "    return {\n" +
                            "        \"column_name\": \"column_data\"\n" +
                            "    }\n";
                    codeMirrorEditor.setContent(builder, true);
                }
        );
    }
}
