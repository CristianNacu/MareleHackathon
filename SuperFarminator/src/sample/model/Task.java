package sample.model;

import javafx.util.Pair;
import sample.utils.IDGenerator;

public class Task {
    private int id;
    private String description;
    private String startDate;
    private String endDate;
    private int state;


    private int idTile;
    private Integer userID;

    public Task(int id,String d, String sd, String ed, int s, int idTile, Integer uid){
        this.id = id;
        description = d;
        startDate = sd;
        endDate = ed;
        state = s;
        this.idTile=idTile;
        userID = uid;
    }

    public int getIdTile() {
        return idTile;
    }

    public void setIdTile(int idTile) {
        this.idTile = idTile;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getState() {
        return state;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getUserID() {

        return userID;
    }
}
