/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author Ali
 */
public class ServerScreenController implements Initializable {

    DBConnection db;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    private ServerHandler handler = new ServerHandler();
    private static GameServer gameServer;
    @FXML
    PieChart pieChart;

    ObservableList<Data> chartData = FXCollections.observableArrayList(
            new PieChart.Data("Online", 79),
            new PieChart.Data("Offline", 16),
            new PieChart.Data("Avilable", 5));

    public ServerScreenController() {
        //db.getOnlinePlayers(p)
        /*try {
            this.x = db.getTopPlayers().indexOf(0);
        } catch (SQLException ex) {
            Logger.getLogger(ServerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    // pieChart.setTitle ("Online & offline Players");
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        pieChart.setData(chartData);
        pieChart.setLegendSide(Side.LEFT);
       pieChart.setClockwise(true);
        pieChart.setLabelsVisible(true);
//        pieChart.setPrefHeight(600); // no effect
//        pieChart.setPrefWidth(600);

    }

    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            gameServer = new GameServer();
        }
    });

    @FXML
    private void onStartAction(ActionEvent event) {
        th.start();
        System.out.println("Starting server");
    }

    @FXML
    private void onStopAction(ActionEvent event) {
        GameServer.closeConnection();
        System.out.println("Srver Closed");
    }

}
