package ca.warp7.rt.core.app

import ca.warp7.rt.core.env.UserEnv
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.scene.control.*
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.stage.Stage
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.kordamp.ikonli.javafx.FontIcon


class AppController : FeatureStage {

    lateinit var appWindowBoarderPane: BorderPane
    lateinit var tabContentBorderPane: BorderPane
    lateinit var tabContentLayover: BorderPane
    lateinit var customSidebarBorderPane: BorderPane
    lateinit var appTabListView: ListView<FeatureWrapper>
    lateinit var listViewSplitPane: SplitPane
    lateinit var statusMessageLabel: Label
    lateinit var focusIcon: FontIcon
    lateinit var statusBarContainer: HBox
    lateinit var appStage: Stage

    private lateinit var searchController: SearchController
    private val appTabs = FXCollections.observableArrayList<FeatureWrapper>()
    private val focusedMode = SimpleBooleanProperty()
    private var current: FeatureWrapper? = null
    private var searchPaneShown = false
    private val searchPane = loadParent<SearchController>("/ca/warp7/rt/core/app/SearchPane.fxml")
    { searchController = it }

    fun initialize() {
        utilsController = this
        Platform.runLater {
            setupAppTabListView()
            setupFocusedMode()
            appFeatures.forEach { appTabs.add(FeatureWrapper(it)) }
            statusBarContainer.isVisible = true
            appTabListView.selectionModel.select(0)
            handleFeatureLink(appTabs[0])
            statusMessageLabel.text = "Finished loading app"
            val totalHeight = (appTabs.size * 36).toDouble()
            appTabListView.minHeight = totalHeight
            appTabListView.maxHeight = totalHeight
            toggleSearch()
        }
    }

    fun toggleFullScreen() {
        appStage.isFullScreen = !appStage.isFullScreen
    }

    fun toggleFocused() {
        focusedMode.value = !focusedMode.get()
    }

    fun showSettings() {
        getUserSettings()
    }

    override fun setStage(stage: Stage) {
        stage.scene.apply {
            setOnKeyPressed {
                when {
                    it.code == KeyCode.F11 -> stage.isFullScreen = true
                    it.code == KeyCode.F9 -> toggleFocused()
                }
            }
        }

        stage.scene.accelerators[KeyCodeCombination(KeyCode.BACK_QUOTE, KeyCodeCombination.SHORTCUT_DOWN)] =
                Runnable { toggleSearch() }
        stage.setOnCloseRequest { event ->
            UserEnv.save()
            if (current != null && !current!!.onClose()) {
                event.consume()
            }
        }
        appStage = stage
        appStage.minWidth = 800.0
        appStage.minHeight = 450.0
        appStage.isMaximized = true
    }

    fun toggleSearch() {
        searchPaneShown = !searchPaneShown
        tabContentLayover.right = if (searchPaneShown) searchPane else null
        if (searchPaneShown) {
            searchPane.requestFocus()
            searchController.focus()
        }
    }

    fun showStatus() {
        val mem = String.format("Memory: %.3f MB", (Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory()) / 1000000.0)
        val msg = statusMessageLabel.text + "\n\n" + mem

        val dialog = Dialog<String>()
        dialog.title = "App Status"
        dialog.initOwner(appStage)
        dialog.contentText = msg
        dialog.dialogPane.buttonTypes.addAll(ButtonType.OK)
        dialog.showAndWait()
    }

    fun closeCurrentTab() {
        if (current == null) return
        if (current!!.onClose()) {
            current = null
            appStage.title = "Restructured Tables "
            tabContentBorderPane.center = null
            customSidebarBorderPane.center = null
        }
    }

    fun doGarbageCollection() {
        Thread {
            val before = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
            System.gc()
            Platform.runLater {
                val now = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())
                val mem = String.format("Memory: %.3f MB", now / 1000000.0)
                val freedMemory = String.format("Freed: %.3f MB", (before - now) / 1000000.0)
                val msg = "Garbage Collection Done\n\n$mem\n$freedMemory"
                val dialog = Dialog<String>()
                dialog.title = "App Status"
                dialog.initOwner(appStage)
                dialog.contentText = msg
                dialog.dialogPane.buttonTypes.addAll(ButtonType.OK)
                dialog.showAndWait()
            }
        }.start()
    }

    private fun setupAppTabListView() {
        appTabListView.setCellFactory {
            object : ListCell<FeatureWrapper>() {
                override fun updateItem(item: FeatureWrapper?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty || item == null) {
                        graphic = null
                        return
                    }
                    graphic = tabUIFromLink(item)
                    prefHeight = 36.0
                    setOnMouseClicked { handleFeatureLink(item) }
                }
            }
        }
        appTabListView.setOnKeyPressed {
            val selectedItems = appTabListView.selectionModel.selectedItems
            if (selectedItems.size == 1 && it.code == KeyCode.ENTER) handleFeatureLink(selectedItems[0])
        }
        appTabListView.selectionModel.selectionMode = SelectionMode.SINGLE
        appTabListView.items = appTabs
    }

    private fun handleFeatureLink(ft: FeatureWrapper) {
        if (ft === current) return
        if (current == null || current!!.onClose()) {
            tabContentBorderPane.center = null
            customSidebarBorderPane.center = null
            current = ft
            val parent = current!!.onOpen()
            val title = ft.link.title
            appStage.title = title
            customSidebarBorderPane.center = parent.first
            tabContentBorderPane.center = parent.second
        }
    }

    private fun setupFocusedMode() {
        focusedMode.addListener { _, _, focused ->
            if (focused!!) {
                appWindowBoarderPane.left = null
                focusIcon.iconCode = FontAwesomeSolid.EYE
            } else {
                appWindowBoarderPane.left = listViewSplitPane
                focusIcon.iconCode = FontAwesomeSolid.EYE_SLASH
            }
            if (current != null) current!!.setFocused(focused)
        }
    }
}