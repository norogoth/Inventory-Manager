package sample;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {
    @FXML TextField idAutoGenField;
    @FXML TextField nameField;
    @FXML TextField invField;
    @FXML TextField priceField;
    @FXML TextField maxField;
    @FXML TextField minField;
    @FXML TextField machineIDOrCompanyName;
    @FXML Label machineOrCompLabel;

    @FXML final ToggleGroup typeOfPart = new ToggleGroup();
    @FXML RadioButton outsourcedButton;
    @FXML RadioButton inHouseButton;

    @FXML Label minWarningLabel;

    public void typeChange(){
        if (outsourcedButton.isSelected()){
            machineOrCompLabel.setText("Company Name");
        }
        else if (inHouseButton.isSelected()){
            machineOrCompLabel.setText("Machine ID");
        }
        else{
            System.out.println("An error has occurred with the add part radio button. Please contact the developers and we will look into this issue.");
        }
    }

    public void changeWindow(ActionEvent event, String fxmlDoc) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlDoc));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void pressCancel(ActionEvent event) throws IOException {
        changeWindow(event,"MainScreen.fxml");
    }

    public void pressSave(ActionEvent event) throws IOException {
        if (Integer.parseInt(minField.getText()) <= Integer.parseInt(maxField.getText())) {
            if (inHouseButton.isSelected()) {
                Part newPart = new InHouse(nameField.getText()
                        , Double.parseDouble(priceField.getText())
                        , Integer.parseInt(invField.getText())
                        , Integer.parseInt(minField.getText())
                        , Integer.parseInt(maxField.getText())
                        , Integer.parseInt(machineIDOrCompanyName.getText()));
                Inventory.addPart(newPart);
            } else if (outsourcedButton.isSelected()) {
                Part newPart = new OutSourced(nameField.getText()
                        , Double.parseDouble(priceField.getText())
                        , Integer.parseInt(invField.getText())
                        , Integer.parseInt(minField.getText())
                        , Integer.parseInt(maxField.getText())
                        , machineIDOrCompanyName.getText());
                Inventory.addPart(newPart);
            }
            changeWindow(event,"MainScreen.fxml");
        }
        else if (Integer.parseInt(minField.getText()) > Integer.parseInt(maxField.getText())){
            minWarningLabel.setText("Minimum value cannot be greater than the maximum value.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        outsourcedButton.setToggleGroup(typeOfPart);
        inHouseButton.setToggleGroup(typeOfPart);
        idAutoGenField.setText(String.valueOf(Inventory.getNextID()));
        idAutoGenField.setDisable(true);
        inHouseButton.setSelected(true);
        //set text of ID text field. Disable write features in Scene Builder. Set text here.
    }
}
