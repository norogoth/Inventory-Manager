package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    //need to create fxml stuff here.
    @FXML
    TextField idAutoGenField;
    @FXML
    TextField nameField;
    @FXML
    TextField invField;
    @FXML
    TextField maxField;
    @FXML
    TextField minField;
    @FXML
    TextField priceField;
    @FXML
    TextField CNorMIDField;
    @FXML
    Label machineOrCompLabel;

    @FXML
    Button cancelButton;
    @FXML
    Button saveButton;

    @FXML
    final ToggleGroup typeOfPart = new ToggleGroup();
    @FXML
    RadioButton outsourcedButton;
    @FXML
    RadioButton inHouseButton;

    @FXML
    Label minWarningLabel;

    Part partChosen = MainScreenController.partChosen;

    public void setMachineOrCompLabel(String name) {
        this.machineOrCompLabel.setText(name);

    }

    public void typeChange() {
        if (outsourcedButton.isSelected()) {
            machineOrCompLabel.setText("Company Name");
        } else if (inHouseButton.isSelected()) {
            machineOrCompLabel.setText("Machine ID");
        } else {
            System.out.println("An error has occured with the add part radio button. Please contact the developers and we will look into this issue.");
        }
    }

    public void changeWindow(ActionEvent event, String fxmlDoc) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlDoc));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void pressCancel(ActionEvent event) throws IOException {
        changeWindow(event, "MainScreen.fxml");
    }

    public void modifyFields(Part part) {

    }

    public void modifyFields(Product product) {

    }

    public void setAttributesToTextFields(Part part) {
        //ID taken out so that ID will not change
        if (part.getName().length() != 0) {
            part.setName(nameField.getText());
        }
        if (invField.getText().length() != 0) {
            part.setStock(Integer.parseInt(invField.getText()));
        }
        if (priceField.getText().length() != 0) {
            part.setPrice(Double.parseDouble(priceField.getText()));
        }
        if (minField.getText().length() != 0) {
            part.setMin(Integer.parseInt(minField.getText()));
        }
        if (maxField.getText().length() != 0) {
            part.setMax(Integer.parseInt(maxField.getText()));
        }
        if (typeOfPart.getSelectedToggle() == inHouseButton && part instanceof InHouse) {
            ((InHouse) part).setMachineid(Integer.parseInt(CNorMIDField.getText()));
        } else if (typeOfPart.getSelectedToggle() == outsourcedButton && part instanceof OutSourced) {
            ((OutSourced) part).setCompanyName(CNorMIDField.getText());
        }
    }

    public void pressSave(ActionEvent event) throws IOException {
        if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())) {
            minWarningLabel.setText("Minimum cannot be greater than the maximum.");
            return;
        }
        else if (typeOfPart.getSelectedToggle() == outsourcedButton) {
            if (partChosen instanceof OutSourced){
                setAttributesToTextFields(partChosen);
            }
            else if (partChosen instanceof InHouse) {
                Part newPart = new OutSourced(partChosen.getName(), partChosen.getPrice(), partChosen.getStock(), partChosen.getMin(), partChosen.getMax(), CNorMIDField.getText());
                newPart.setId(partChosen.getId());
                Inventory.addPart(newPart);
                Inventory.deletePart(partChosen);
                setAttributesToTextFields(partChosen);
                }
            else {
                System.out.println("Error: Part should have been of type InHouse.");
            }
            }
            else if (typeOfPart.getSelectedToggle() == inHouseButton){
                if (partChosen instanceof InHouse){
                    setAttributesToTextFields(partChosen);
                }
                else if (partChosen instanceof OutSourced) {
                    Part newPart = new InHouse(partChosen.getName(),partChosen.getPrice(),partChosen.getStock(),partChosen.getMin(),partChosen.getMax(),Integer.parseInt(CNorMIDField.getText()));
                    newPart.setId(partChosen.getId());
                    Inventory.addPart(newPart);
                    Inventory.deletePart(partChosen);
                }
                else {
                    System.out.println("Error: Part should have been of type outsourced.");
                }
            }
            else {
                System.out.println("Error: It seems that neither 'in house' nor 'outsourced' was selected.");
            }
            changeWindow(event, "MainScreen.fxml");
        }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(partChosen); That works. So we get the part from the MainScreen.
        if (partChosen instanceof InHouse){
            inHouseButton.setSelected(true);
        }
        else if (partChosen instanceof OutSourced){
            outsourcedButton.setSelected(true);
        }
        outsourcedButton.setToggleGroup(typeOfPart);
        inHouseButton.setToggleGroup(typeOfPart);
        idAutoGenField.setText(Integer.toString(partChosen.getId()));
        nameField.setText(partChosen.getName());
        priceField.setText(Double.toString(partChosen.getPrice()));
        invField.setText(Integer.toString(partChosen.getStock()));
        maxField.setText(Integer.toString(partChosen.getMax()));
        minField.setText(Integer.toString(partChosen.getMin()));
        if (partChosen instanceof InHouse){
            CNorMIDField.setText(Integer.toString(((InHouse) partChosen).getMachineid()));
            this.machineOrCompLabel.setText("Machine ID");
        }
        else if (partChosen instanceof OutSourced){
            CNorMIDField.setText(((OutSourced) partChosen).getCompanyName());
            this.machineOrCompLabel.setText("Company Name");
        }
    }
}
