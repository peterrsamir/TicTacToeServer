/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author zoz
 */
public class Player implements Serializable {
    private String userName;
    private String password;
    private int isOnline;
    private int isRequest;
    private int totalScore;
    private int noOfWins;
    private int noOfLoss;

    public Player() {
    }

    public Player(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    
    public Player(String userName) {
        this.userName = userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public int getIsRequest() {
        return isRequest;
    }

    public void setIsRequest(int isRequest) {
        this.isRequest = isRequest;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int noOfWins,int noOfLoss) {
        this.totalScore = (noOfWins*10) - (noOfLoss*5);
    }

    public int getNoOfWins() {
        return noOfWins;
    }

    public void setNoOfWins(int noOfWins) {
        this.noOfWins = noOfWins;
    }

    public int getNoOfLoss() {
        return noOfLoss;
    }

    public void setNoOfLoss(int noOfLoss) {
        this.noOfLoss = noOfLoss;
    }
    
}
