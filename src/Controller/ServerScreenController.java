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
    private ClientHandler handler = new ClientHandler();
    private static GameServer gameServer;
    private boolean flag = true;
    
    @FXML
    PieChart pieChart = new PieChart();

    ObservableList<Data> chartData = FXCollections.observableArrayList(
            new PieChart.Data("Online", 60),
            new PieChart.Data("Offline", 10),
            new PieChart.Data("Avilable", 30));
//===============================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //db.getOnlinePlayers(userName);
        btnStop.setDisable(true);
        pieChart.setData(chartData);
        pieChart.setLegendSide(Side.LEFT);
        pieChart.setClockwise(false);
        pieChart.setLabelsVisible(false);
        //Setting the length of the label line 
        pieChart.setLabelLineLength(50);
        //Setting the start angle of the pie chart 
        //pieChart.setStartAngle(180);

    }

    Thread th = new Thread(new Runnable() {
        @Override
        public void run() {
            gameServer = new GameServer();
        }
    });
//==========================================================

    @FXML
    private void onStartAction(ActionEvent event) {
        if (flag) {
            th.start();
        } else {
            th.resume();
        }
        btnStop.setDisable(false);
        btnStart.setDisable(true);
        System.out.println("Starting server");
    }
//===========================================================

    @FXML
    private void onStopAction(ActionEvent event) {
//        GameServer.closeConnection();
        th.suspend();
        flag = false;
        btnStop.setDisable(true);
        btnStart.setDisable(false);
        System.out.println("Srver Closed");
    }

}
