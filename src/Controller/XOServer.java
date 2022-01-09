/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author peter
 */
public class XOServer extends Application {

    DBConnection db;

    @Override
    public void start(Stage stage) throws Exception {
        
        //StackPane root = new StackPane();
        Parent root = FXMLLoader.load(getClass().getResource("/view/ServerScreen.fxml"));
        //root.getC
        Scene scene = new Scene(root, 700, 500);
        stage.setScene(scene);
        stage.setMinHeight(630);
        stage.setMinWidth(600);
        stage.show();
    }

//==================================================
    public static void main(String[] args) {
        launch(args);
    }

}
