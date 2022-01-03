/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.DBConnection.onlinePlayers;
import static Controller.DBConnection.topPlayers;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Login;
import model.Player;
import model.Register;
import model.TopOnlinePlayers;
import model.LogOut;

/**
 *
 * @author zoz
 */
public class ServerHandler extends Thread {

//    GameServer gameServer;
    InputStream is;
    OutputStream os;
    ObjectOutputStream oos;
//    ObjectInputStream ois;
    DBConnection db;
    Socket socket;
    Vector<ServerHandler> clientsVector = new Vector<ServerHandler>();
    //HashMap<Integer, ChatHandler> clients = new HashMap<Integer, ChatHandler>();

    public ServerHandler() {
    }

    public ServerHandler(Socket cs) {
        db = new DBConnection();
        try {

            os = cs.getOutputStream();
            is = cs.getInputStream();
            socket = cs;
            db.openConnection();
            clientsVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                ObjectInputStream ois = new ObjectInputStream(is);
                if (ois != null) {
                    Object readObj = ois.readObject();
                if (readObj instanceof Login) {
                    oos = new ObjectOutputStream(os);
                    try {
                        Player login = db.loginCheck((Login) readObj);
                        login.setIsOnline(1);
                        login.setIsRequest(1);
                        db.changeOnlineStatus(login);
                        db.inGameStatus(login);
                        Player player = login;
                        player = db.getPlayerInformation(login);
                        oos.writeObject(player);
                        oos.flush();
                    } catch (SQLException ex) {
                        Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                        oos.writeObject("Error");
                        oos.flush();
                    }

                } else if (readObj instanceof Register) {
                    oos = new ObjectOutputStream(os);
                    Register r = (Register) readObj;
                    System.out.println("userName: " + r.getUserName() + ", pass: " + r.getPassward());
                    try {
                        Player p = db.registerNewPlayer(r);
                        oos.writeObject(p);
                        oos.flush();
                    } catch (SQLException sQLException) {
                        System.out.println(sQLException.toString());
                        oos.writeObject("Error");
                        oos.flush();
                    }
                } else if (readObj instanceof TopOnlinePlayers) {
                    oos = new ObjectOutputStream(os);

                    TopOnlinePlayers topOnlinePlayers = (TopOnlinePlayers) readObj;
                    try {
                        String str = topOnlinePlayers.getUserName();
                        topOnlinePlayers.setOnlinePlayers(db.getOnlinePlayers(str));
                        topOnlinePlayers.setTopPlayers(db.getTopPlayers());
                        oos.writeObject(topOnlinePlayers);
                        oos.flush();
                    } catch (SQLException sQLException) {
                        System.out.println(sQLException.toString());
                        oos.writeObject("error 4");
                        oos.flush();
                    }
                }else if (readObj instanceof LogOut) {
                        Player p = new Player();
                        LogOut logOut = (LogOut) readObj;
                        System.out.println(logOut.getUserName());
                        p.setUserName(logOut.getUserName());
                        p.setIsRequest(0);
                        p.setIsOnline(0);
                        try {
                            System.out.println("in try");
                            db.changeOnlineStatus(p);
                            db.inGameStatus(p);
                            
                            oos = new ObjectOutputStream(os);
                            oos.writeObject("Logged out");
                            oos.flush();
                            ois.close();
                            oos.close();
                            socket.close();
                            clientsVector.remove(this);
                        } catch (SQLException ex) {
                            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("something wrong");
                            oos.writeObject("ERR");
                            oos.flush();
                        }

            } catch (SocketException s) {
            } catch (EOFException d) {
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
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
                os.close();
                is.close();
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
