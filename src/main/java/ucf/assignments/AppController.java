/*
 *
 *  *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  *  Copyright 2021 rielly donnell
 *
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class AppController implements Initializable {

    static AppModel appModel = new AppModel();

    FileChooser fileChooser = new FileChooser();

    @FXML
    private TableView<Item> tableView;
    
    @FXML
    private TableColumn<Item, String> colName = new TableColumn<>();

    @FXML
    private TableColumn<Item, String> colSerialNumber = new TableColumn<>();

    @FXML
    private TableColumn<Item, Double> colValue = new TableColumn<>();

    @FXML
    private Label lblAddInventoryItem;

    @FXML
    private TextField txtNewItemName;

    @FXML
    private TextField txtNewItemSerialNumber;

    @FXML
    private TextField txtNewItemValue;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnSave;

    @FXML
    private TextField txtSearch;

    @FXML
    private Button btnSearch;

    @FXML
    public void btnAddClicked() {
        String name = txtNewItemName.getText();
        String serialNumber = txtNewItemSerialNumber.getText();
        String value = txtNewItemValue.getText();
        if (validateName(name) && validateSerialNumber(serialNumber) && validateValue(value)) {
            Double dblValue = parseDouble(value);
            Item newItem = new Item(name, serialNumber.toUpperCase(), dblValue);
            appModel.getInventory().addItem(newItem);
            txtNewItemName.clear();
            txtNewItemSerialNumber.clear();
            txtNewItemValue.clear();
        }

        loadList();

    }

    @FXML
    public void btnSearchClicked() {
        String search = txtSearch.getText().trim();
        tableView.setItems(getListForTable(appModel.getInventory(), search));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tableView.setEditable(true);
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(event -> {if (validateName(event.getNewValue())) {event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());}});

        colSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        colSerialNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        colSerialNumber.setOnEditCommit(event ->{if (validateSerialNumber(event.getNewValue())) {event.getTableView().getItems().get(event.getTablePosition().getRow()).setSerialNumber(event.getNewValue());}});

        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        /*colValue.setCellFactory(tc -> new TableCell<Item, Double>() {
            NumberFormat currency = NumberFormat.getCurrencyInstance();
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currency.format(value));
                }
            }
        });*/
        colValue.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colValue.setOnEditCommit(event -> {if (validateValue(event.getNewValue().toString())) {event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(event.getNewValue());}});

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        loadList();
    }

    public void loadList() {
        if (!appModel.getInventory().item.isEmpty()) {
            tableView.setItems(getListForTable(appModel.getInventory(),null));
        }
        txtSearch.clear();
    }

    public static ObservableList<Item> getListForTable(Inventory inv, String search) {
        ObservableList<Item> item = FXCollections.observableArrayList();
        if (search != null) {
            if (search.isEmpty() || search.isBlank()) {
                search = null;
            }
        }
        if (!inv.getInventoryItems().isEmpty()) {
            if (search != null) {
                for (Item i :
                        inv.getInventoryItems()) {
                    if (i.getName().toLowerCase().contains(search.toLowerCase()) ||
                            i.getSerialNumber().equalsIgnoreCase(search)) {
                        item.add(i);
                    }
                }
            } else {
                item.addAll(inv.getInventoryItems());
            }
        }
        return item;
    }

    public static boolean validateName(String name) {
        return name.length() >= 2 && name.length() <= 256;
    }

    public static boolean validateSerialNumber(String sn) {
        boolean found = false;
        for (Item i :
                appModel.getInventory().getInventoryItems()) {
            if (i.getSerialNumber().equalsIgnoreCase(sn)) {
                found = true;
                duplicateSerialAlert();
            }
        }
        return Pattern.matches("[a-zA-Z0-9]{10}", sn) && !found;
    }

    public static void duplicateSerialAlert() {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR: Duplicate Serial Number");
        alert.setHeaderText("ERROR: Duplicate Serial Number");
        alert.setContentText("The serial number that was entered already exists in the inventory. Each item in the inventory should have its own serial number. Please enter a unique serial number for this item.");
        alert.show();
    }

    public static boolean validateValue(String value) {
        try {
            Double dbl = parseDouble(value);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void deleteRow(KeyEvent keyEvent) {
        if(keyEvent.getCode().toString().equalsIgnoreCase("DELETE")) {
            appModel.getInventory().removeItemBySN(tableView.getSelectionModel().getSelectedItem().getSerialNumber());
        }
        loadList();
    }

    public void btnLoadClicked(ActionEvent actionEvent) {
        Window stage = btnLoad.getScene().getWindow();
        fileChooser.setTitle("Load");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TSV", "*.txt"),
                new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(stage);
        appModel.loadFile(file);
        tableView.setItems(getListForTable(appModel.getInventory(), null));
    }

    public void btnSaveClicked(ActionEvent actionEvent){
        Window stage = btnSave.getScene().getWindow();
        fileChooser.setTitle("Save");
        fileChooser.setInitialFileName("MyInventory");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TSV", "*.txt"),
                new FileChooser.ExtensionFilter("HTML", "*.html"),
                new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showSaveDialog(stage);
        fileChooser.setInitialDirectory(file.getParentFile());
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileType = file.getName().substring(file.getName().length() - 4);
        String fileContent;
        if (fileType.equalsIgnoreCase(".txt")) {
            fileContent = appModel.getTSV();
        } else if (fileType.equalsIgnoreCase("html")) {
            fileContent = appModel.getHTML();
        } else if (fileType.equalsIgnoreCase("json")) {
            fileContent = appModel.getJSON();
        } else {
            fileContent = "No File Type Found";
        }

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(fileContent);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
