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
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

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
        try{
            Double value = parseDouble(txtNewItemValue.getText());
            Item newItem = new Item(name, serialNumber, value);
            appModel.getInventory().addItem(newItem);
        } catch(Exception e){
            e.printStackTrace();
        }

        if (!appModel.getInventory().item.isEmpty()) {
            tableView.setItems(getListForTable(appModel.getInventory()));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableView.setEditable(true);
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setCellFactory(TextFieldTableCell.forTableColumn());
        colName.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue()));
        colSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        colSerialNumber.setCellFactory(TextFieldTableCell.forTableColumn());
        colSerialNumber.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setSerialNumber(event.getNewValue()));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        colValue.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        colValue.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow()).setValue(event.getNewValue()));

        if (!appModel.getInventory().item.isEmpty()) {
            tableView.setItems(getListForTable(appModel.getInventory()));
        }
    }

    public ObservableList<Item> getListForTable(Inventory inv) {
        ObservableList<Item> item = FXCollections.observableArrayList();
        if (!inv.getInventory().isEmpty()){
            item.addAll(inv.getInventory());
        }
        return item;
    }

}
