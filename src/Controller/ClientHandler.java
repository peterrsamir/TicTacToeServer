/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import model.Login;
import model.Player;
import model.Register;
import model.TopOnlinePlayers;
import model.LogOut;
import model.Move;
import model.Request;

/**
 *
 * @author zoz
 */
public class ClientHandler extends Thread {

    InputStream is;
    OutputStream os;
    ObjectOutputStream objectOutputStream;
    ObjectInputStream objectInputStream;
    DBConnection db;
    Player player = null;

    Socket socket;
    static HashMap<String, ClientHandler> clientsVector = new HashMap<String, ClientHandler>();

    public ClientHandler() {
    }

    public ClientHandler(Socket cs) {
        try {
            socket = cs;
            System.out.println(socket);
            os = socket.getOutputStream();
            is = socket.getInputStream();
            objectInputStream = new ObjectInputStream(is);
            objectOutputStream = new ObjectOutputStream(os);
            start();
        } catch (EOFException | SocketException s) {
            closeConnection();
            stop();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        db = new DBConnection();
        while (true) {
            try {
                Object readObj = objectInputStream.readObject();
                System.out.println(readObj.toString());
                if (readObj instanceof Login) {
                    try {

                        Player login = db.loginCheck((Login) readObj);
                        if (login != null) {
                            login.setIsOnline(1);
                            login.setIsRequest(1);
                            db.changeOnlineStatus(login);
                            db.inGameStatus(login);
                            Player p = login;
                            p = db.getPlayerInformation(login);
                            clientsVector.put(p.getUserName(), this);
                            System.out.println(p.getUserName());
                            System.out.println("No of Clients: " + clientsVector.size());
                            objectOutputStream.writeObject(p);
                            objectOutputStream.flush();
                            player = p;
                        } else {
                            objectOutputStream.writeObject("Login Error");
                            objectOutputStream.flush();
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        objectOutputStream.writeObject("Login Error");
                        objectOutputStream.flush();
                    }

                } else if (readObj instanceof Register) {
                    Register r = (Register) readObj;
                    System.out.println("userName: " + r.getUserName() + ", pass: " + r.getPassward());
                    try {
                        Player p = db.registerNewPlayer(r);
                        objectOutputStream.writeObject(p);
                        objectOutputStream.flush();
                        player = p;
                    } catch (SQLException sQLException) {
                        System.out.println(sQLException.toString());
                        objectOutputStream.writeObject("Error");
                        objectOutputStream.flush();
                    }
                } else if (readObj instanceof TopOnlinePlayers) {
                    TopOnlinePlayers topOnlinePlayers = (TopOnlinePlayers) readObj;
                    try {
                        String str = topOnlinePlayers.getUserName();
                        topOnlinePlayers.setOnlinePlayers(db.getOnlinePlayers(str));
                        topOnlinePlayers.setTopPlayers(db.getTopPlayers());
                        objectOutputStream.writeObject(topOnlinePlayers);
                        objectOutputStream.flush();
                    } catch (SQLException sQLException) {
                        System.out.println(sQLException.toString());
                        objectOutputStream.writeObject("error 4");
                        objectOutputStream.flush();
                    }
                } else if (readObj instanceof LogOut) {
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
                        System.out.println(objectOutputStream.toString());
                        objectOutputStream.writeObject("LoggedOut");
                        System.out.println(socket);
                        objectOutputStream.flush();
                        closeConnection();
                        clientsVector.remove(p.getUserName());
                    } catch (SQLException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("something wrong");
                    }
                } else if (readObj instanceof Request) {
                    Request sendRequest = (Request) readObj;
                    System.out.println(sendRequest.toString());
                    if (sendRequest.isRequest()) {
                        System.out.println("sender: " + sendRequest.isRequest());
                        sendRequestToPlayer(sendRequest.getReceiverUserName(), sendRequest);
                    } else {
                        System.out.println("receiver: " + sendRequest.isResponse());
                        sendRequestToPlayer(sendRequest.getSendingUserName(), sendRequest);
                        if (sendRequest.isResponse()) {
                            Platform.runLater(() -> {
                                new GameSession(clientsVector.get(sendRequest.getReceiverUserName()), clientsVector.get(sendRequest.getReceiverUserName()));
                            });
                        }
                    }
                }
//                else if (readObj instanceof Move) {
//                    Move move = (Move) readObj;
//                    System.out.println("m1:" + move.getPlayer1());
//                    System.out.println("m2" + move.getPlayer2());
//                    if (move.isP1Turn()) {
//                        System.out.println("sender: " + move.isP1Turn());
//                        sendMoveToPlayer(move.getPlayer1(), move);
//                    } else {
//                        System.out.println("receiver: " + move.isP2Turn());
//                        sendMoveToPlayer(move.getPlayer2(), move);
//                    }
//                }
            } catch (SocketException | EOFException s) {
                closeConnection();
                if(player != null){
                    clientsVector.remove(player.getUserName());
                }
                this.stop();
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public synchronized void sendRequestToPlayer(String userName, Request r) {
        try {
            ClientHandler ch = clientsVector.get(userName);
            System.out.println("Receiver: " + ch.socket);
            ch.objectOutputStream.writeObject(r);
            ch.objectOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }

    public synchronized void sendMoveToPlayer(String userName, Move m) {
        try {
            ClientHandler ch = clientsVector.get(userName);
            System.out.println("Receiver: " + ch.socket);
            ch.objectOutputStream.writeObject(m);
            ch.objectOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    }

    public synchronized void closeConnection() {
        try {
            is.close();
            os.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized void closeAllConnection() {
        if (clientsVector != null) {
            clientsVector.values().forEach((ch) -> {
                try {
                    ch.objectOutputStream.close();
                    ch.objectInputStream.close();
                    ch.os.close();
                    ch.is.close();
                    ch.socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            clientsVector.clear();
        }
    }

    /*public void startConnection() {
        System.out.println("starting");
        new GameServer();
    }*/
}
