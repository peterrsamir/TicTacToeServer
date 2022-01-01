package Controller;

import Controller.ServerScreenBase;
import model.Player;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Login;
import model.Register;

public class GameServer {

    private ServerSocket server;
    private Socket waiter;

    public GameServer() {
        try {
            server = new ServerSocket(5005);
            while (true) {
                waiter = server.accept();
                new ServerHandler(waiter);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerScreenBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public class ServerHandler extends Thread {

        GameServer gameServer;
        DataInputStream dis;
        DataOutputStream dos;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        DBConnection db;
        Vector<ServerHandler> clientsVector = new Vector<ServerHandler>();
        //HashMap<Integer, ChatHandler> clients = new HashMap<Integer, ChatHandler>();

        public ServerHandler() {
        }

        public ServerHandler(Socket cs) {
            try {
                dis = new DataInputStream(cs.getInputStream());
                dos = new DataOutputStream(cs.getOutputStream());
                oos = new ObjectOutputStream(dos);
                ois = new ObjectInputStream(dis);
                clientsVector.add(new ServerHandler());
                start();

            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void run() {
            db.openConnection();
            while (true) {
                try {
                    Object readObj = ois.readObject();
                    if (readObj instanceof Login) {
                      boolean loginCheck = db.loginCheck((Login)readObj);
                      oos.writeObject(loginCheck);
                    }else if(readObj instanceof Register){
                        db.registerNewPlayer((Register)readObj);
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

        public int getPlayer1ID() {
            Player p = new Player();
            return p.getUserID();
        }

        public int getPlayer2ID() {
            Player p = new Player();
            return p.getUserID();
        }

//        void sendMessageSelectedClient(String msg, int player1ID, int player2ID) {
//            ChatHandler player1 = clientsVector.get(player1ID);
//            ChatHandler player2 = clientsVector.get(player2ID);
//
//            player1.dos.println(msg);
//            player2.dos.println(msg);
//
//        }
        
        public void stopConnection() {
            Iterator it = clientsVector.iterator();
            while (it.hasNext()) {
                try {
                    dis.close();
                    dos.close();
                    waiter.close();
                } catch (IOException ex) {
                    Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            try {
                server.close();
            } catch (IOException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void startConnection() {
            new GameServer();
        }
    }
}
