/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Ali
 */
public class ServerScreenController implements Initializable {

    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    private ServerHandler handler = new ServerHandler();
    private static GameServer gameServer;
    PieChart pieChart = new PieChart();
        
        
        ObservableList<Data> chartData = FXCollections.observableArrayList(
                new PieChart.Data("OnLinePlayers", 25),
                new PieChart.Data("OffLinePlayers", 12));
        // pieChart.setData(chartData);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
