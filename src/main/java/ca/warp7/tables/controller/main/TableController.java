package ca.warp7.tables.controller.main;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.util.Comparator;

public class TableController {

    @FXML
    TableView<Person> tableView;

    @FXML
    void initialize() {

        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));

        final Label actionTaken = new Label();


        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameCol.setCellFactory(param -> {
            TableCell<Person, String> cell = TextFieldTableCell.<Person>forTableColumn().call(param);
            cell.setEditable(true);
            return cell;
        });

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        lastNameCol.setCellFactory(new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
            @Override
            public TableCell<Person, String> call(TableColumn<Person, String> param) {
                return new TableCell<Person, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        setText(item);
                        if (!empty) {
                            String c = Integer.toHexString((int) (Math.random() * 255));
                            setStyle(String.format("-fx-background-color: #ff%s%s", c, c));
                        }
                    }
                };
            }
        });

        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(param -> new CheckBoxTableCell<Person, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
                setAlignment(Pos.CENTER_LEFT);
            }
        });

        TableColumn<Person, Person> btnCol = new TableColumn<>("Gifts");
        btnCol.setMinWidth(150);
        btnCol.setCellValueFactory(features -> new SimpleObjectProperty<>(features.getValue()));
        btnCol.setComparator(Comparator.comparing(Person::getLikes));

        tableView.setItems(FXCollections.observableArrayList(
                new Person("Jacob", "Smith", "jacob.smith@example.com", "The Button"),
                new Person("Isabella", "Johnson", "isabella.johnson@example.com", "The Button"),
                new Person("Ethan", "Williams", "ethan.williams@example.com", "The Button"),
                new Person("Emma", "Jones", "emma.jones@example.com", "The Button"),
                new Person("Michael", "Brown", "michael.brown@example.com", "The Button")

        ));
        tableView.setEditable(true);

        tableView.getSelectionModel().setCellSelectionEnabled(true);

        tableView.getColumns().add(firstNameCol);
        tableView.getColumns().add(lastNameCol);
        tableView.getColumns().add(emailCol);

        tableView.setOnKeyPressed(event -> tableView.getSortOrder().clear());

        btnCol.setCellFactory(new Callback<TableColumn<Person, Person>, TableCell<Person, Person>>() {

            @Override
            public TableCell<Person, Person> call(TableColumn<Person, Person> btnCol) {

                return new TableCell<Person, Person>() {

                    final ImageView buttonGraphic = new ImageView();
                    final Button button = new Button();

                    {
                        button.setGraphic(buttonGraphic);
                        button.setMinWidth(130);
                    }

                    @Override
                    public void updateItem(final Person person, boolean empty) {

                        super.updateItem(person, empty);
                        if (person != null) {
                            if ("fruit".equals(person.getLikes().toLowerCase())) {
                                button.setText("Buy fruit");
                                buttonGraphic.setImage(fruitImage);
                            } else {
                                button.setText("Buy coffee");
                                buttonGraphic.setImage(coffeeImage);
                            }

                            setGraphic(button);
                            button.setOnAction(event -> actionTaken.setText("Bought " + person.getLikes().toLowerCase() + " for: " + person.getFirstName() + " " + person.getLastName()));
                        } else {
                            setGraphic(null);
                        }
                    }
                };

            }
        });
    }

    @SuppressWarnings("ALL")
    public static class Person {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty email;
        private final SimpleStringProperty likes;

        private Person(String fName, String lName, String email, String likes) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.email = new SimpleStringProperty(email);
            this.likes = new SimpleStringProperty(likes);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getEmail() {
            return email.get();
        }

        public void setEmail(String fName) {
            email.set(fName);
        }

        public String getLikes() {
            return likes.get();
        }

        public void setLikes(String likes) {
            this.likes.set(likes);
        }
    }

    // icons for non-commercial use with attribution from: http://www.iconarchive.com/show/veggies-icons-by-iconicon/bananas-icon.html and http://www.iconarchive.com/show/collection-icons-by-archigraphs.html
    private final Image coffeeImage = new Image(
            "http://icons.iconarchive.com/icons/archigraphs/collection/48/Coffee-icon.png"
    );
    private final Image fruitImage = new Image(
            "http://icons.iconarchive.com/icons/iconicon/veggies/48/bananas-icon.png"
    );
}
