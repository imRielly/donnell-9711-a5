/*
 *
 *  *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  *  Copyright 2021 rielly donnell
 *
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;

public class AppController implements Initializable {

    AppModel appModel = new AppModel();

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
    private Button btnSaveTSV;

    @FXML
    private Button btnSaveHTML;

    @FXML
    private Button btnSaveJSON;

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
        colSerialNumber.setOnEditCommit(event -> {if (validateSerialNumber(event.getNewValue())) {event.getTableView().getItems().get(event.getTablePosition().getRow()).setSerialNumber(event.getNewValue());}});

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

        loadList();
    }

    public void loadList() {
        if (!appModel.getInventory().item.isEmpty()) {
            tableView.setItems(getListForTable(appModel.getInventory(),null));
        }
    }

    public ObservableList<Item> getListForTable(Inventory inv, String search) {
        ObservableList<Item> item = FXCollections.observableArrayList();
        if (search != null) {
            if (search.isEmpty() || search.isBlank()) {
                search = null;
            }
        }
        if (!inv.getInventory().isEmpty()) {
            if (search != null) {
                for (Item i :
                        inv.getInventory()) {
                    if (i.getName().toLowerCase().contains(search.toLowerCase()) || i.getSerialNumber().equalsIgnoreCase(search)) {
                        item.add(i);
                    }
                }
            } else {
                item.addAll(inv.getInventory());
            }
        }
        return item;
    }

    public boolean validateName(String name) {
        return name.length() >= 2 && name.length() <= 256;
    }

    public boolean validateSerialNumber(String sn) {
        boolean found = false;
        for (Item i :
                appModel.getInventory().getInventory()) {
            if (i.getSerialNumber().equalsIgnoreCase(sn)) {
                found = true;
            }
        }
        return Pattern.matches("[a-zA-Z0-9]{10}", sn) && !found;
    }

    public boolean validateValue(String value) {
        try {
            Double dbl = parseDouble(value);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public void deleteRow(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode().toString());
        if(keyEvent.getCode().toString().equalsIgnoreCase("DELETE")) {
            appModel.getInventory().removeItemBySN(tableView.getSelectionModel().getSelectedItem().getSerialNumber());
        }
        loadList();
    }
}
