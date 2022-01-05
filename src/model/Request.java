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
public class Request implements Serializable{

    private String sendingUserName;
    private String receiverUserName;
    private boolean response;
    private boolean request;

    public boolean isRequest() {
        return request;
    }

    public void setRequest(boolean request) {
        this.request = request;
    }
    
    
    public Request() {
    }

    public String getSendingUserName() {
        return sendingUserName;
    }

    public void setSendingUserName(String sendingUserName) {
        this.sendingUserName = sendingUserName;
    }

    public String getReceiverUserName() {
        return receiverUserName;
    }

    public void setReceiverUserName(String receiverUserName) {
        this.receiverUserName = receiverUserName;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

}
