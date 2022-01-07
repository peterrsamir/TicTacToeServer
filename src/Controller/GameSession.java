/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Move;

/**
 *
 * @author zoz
 */
public class GameSession extends Thread {

    private ClientHandler player1;
    private ClientHandler player2;
    private int[] board = new int[8];

    public GameSession() {
    }

    public GameSession(ClientHandler player1, ClientHandler player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("test");
                if (player1.objectInputStream != null) {
                    Move p1Move = (Move) player1.objectInputStream.readObject();
                    System.out.println("GameSession Player1");
                    player2.objectOutputStream.writeObject(p1Move);
                } else if (player2.objectInputStream != null) {
                    Move p2Move = (Move) player2.objectInputStream.readObject();
                    System.out.println("GameSession Player2");
                    player1.objectOutputStream.writeObject(p2Move);
                }
            } catch (SocketException | EOFException s) {
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
