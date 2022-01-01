/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import Controller.GameServer.ServerHandler;

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
    GameServer outerobject = new GameServer();
        ServerHandler chat = outerobject.new ServerHandler();
//    private GameServer gameServer = new GameServer();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onStartAction(ActionEvent event) {
       
        chat.startConnection();
    }

    @FXML
    private void onStopAction(ActionEvent event) {
        chat.stopConnection();
    }
    
}
