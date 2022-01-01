/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Login;
import model.Player;
import model.Register;

/**
 *
 * @author zoz
 */
public class ServerHandler extends Thread {

    GameServer gameServer;
    DataInputStream dis;
    DataOutputStream dos;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    DBConnection db;
    Socket socket;
    Vector<ServerHandler> clientsVector = new Vector<ServerHandler>();
    //HashMap<Integer, ChatHandler> clients = new HashMap<Integer, ChatHandler>();

    public ServerHandler() {
    }

    public ServerHandler(Socket cs) {
        db = new DBConnection();
        socket = cs;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(dos);
            ois = new ObjectInputStream(dis);
            clientsVector.add(this);

            start();

        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        db.openConnection();
        while (true) {
            try {
                Object readObj = ois.readObject();
//                String msg = (String)readObj;
////                msg = dis.readLine();
//                System.out.println(msg);
                if (readObj instanceof Login) {
                    boolean loginCheck = db.loginCheck((Login) readObj);
//                    System.out.println(((Login) readObj).getUserName()+ " , " +((Login) readObj).getPassword());
//                    oos.writeObject(loginCheck);
                } else if (readObj instanceof Register) {
                    Register r = (Register) readObj;
//                    
                    System.out.println("userName: " + r.getUserName() + ", pass: " + r.getPassward());
                    try {
                        db.registerNewPlayer(r);
                     oos.writeObject("Done");
                        
                    } catch (SQLException sQLException) {
                        System.out.println(sQLException.toString());
                        
                        oos.writeObject("Error");
                    }
//                    db.getPlayerID((Player) readObj);

//                    Register r = (Register) readObj;
//                    if (r.getUserName().equals("aziza")) {
//                        
//                    } else {
//                        System.out.println(r.getUserName() + " , " + r.getPassward());
//                    }
//
//                    oos.writeObject("valid user name");
                }

//                    String outStr = (String) oos.writeObject(dos);
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /* public int getPlayer1ID() {
        Player p = new Player();
        return p.getUserID();
    }

    public int getPlayer2ID() {
        Player p = new Player();
        return p.getUserID();
    }*/
//        void sendMessageSelectedClient(String msg, int player1ID, int player2ID) {
//            ChatHandler player1 = clientsVector.get(player1ID);
//            ChatHandler player2 = clientsVector.get(player2ID);
//
//            player1.dos.println(msg);
//            player2.dos.println(msg);
//
//        }
    public void stopConnection() {
        System.out.println("stoping");
        Iterator it = clientsVector.iterator();
        while (it.hasNext()) {
            try {
                ois.close();
                oos.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
//        try {
////            server.close();
//        } catch (IOException ex) {
//            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    /*public void startConnection() {
        System.out.println("starting");
        new GameServer();
    }*/
}
