package com.farminator.farminator;

/**
 * Created by Sebastian on 09-Dec-17.
 */

public class Task {
    private String username,description;
    private int id,X,Y;

    public Task(int i,String d,String u,int x,int y){
        id = i;
        username = u;
        description = d;
        X=x;
        Y=y;
    }

    public int getX(){
        return X;}

    public int getY(){
        return Y;
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
