/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 rielly donnell
 */

package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("App.fxml")));

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Inventory Manager - brought to you by Rielly!");
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
