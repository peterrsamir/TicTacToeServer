package Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class GameServer {

    protected static ServerSocket server;
//    protected Socket waiter;
//    private static ServerHandler serverHandler;

    public GameServer() {
        try {
            server = new ServerSocket(5555);
            while (true) {
                Socket waiter = server.accept();
                new ServerHandler(waiter);
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
