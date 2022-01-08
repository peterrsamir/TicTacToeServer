/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Player;

/**
 * FXML Controller class
 *
 * @author Ali
 */
public class ServerScreenController implements Initializable {

    DBConnection db = new DBConnection();
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStop;
    @FXML
    private Text serverIp;
    @FXML
    private PieChart pieChart = new PieChart();
    @FXML
    private ListView<String> onLineList;

    ObservableList<String> onlineObservableList;

    private ClientHandler handler = new ClientHandler();
    private static GameServer gameServer;
    private boolean flag = true;
    private static Vector<Player> allonlinePlayers;
    private static Vector<Player> avaliablePlayers;
    private static Vector<Player> offlinePlayers;
    private Thread thread;
    Vector<Player> players;
    int numOfOnlinePlayers;
    int numOfOfflinePlayers;
    int numOfAvaliablePlayers;
    String ip;

    ObservableList<Data> chartData;

//===============================================================
    @FXML
    private Rectangle recOnlinePlayers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getIpAddress();
        serverIp.setText("" + ip);
        btnStop.setDisable(true);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                DBConnection.openConnection();
                while (true) {

                    getOnlinePlayers();

                    getPieChart();

                    try {
                        thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ServerScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
        thread.start();

        pieChart.setLegendSide(Side.LEFT);
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(true);
        pieChart.setLabelLineLength(10);

    }
//===============================================================
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
        th.suspend();
        flag = false;
        btnStop.setDisable(true);
        btnStart.setDisable(false);
        System.out.println("Srver Closed");
    }

    //================================================
    public void getIpAddress() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();

        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//=====================================================

    public void getOnlinePlayers() {
        try {
            players = DBConnection.getAllOnlinePlayers();
        } catch (SQLException ex) {
            Logger.getLogger(ServerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> playerName = new ArrayList<>();

        for (Player p : players) {
            playerName.add(p.getUserName());
        }
        onlineObservableList = FXCollections.observableArrayList(playerName);
        onLineList.refresh();
        Platform.runLater(() -> {
            onLineList.setItems(onlineObservableList);

        });

        System.out.println("list" + onLineList.getItems());
    }

    //=================================
    public void getPieChart() {
        try {
            allonlinePlayers = DBConnection.getAllOnlinePlayers();
            numOfOnlinePlayers = allonlinePlayers.size();
            offlinePlayers = DBConnection.getOfflinePlayers();
            numOfOfflinePlayers = offlinePlayers.size();
            avaliablePlayers = DBConnection.getAvilablePlayers();
            numOfAvaliablePlayers = avaliablePlayers.size();
        } catch (SQLException ex) {
            Logger.getLogger(ServerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        chartData = FXCollections.observableArrayList(
                new PieChart.Data("Online", numOfOnlinePlayers),
                new PieChart.Data("Offline", numOfOfflinePlayers),
                new PieChart.Data("Avilable", numOfAvaliablePlayers));
        Platform.runLater(() -> {
            pieChart.setData(chartData);
        });
    }
    //====================================================== 
}
