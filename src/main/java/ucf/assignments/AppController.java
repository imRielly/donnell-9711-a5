/*
 *
 *  *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  *  Copyright 2021 rielly donnell
 *
 */

package ucf.assignments;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    AppModel appModel = new AppModel();

    @FXML
    private TableView<Item> tableView;
    
    @FXML
    private TableColumn<Item, SimpleStringProperty> colName;

    @FXML
    private TableColumn<Item, SimpleStringProperty> colSerialNumber;

    @FXML
    private TableColumn<Item, SimpleDoubleProperty> colValue;

    @FXML
    private Label lblAddInventoryItem;

    @FXML
    private TextField txtNewItemName;

    @FXML
    private TextField txtNewItemSerialNumber;

    @FXML
    private TextField txtNewItemValue;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSerialNumber.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        if (!appModel.getInventory().Inventory.isEmpty()) {
            tableView.setItems(getListForTable(appModel.getInventory()));
            tableView.setEditable(true);
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
