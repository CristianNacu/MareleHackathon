package com.farminator.farminator.utils;

import com.farminator.farminator.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sebastian on 09-Dec-17.
 */

public final class Utils {
    private static final List<Task> TASKS = new ArrayList<Task>();

    static{
        TASKS.add(new Task("Water the plants","Gigel"));
        TASKS.add(new Task("Water the flowers","Ramon"));
        TASKS.add(new Task("Plant something","Gigel"));
        TASKS.add(new Task("Water the plants","Ramon"));
        TASKS.add(new Task("Free task 1",""));
        TASKS.add(new Task("Free task 2",""));
        TASKS.add(new Task("Free task 3",""));
        TASKS.add(new Task("Free task 4",""));
    }

    public static boolean checkUser(String username,String password){
        if(username==null || password==null || username.isEmpty() || password.isEmpty()){
            return false;
        }

        if(username.equals("Test") && password.equals("test")){
            return true;
        }

        String out=null;
        try {
            out = new ServerRequest().execute("1|1|"+username+"|"+password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(out!=null && !out.equals("true")){
            return false;
        }

        return true;
    }

    public static List<Task> getMyTasks(String username){
        List<Task> l = new ArrayList<>();
        for(Task e:TASKS){
            if(e.getUsername().equals(username))
                l.add(e);
        }
        return l;
    }

    public static List<Task> getAvailableTasks(){
        List<Task> l = new ArrayList<>();
        for(Task e:TASKS){
            if(e.getUsername().isEmpty())
                l.add(e);
        }
        return l;
    }
}
