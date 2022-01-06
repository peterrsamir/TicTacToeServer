/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
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
        stage.setMinHeight(500);
        stage.setMinWidth(600);
        stage.show();
    }

//==================================================
    public static void main(String[] args) {
        launch(args);
    }

}
