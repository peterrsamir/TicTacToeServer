/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Azza Helmy
 */
public class TopOnlinePlayers implements Serializable{
    Vector <Player> onlinePlayers;
    Vector<Player> topPlayers;

    public TopOnlinePlayers() {
    }

    public TopOnlinePlayers(Vector<Player> onlinePlayers, Vector<Player> topPlayers) {
        this.onlinePlayers = onlinePlayers;
        this.topPlayers = topPlayers;
    }

    public Vector<Player> getOnlinePlayers() {
        return onlinePlayers;
    }

    public void setOnlinePlayers(Vector<Player> onlinePlayers) {
        this.onlinePlayers = onlinePlayers;
    }

    public Vector<Player> getTopPlayers() {
        return topPlayers;
    }

    public void setTopPlayers(Vector<Player> topPlayers) {
        this.topPlayers = topPlayers;
    }
    
    
}
