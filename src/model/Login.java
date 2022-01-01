/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Azza Helmy
 */
public class Login implements Serializable{
    private String userName;
    private String passward;

    public Login(String userName, String passward) {
        this.userName = userName;
        this.passward = passward;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }
    
}
