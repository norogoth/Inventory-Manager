package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    //ATTRIBUTES
    //public Inventory mainInventory = new Inventory();
    public ObservableList<Part> partList = FXCollections.observableArrayList();
    public static Part partChosen;
    public static Product productChosen;

    @FXML public TextField searchPartTextField;
    @FXML public TextField searchProductTextField;

    //Button
    @FXML public Button addPartButton;
    @FXML public Button modifyPartButton;
    @FXML public Button deletePartButton;
    @FXML public Button searchPartButton;
    @FXML public Button addProductButton;
    @FXML public Button modifyProductButton;
    @FXML public Button deleteProductButton;
    @FXML public Button searchProduct;

    //Part View
    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, String> partidCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, String> partInventoryLevelCol;
    @FXML private TableColumn<Part, String> partPriceCol;

    //Product View
    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, String> productidCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, String> productInventoryLevelCol;
    @FXML private TableColumn<Product, String> productPriceCol;

    public void changeWindow(ActionEvent event, String fxmlDoc) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlDoc));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void deleteProductButtonPress(){
        productChosen = productTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(productChosen);
    }
    public void deletePartButtonPress(){
        partChosen = partTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(partChosen);
    }
    public void searchPartButtonPress(){
        String searchInput = searchPartTextField.getText();
        ObservableList<Part> searchResults = Inventory.lookupPart(searchInput);
        partTable.setItems(searchResults);
    }
    public void searchProductButtonPress(){
        String searchInput = searchProductTextField.getText();
        ObservableList<Product> searchResults = Inventory.lookupProduct(searchInput);
        productTable.setItems(searchResults);
    }
    public void addPartButtonPress(ActionEvent event) throws IOException {
        changeWindow(event,"AddPartScreen.fxml");
    }

    public void modifyPartButtonPress(ActionEvent event) throws IOException {
        partChosen = partTable.getSelectionModel().getSelectedItem();
        //System.out.println(partChosen);
        if (true) { //edit this later on
            changeWindow(event, "ModifyPartScreen.fxml");
        }
    }

    public void addProductButtonPress(ActionEvent event) throws IOException {
        changeWindow(event,"AddProductScreen.fxml");
    }

    public void modifyProductButtonPress(ActionEvent event) throws IOException {
        productChosen = productTable.getSelectionModel().getSelectedItem();
        changeWindow(event, "ModifyProductScreen.fxml");
    }

    public void exitButtonPress(ActionEvent event) throws IOException {
        System.exit(0);
    }


    public void showData(){
        //Display Part table
        partidCol.setCellValueFactory(new PropertyValueFactory("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory("name"));
        partInventoryLevelCol.setCellValueFactory(new PropertyValueFactory("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory("price"));

        partTable.setItems(Inventory.getAllParts());

        //Display Product table
        productidCol.setCellValueFactory(new PropertyValueFactory("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory("Name"));
        productInventoryLevelCol.setCellValueFactory(new PropertyValueFactory("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory("price"));

        productTable.setItems(Inventory.getAllProduct());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showData();
    }
}
