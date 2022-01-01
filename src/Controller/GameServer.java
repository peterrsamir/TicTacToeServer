package Controller;

import com.sun.webkit.ThemeClient;
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

    protected static ServerSocket server;
    protected Socket waiter;
    private static ServerHandler serverHandler;

    public GameServer() {
        try {
            server = new ServerSocket(8080);
            while (true) {
                waiter = server.accept();
                new ServerHandler(waiter);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerScreenBase.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void closeConnection() {
        serverHandler = new ServerHandler();
        serverHandler.stopConnection();
        try {
            server.close();
            server = null;
        } catch (IOException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
