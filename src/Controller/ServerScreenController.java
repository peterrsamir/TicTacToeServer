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
    private ClientHandler handler = new ClientHandler();
    private static GameServer gameServer;
    private boolean flag = true;
    

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
        btnStop.setDisable(true);
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
        if (flag) {
            th.start();
        } else {
            th.resume();
        }
        btnStop.setDisable(false);
        btnStart.setDisable(true);
        System.out.println("Starting server");
    }

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
