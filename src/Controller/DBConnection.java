/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import model.Match;
import model.Player;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Login;
import model.Register;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author zoz
 */
public class DBConnection {

    private static final String PLAYERS_TABLE = "PLAYERS";
    private static final String MATCHES_TABLE = "MATCHES";
    static Vector<Player> onlinePlayers;
    static Vector<Player> topPlayers;
    static ResultSet rs;
    static Connection con;
    static PreparedStatement pst;

    public static void openConnection() {
        try {
            DriverManager.registerDriver(new ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection msg: " + ex.getMessage());
        }
    }

    public void registerNewPlayer(Register register) throws SQLException {
        String insertSQL = "INSERT INTO " + PLAYERS_TABLE + " (USERNAME,PASSWORD,ISONLINE,ISREQUEST,TOTALSCORE,NOOFWINS,NOOFLOSS) VALUES (?,?,?,?,?,?,?)";
        pst = con.prepareStatement(insertSQL);
        pst.setString(1, register.getUserName());
        pst.setString(2, register.getPassward());
        pst.setInt(3, 1);
        pst.setInt(4, 1);
        pst.setInt(5, 0);
        pst.setInt(6, 0);
        pst.setInt(7, 0);
        pst.executeUpdate();
    }

    public Vector<Player> getOnlinePlayers(String userName) throws SQLException {
        String onlineSQL = "SELECT * FROM " + PLAYERS_TABLE + " WHERE ISONLINE = 1 AND USERNAME <> " + userName;
        pst = con.prepareStatement(onlineSQL);
        rs = pst.executeQuery();
        onlinePlayers = new Vector<>();
        while (rs.next()) {
            Player p = new Player();
//            p.setUserID(rs.getInt(1));
            p.setUserName(rs.getString(2));
            p.setPassword(rs.getString(3));
            p.setIsOnline(rs.getInt(4));
            p.setIsRequest(rs.getInt(5));
            p.setTotalScore(rs.getInt(7), rs.getInt(8));
            p.setNoOfWins(rs.getInt(7));
            p.setNoOfLoss(rs.getInt(8));
            onlinePlayers.add(p);
        }
        return onlinePlayers;
    }

    public Vector<Player> getTopPlayers() throws SQLException {
        String onlineSQL = "SELECT USERNAME , NOOFWINS, NOOFLOSS FROM " + PLAYERS_TABLE + " ORDER BY TOTALSCORE desc";
        pst = con.prepareStatement(onlineSQL);
        rs = pst.executeQuery();
        topPlayers = new Vector<>();
        while (rs.next()) {
            Player p = new Player();
            p.setUserName(rs.getString(1));
            p.setTotalScore(rs.getInt(2), rs.getInt(3));
            topPlayers.add(p);
        }
        return topPlayers;
    }

    public Player loginCheck(Login l) throws SQLException {
        String loginSQL = "SELECT USERNAME , PASSWORD FROM PLAYERS";
        pst = con.prepareStatement(loginSQL);
        rs = pst.executeQuery();
        while (rs.next()) {
            if (rs.getString(1).equals(l.getUserName()) && rs.getString(2).equals(l.getPassward())) {
                Player p = new Player();
                p.setUserName(l.getUserName());
                p.setPassword(l.getPassward());
                return p;
            }
        }
        return null;
    }

    public int getPlayerID(Player p) throws SQLException {
        String playerID = "SELECT ID FROM " + PLAYERS_TABLE + " WHERE USERNAME = ?";
        pst = con.prepareStatement(playerID);
        pst.setString(1, p.getUserName());
        rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public Player getPlayerInformation(Player p) throws SQLException {
        String playerInformation = "SElECT NOOFWINS, NOOFLOSS FROM PLAYERS WHERE USERNAME = ?";
        pst = con.prepareStatement(playerInformation);
        pst.setString(1, p.getUserName());
        rs = pst.executeQuery();
        if (rs.next()) {
            p.setNoOfWins(rs.getInt(1));
            p.setNoOfLoss(rs.getInt(2));
        }
        p.setTotalScore(p.getNoOfWins(), p.getNoOfLoss());
        return p;
    }

    public void insertToMatchTable(Match m) throws SQLException {
        String insertMTSQL = "INSERT INTO " + MATCHES_TABLE + " (TIMEDATE,P1ID,P2ID) VALUES (?,?,?)";
        pst = con.prepareStatement(insertMTSQL);
        pst.setDate(1, m.getTimeDate());
        pst.setInt(2, m.getP1ID());
        pst.setInt(3, m.getP2ID());
        pst.executeUpdate();
    }

    public void changeOnlineStatus(Player p) throws SQLException {
        String onlineSQL = "UPDATE PLAYERS SET ISONLINE = ? WHERE USERNAME = ?";
        pst = con.prepareStatement(onlineSQL);
        pst.setInt(1, p.getIsOnline());
        pst.setString(2, p.getUserName());
        pst.executeUpdate();
    }

    public void inGameStatus(Player p) throws SQLException {
        String inGameSQL = "UPDATE PLAYERS SET ISREQUEST = ? WHERE USERNAME = ?";
        pst = con.prepareStatement(inGameSQL);
        pst.setInt(1, p.getIsRequest());
        pst.setString(2, p.getUserName());
        pst.executeUpdate();
    }

    public void updateTotalScore(Player p) throws SQLException {
        String updateTS = "UPDATE " + PLAYERS_TABLE + " SET TOTALSCORE = ? WHERE ID = ?";
        pst = con.prepareStatement(updateTS);
        pst.setInt(1, p.getTotalScore());
        pst.setInt(2, getPlayerID(p));
        pst.executeUpdate();
    }

    public void updateNoOfWins(Player p) throws SQLException {
        String updatewins = "UPDATE " + PLAYERS_TABLE + " SET NOOFWINS = ? WHERE ID = ?";
        pst = con.prepareStatement(updatewins);
        pst.setInt(1, p.getNoOfWins());
        pst.setInt(2, getPlayerID(p));
        pst.executeUpdate();
    }

    public void updateNoOfLoss(Player p) throws SQLException {
        String updateloss = "UPDATE " + PLAYERS_TABLE + " SET NOOFLOSS = ? WHERE ID = ?";
        pst = con.prepareStatement(updateloss);
        pst.setInt(1, p.getNoOfLoss());
        pst.setInt(2, getPlayerID(p));
        pst.executeUpdate();
    }
}