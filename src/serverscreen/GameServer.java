/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverscreen;

import databaseconnection.Player;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameServer {

    private ServerSocket server;
    private Socket waiter;

    public GameServer() {
        try {
            server = new ServerSocket(5005);
            while (true) {
                waiter = server.accept();
                new ChatHandler(waiter);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerScreenBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

class ChatHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    //static Vector<ChatHandler> clientsVector = new Vector<ChatHandler>();
    static HashMap<Integer, ChatHandler> clients = new HashMap<Integer, ChatHandler>();

    public ChatHandler(Socket cs) {
        try {
            dis = new DataInputStream(cs.getInputStream());
            ps = new PrintStream(cs.getOutputStream());
            clients.put(new Player().getUserID(), this);
            start();

        } catch (IOException ex) {
            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        while (true) {

            try {
                String str = dis.readLine();
                int id1 = 1;
                int id2 = 2;
                sendMessageSelectedClient(str,id1,id2);
            } catch (IOException ex) {
                Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getPlayer1ID(){
        Player p = new Player();
        return p.getUserID();
    }
    
    public int getPlayer2ID(){
        Player p = new Player();
        return p.getUserID();
    }

    void sendMessageSelectedClient(String msg,int player1ID, int player2ID) {
        ChatHandler player1 = clients.get(player1ID);
        ChatHandler player2 = clients.get(player2ID);

        player1.ps.println(msg);
        player2.ps.println(msg);

    }

    public void stopConnection() {

    }
}
