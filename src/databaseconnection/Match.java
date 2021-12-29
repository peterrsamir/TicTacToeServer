/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseconnection;

import java.sql.Date;

/**
 *
 * @author zoz
 */
public class Match {
    private int MatchNo;
    private Date timeDate;
    private int p1ID;
    private int p2ID;

    public Match(Date timeDate, int p1ID, int p2ID) {
        this.timeDate = timeDate;
        this.p1ID = p1ID;
        this.p2ID = p2ID;
    }

    public Match() {
    }

    
    
    public int getMatchNo() {
        return MatchNo;
    }

    public void setMatchNo(int MatchNo) {
        this.MatchNo = MatchNo;
    }

    public Date getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(Date timeDate) {
        this.timeDate = timeDate;
    }

    public int getP1ID() {
        return p1ID;
    }

    public void setP1ID(int p1ID) {
        this.p1ID = p1ID;
    }

    public int getP2ID() {
        return p2ID;
    }

    public void setP2ID(int p2ID) {
        this.p2ID = p2ID;
    }
    
}
