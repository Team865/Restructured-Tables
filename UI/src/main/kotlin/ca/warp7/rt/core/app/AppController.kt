package ca.warp7.rt.core.app

import ca.warp7.rt.core.env.EnvUser
import ca.warp7.rt.core.feature.Feature
import ca.warp7.rt.core.feature.FeatureStage
import javafx.application.Platform
import javafx.beans.property.SimpleBooleanProperty
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.scene.paint.Color
import javafx.stage.Stage
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid
import org.kordamp.ikonli.javafx.FontIcon

class AppController : FeatureStage {

    lateinit var tabContent: BorderPane
    lateinit var tabsAndContentContainer: HBox
    lateinit var appTabListView: ListView<Feature>
    lateinit var listViewContainer: SplitPane
    lateinit var statusMessageLabel: Label
    lateinit var rowLabel: Label
    lateinit var columnLabel: Label
    lateinit var focusIcon: FontIcon
    lateinit var userName: Label
    lateinit var deviceName: Label
    lateinit var statusBarContainer: HBox
    lateinit var appStage: Stage
    private val appTabs = FXCollections.observableArrayList<Feature>()
    private val focusedMode = SimpleBooleanProperty()
    private var current: Feature? = null

    fun initialize() {
        Platform.runLater {
            utilsController = this
            setupAppTabListView()
            setupFocusedMode()
            appTabs.addAll(appFeatures)
            rowLabel.text = "None"
            columnLabel.text = "None"
            statusBarContainer.isVisible = true
            tabsAndContentContainer.isVisible = true
            Platform.runLater {
                AppElement.updateUserAndDevice(userName, deviceName)
                statusMessageLabel.text = "Finished loading app"
                val totalHeight = (appTabs.size * 36).toDouble()
                appTabListView.minHeight = totalHeight
                appTabListView.setMaxHeight(totalHeight)
            }
        }
    }

    fun toggleFullScreen() {
        appStage.isFullScreen = !appStage.isFullScreen
    }

    fun toggleFocused() {
        focusedMode.value = !focusedMode.get()
    }

    fun setUserName() {
        userName.text = AppElement.getUserExplicitName()
    }

    fun setUserDevice() {
        deviceName.text = AppElement.getUserExplicitDevice()
    }

    override fun setStage(stage: Stage) {
        stage.scene.setOnKeyPressed { event ->
            if (event.code == KeyCode.F11)
                stage.isFullScreen = true
            else if (event.code == KeyCode.F9) toggleFocused()
        }
        stage.setOnCloseRequest { event ->
            EnvUser.save()
            if (current != null && !current!!.onClose()) {
                event.consume()
            }
        }
        appStage = stage
        appStage.minWidth = 800.0
        appStage.minHeight = 450.0
        appStage.isMaximized = true
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
    }

    private fun setupAppTabListView() {
        appTabListView.setCellFactory {
            object : ListCell<Feature>() {
                override fun updateItem(item: Feature?, empty: Boolean) {
                    super.updateItem(item, empty)
                    if (empty || item == null) {
                        graphic = null
                        return
                    }
                    graphic = AppElement.tabUIFromLink(item.link)
                    prefHeight = 36.0
                    setOnMouseClicked { handleFeatureLink(item) }
                }
            }
        }
        appTabListView.setOnKeyPressed {
            val selectedItems = appTabListView.selectionModel.selectedItems
            if (selectedItems.size == 1 && it.code == KeyCode.ENTER) handleFeatureLink(selectedItems[0])
        }
        appTabListView.selectionModel.selectedItems.addListener(ListChangeListener<Feature>
        { c ->
            while (c.next()) {
                c.removed.forEach { it.link.icon.iconColor = AppElement.teamColor }
                c.addedSubList.forEach { it.link.icon.iconColor = Color.WHITE }
            }
        })
        appTabListView.focusedProperty().addListener { _, _, newValue ->
            appTabListView.selectionModel.selectedItems.forEach {
                it.link.icon.iconColor = if (newValue) Color.WHITE else AppElement.teamColor
            }

        }
        appTabListView.selectionModel.selectionMode = SelectionMode.SINGLE
        appTabListView.items = appTabs
    }

    private fun handleFeatureLink(ft: Feature) {
        if (ft === current) return
        if (current == null || current!!.onClose()) {
            val title = ft.link.title
            //AppUtils.setStatusMessage("Opened tab '$title'")
            appStage.title = title
            current = ft
            val parent = current!!.onOpen()
            tabContent.center = parent.second
            rowLabel.text = "None"
            columnLabel.text = "None"
        }
    }

    private fun setupFocusedMode() {
        focusedMode.addListener { _, _, focused ->
            if (focused!!) {
                tabsAndContentContainer.children.removeAt(0)
                focusIcon.iconCode = FontAwesomeSolid.EYE
            } else {
                tabsAndContentContainer.children.add(0, listViewContainer)
                focusIcon.iconCode = FontAwesomeSolid.EYE_SLASH
            }
            if (current != null) current!!.setFocused(focused)
        }
    }
}