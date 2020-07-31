package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ModifyProductController implements Initializable {
    ObservableList<Part> partsAdded = FXCollections.observableArrayList();
    private Product productChosen = MainScreenController.productChosen;

    @FXML
    TableView partTable;
    @FXML
    TableColumn partidCol;
    @FXML
    TableColumn partNameCol;
    @FXML TableColumn partInvCol;
    @FXML TableColumn partPriceCol;

    @FXML TableView addedPartTable;
    @FXML TableColumn addedPartidCol;
    @FXML TableColumn addedPartNameCol;
    @FXML TableColumn addedInvCol;
    @FXML TableColumn addedPriceCol;

    @FXML TextField idField;
    @FXML TextField nameField;
    @FXML TextField priceField;
    @FXML TextField invField;
    @FXML TextField minField;
    @FXML TextField maxField;
    @FXML TextField searchField;

    @FXML Button searchButton;
    @FXML Button addButton;
    @FXML Button deleteButton;
    @FXML Button saveButton;
    @FXML Button cancelButton;

    @FXML
    Label warningLabel;

    public void pressSearch(){
        String searchInput = searchField.getText();
        ObservableList<Part> searchResults = Inventory.lookupPart(searchInput);
        partTable.setItems(searchResults);
    }

    public void pressSave(ActionEvent event) throws IOException{
        if (nameField.getText().equals("") ||priceField.getText().equals("")||invField.getText().equals("") ){
            warningLabel.setText("Product must have a name, price, and inventory level.");
            return;
        }
        if (Integer.parseInt(minField.getText()) <= Integer.parseInt(maxField.getText())) {
            if (productChosen.getStock() != Integer.parseInt(invField.getText())) {
                productChosen.setStock(Integer.parseInt(invField.getText()));
            }
            if (!productChosen.getName().equals(nameField.getText())) {
                productChosen.setName(nameField.getText());
            }
            if (productChosen.getPrice() != Double.parseDouble(priceField.getText())) {
                productChosen.setPrice(Double.parseDouble(priceField.getText()));
            }
            if (productChosen.getMin() != Integer.parseInt(minField.getText())) {
                productChosen.setMin(Integer.parseInt(minField.getText()));
            }
            if (productChosen.getMax() != Integer.parseInt(maxField.getText())) {
                productChosen.setMax(Integer.parseInt(maxField.getText()));
            }
            changeWindow(event,"MainScreen.fxml");
        }
        else if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())){
            warningLabel.setText("Minimum value cannot be greater than the maximum value.");
        }
    }
    public void pressAdd(){
        Part partChosen = (Part) partTable.getSelectionModel().getSelectedItem();
        productChosen.associatedParts.add(partChosen);
        showData();
    }
    public void pressDelete(){
        Part partChosen = (Part) addedPartTable.getSelectionModel().getSelectedItem();
        productChosen.associatedParts.remove(partChosen);
        showData();
    }
    public void changeWindow(ActionEvent event, String fxmlDoc) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlDoc));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void showData(){
        //Display Part table

        partidCol.setCellValueFactory(new PropertyValueFactory("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory("price"));

        partTable.setItems(Inventory.getAllParts());

        //Display Parts Added table
        addedPartidCol.setCellValueFactory(new PropertyValueFactory("id"));
        addedPartNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        addedInvCol.setCellValueFactory(new PropertyValueFactory("stock"));
        addedPriceCol.setCellValueFactory(new PropertyValueFactory("price"));


        addedPartTable.setItems(productChosen.associatedParts);
    }
    public void pressCancel(ActionEvent event) throws IOException{
        System.out.println("testing press Cancel.");
        changeWindow(event, "MainScreen.fxml");
    }
    public void initialize(URL location, ResourceBundle resources) {
        idField.setEditable(false);
        idField.setText(Integer.toString(Inventory.getNextID() ));
        nameField.setText(productChosen.getName());
        invField.setText(Integer.toString(productChosen.getStock()));
        priceField.setText(Double.toString(productChosen.getPrice()));
        minField.setText(Integer.toString(productChosen.getMin()));
        maxField.setText(Integer.toString(productChosen.getMax()));
        showData();
    }
}
