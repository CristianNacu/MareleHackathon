package com.farminator.farminator;

/**
 * Created by Sebastian on 09-Dec-17.
 */

public class Task {
    private String username,description;
    private int id;

    public Task(int i,String d,String u){
        id = i;
        username = u;
        description = d;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public String getDescription(){
        return description;
    }

    public void setUsername(String u){
        username = u;
    }
}
