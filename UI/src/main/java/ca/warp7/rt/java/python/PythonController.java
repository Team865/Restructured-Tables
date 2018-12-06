package ca.warp7.rt.java.python;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.almibe.codeeditor.CodeMirrorEditor;

public class PythonController {
    @FXML
    BorderPane codeRoot;
    @FXML
    Button themeChange;

    private static final String template = "from tables import table" +
            "\nimport numpy as np" +
            "\nimport pandas as pd" +
            "\n\n#%s\n\n" +
            "@table(\"entry\", level=1)\n" +
            "def table_name(data, entry):\n" +
            "    return {\n" +
            "        \"column_name\": \"column_data\"\n" +
            "    }\n";

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
