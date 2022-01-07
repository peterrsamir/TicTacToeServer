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
import java.util.ResourceBundle;
import java.util.Vector;
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
    PieChart pieChart = new PieChart();

    private ClientHandler handler = new ClientHandler();
    private static GameServer gameServer;
    private boolean flag = true;
    private static Vector<Player> allonlinePlayers;
    private static Vector<Player> avaliablePlayers;
    private static Vector<Player> offlinePlayers;
    int numOfOnlinePlayers;
    int numOfOfflinePlayers;
    int numOfAvaliablePlayers;
    String ip;

    ObservableList<Data> chartData = FXCollections.observableArrayList(
            new PieChart.Data("Online", numOfOnlinePlayers),
            new PieChart.Data("Offline", numOfOfflinePlayers),
            new PieChart.Data("Avilable", numOfAvaliablePlayers));
//        ObservableList<Data> chartData = FXCollections.observableArrayList(
//            new PieChart.Data("Online", 18),
//            new PieChart.Data("Offline", 10),
//            new PieChart.Data("Avilable", 20));
//===============================================================

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();

        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverIp.setText("" + ip);
        

        btnStop.setDisable(true);
        pieChart.setData(chartData);
        pieChart.setLegendSide(Side.LEFT);
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(true);
        //Setting the length of the label line 
        pieChart.setLabelLineLength(10);
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
        try {
            allonlinePlayers = DBConnection.getAllOnlinePlayers();
            numOfOnlinePlayers = allonlinePlayers.size();
            offlinePlayers = DBConnection.getOfflinePlayers();
            numOfOfflinePlayers = offlinePlayers.size();
            avaliablePlayers=DBConnection.getAvilablePlayers();
            numOfAvaliablePlayers=avaliablePlayers.size();
        } catch (SQLException ex) {
            Logger.getLogger(ServerScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
