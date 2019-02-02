package ca.warp7.rt.ext.python

import javafx.scene.control.Button
import javafx.scene.control.ToolBar
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import org.almibe.codeeditor.CodeMirrorEditor
import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.StringWriter
import java.nio.charset.Charset

class PythonController {
    lateinit var codeRoot: BorderPane
    lateinit var themeChange: Button
    lateinit var container: VBox
    lateinit var toolbar: ToolBar
    lateinit var feature: PythonFeature

    private val template: String
    private var themeIndex: Int = 0
    private val codeMirrorEditor = CodeMirrorEditor()
    private val themes = arrayOf("idea", "darcula", "monokai", "material")

    init {
        val inputStream = javaClass.getResourceAsStream("/ca/warp7/rt/res/docs.py")
        val writer = StringWriter()
        try {
            IOUtils.copy(inputStream, writer, null as Charset?)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        template = writer.toString()
    }

    fun initialize() {
        themeChange.setOnAction {
            if (codeMirrorEditor.isEditorInitialized) {
                themeIndex = (themeIndex + 1) % themes.size
                codeMirrorEditor.setTheme(themes[themeIndex])
            }
        }
        codeRoot.center = codeMirrorEditor.widget
    }

    fun setTabItemParams(itemParams: String) {
        val t = String.format(template, itemParams)
        if (codeMirrorEditor.isEditorInitialized) codeMirrorEditor.setContent(t, true)
        else codeMirrorEditor.init(Runnable {
            codeMirrorEditor.mode = "python"
            themeIndex = 0
            codeMirrorEditor.setTheme(themes[0])
            codeMirrorEditor.setContent(t, true)
        })
    }

    fun setFocused(focused: Boolean) {
        if (focused) container.children.remove(toolbar)
        else container.children.add(0, toolbar)
    }
}
