package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.utils.ClientTcp;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("SuperFarminator");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
//
        //System.out.println(ClientTcp.makeRequest("111|111|World|2016-01-11|15|4"));
//        ClientTcp.requestCodes.get("UnfinishedTasks");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
