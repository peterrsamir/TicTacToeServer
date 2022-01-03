/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author peter
 */
public class LogOut implements Serializable{
    String userName;

    public LogOut(String userName) {
        this.userName = userName;
    }

    public LogOut() {
    }

    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
