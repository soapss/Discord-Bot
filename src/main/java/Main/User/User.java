package Main.User;

import java.time.LocalDateTime;

//customized user class
public class User {

    //time to calculate from for the dailytime and reptime (basically as a reference point for time data stored)
    private final LocalDateTime DEFAULT_TIME = LocalDateTime.of(2000,1,1,0,0);
    //user info
    private String userID;
    private long exp = 0;
    private long econ = 0;
    private long rep = 0;
    private LocalDateTime dailyTime = DEFAULT_TIME;
    private LocalDateTime repTime = DEFAULT_TIME;

    //user object identified by id
    public User(String userID){
        this.setUserID(userID);
    }

    //get exp
    public long getExp(){
        return exp;
    }

    //add exp
    public void setExp(long e){
        exp += e;
    }

    //get econ aka currency
    public long getEcon(){
        return econ;
    }

    //add econ
    public void setEcon(long cred){
        econ += cred;
    }

    //get rep points
    public long getRep(){
        return rep;
    }

    //set rep points
    public void setRep(long r){
        rep += r;
    }

    //get user id
    public String getUserID() {
        return userID;
    }

    //set user id
    public void setUserID(String userID) {
        this.userID = userID;
    }

    //set last ~daily time
    public void setDailyTime(LocalDateTime dailyTime) {
        this.dailyTime = dailyTime;
    }

    //get last ~daily time
    public LocalDateTime getDailyTime() {
        return dailyTime;
    }

    //set last ~rep time
    public void setRepTime(LocalDateTime repTime) {
        this.repTime = repTime;
    }

    //get last ~rep time
    public LocalDateTime getRepTime() {
        return repTime;
    }
}
