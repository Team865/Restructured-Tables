package ca.warp7.tables.controller;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import org.almibe.codeeditor.CodeMirrorEditor;

public class EditorController {
    @FXML
    BorderPane codeRoot;
    @FXML
    Button themeChange;

    private boolean darkTheme;
    private StringProperty themeChanger;
    private CodeMirrorEditor codeMirrorEditor = new CodeMirrorEditor();

    @FXML
    void initialize() {
        darkTheme = false;
        themeChanger = themeChange.textProperty();
        themeChanger.set("Monokai");
        themeChange.setOnAction(event -> {
            if (codeMirrorEditor.isEditorInitialized()) {
                darkTheme = !darkTheme;
                themeChanger.set(darkTheme ? "Switch to IntelliJ" : "Switch to Monokai");
                codeMirrorEditor.setTheme(darkTheme ? "monokai" : "idea");
            }
        });
        codeRoot.setCenter(codeMirrorEditor.getWidget());
        codeMirrorEditor.init(
                () -> {
                    codeMirrorEditor.setMode("python");
                    codeMirrorEditor.setTheme("idea");
                    String builder =
                            "from tables import table\n" +
                                    "\n\n@table(\"entry\", level=1)\n" +
                                    "def some_table_name(src, entry):\n" +
                                    "\treturn {\n" +
                                    "\t\t\"column_name\": \"column_data\"\n" +
                                    "\t}\n";
                    codeMirrorEditor.setContent(builder, true);
                }
        );
    }
}
