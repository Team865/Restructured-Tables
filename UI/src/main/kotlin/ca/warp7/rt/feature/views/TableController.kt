package ca.warp7.rt.feature.views

import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class TableController {

    lateinit var tableContainer: VBox
    private val tableView = TableView<Person>()

    // icons for non-commercial use with attribution from: http://www.iconarchive.com/show/veggies-icons-by-iconicon/bananas-icon.html and http://www.iconarchive.com/show/collection-icons-by-archigraphs.html
    private val coffeeImage = Image(
            "http://icons.iconarchive.com/icons/archigraphs/collection/48/Coffee-icon.png"
    )
    private val fruitImage = Image(
            "http://icons.iconarchive.com/icons/iconicon/veggies/48/bananas-icon.png"
    )

    fun initialize() {

        VBox.setVgrow(tableView, Priority.ALWAYS)

        tableContainer.children.add(tableView)

        val label = Label("Address Book")
        label.font = Font("Arial", 20.0)

        val actionTaken = Label()


        val firstNameCol = TableColumn<Person, String>("First Name")
        firstNameCol.minWidth = 100.0
        firstNameCol.cellValueFactory = PropertyValueFactory("firstName")
        firstNameCol.setCellFactory { param ->
            val cell = TextFieldTableCell.forTableColumn<Person>().call(param)
            cell.isEditable = true
            cell
        }

        val lastNameCol = TableColumn<Person, String>("Last Name")
        lastNameCol.minWidth = 100.0
        lastNameCol.cellValueFactory = PropertyValueFactory("lastName")

        lastNameCol.setCellFactory {
            object : TableCell<Person, String>() {
                override fun updateItem(item: String?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = item
                    if (!empty) {
                        val c = Integer.toHexString((Math.random() * 255).toInt())
                        style = String.format("-fx-background-color: #ff%s%s", c, c)
                    }
                }
            }
        }

        val emailCol = TableColumn<Person, String>("Email")
        emailCol.minWidth = 200.0
        emailCol.cellValueFactory = PropertyValueFactory("email")

        val btnCol = TableColumn<Person, Person>("Gifts")
        btnCol.minWidth = 150.0
        btnCol.setCellValueFactory { features -> SimpleObjectProperty(features.value) }

        tableView.items = FXCollections.observableArrayList(
                Person("Jacob", "Smith", "jacob.smith@example.com", "The Button"),
                Person("Isabella", "Johnson", "isabella.johnson@example.com", "The Button"),
                Person("Ethan", "Williams", "ethan.williams@example.com", "The Button"),
                Person("Emma", "Jones", "emma.jones@example.com", "The Button"),
                Person("Michael", "Brown", "michael.brown@example.com", "The Button")

        )
        tableView.isEditable = true

        tableView.selectionModel.isCellSelectionEnabled = true

        tableView.columns.add(firstNameCol)
        tableView.columns.add(lastNameCol)
        tableView.columns.add(emailCol)

        tableView.setOnKeyPressed { tableView.sortOrder.clear() }

        btnCol.setCellFactory {
            object : TableCell<Person, Person>() {

                val buttonGraphic = ImageView()
                val button = Button()

                init {
                    button.graphic = buttonGraphic
                    button.minWidth = 130.0
                }

                public override fun updateItem(person: Person?, empty: Boolean) {
                    super.updateItem(person, empty)
                    if (person != null) {
                        if ("fruit" == person.getLikes().toLowerCase()) {
                            button.text = "Buy fruit"
                            buttonGraphic.image = fruitImage
                        } else {
                            button.text = "Buy coffee"
                            buttonGraphic.image = coffeeImage
                        }
                        graphic = button
                        button.setOnAction { actionTaken.text = "Bought " + person.getLikes().toLowerCase() + " for: " + person.getFirstName() + " " + person.getLastName() }
                    } else {
                        graphic = null
                    }
                }
            }
        }
    }
}
