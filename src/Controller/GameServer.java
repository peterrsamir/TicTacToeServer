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
}
