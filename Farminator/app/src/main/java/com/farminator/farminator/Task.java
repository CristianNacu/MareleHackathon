package com.farminator.farminator;

/**
 * Created by Sebastian on 09-Dec-17.
 */

public class Task {
    private String username,description;

    public Task(String d,String u){
        username = u;
        description = d;
    }

    public String getUsername(){
        return username;
    }

    public String getDescription(){
        return description;
    }
}
