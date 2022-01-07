package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class GameServer {

    protected static ServerSocket server;
    protected DBConnection db;
//    protected Socket waiter;
//    private static ServerHandler serverHandler;

    public GameServer() {
        db = new DBConnection();
        try {
            db.openConnection();
            server = new ServerSocket(5005);
            while (true) {
                Socket waiter = server.accept();
                new ClientHandler(waiter);
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /*public static void closeConnection() {
        serverHandler = new ServerHandler();
        serverHandler.stopConnection();
        try {
            server.close();
            server = null;
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
