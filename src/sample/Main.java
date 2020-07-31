package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent root = loader.load();
        Scene main_scene = new Scene(root);
        primaryStage.setTitle("Inventory Management");
        MainScreenController controller = loader.getController();
        primaryStage.setScene(main_scene);
        primaryStage.show();
        Inventory.generateSampleData();
        //controller.showData();
    }


    public static void main(String[] args) {

        launch(args);

    }
}
