package com.farminator.farminator.utils;

import com.farminator.farminator.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sebastian on 09-Dec-17.
 */

public final class Utils {
    private static final Map<String, String> USER_DATA = new HashMap<String, String>();
    private static final List<Task> TASKS = new ArrayList<Task>();

    static{
        USER_DATA.put("gigel", "gigel");
        USER_DATA.put("ramon", "ramon");

        TASKS.add(new Task("Water the plants","gigel"));
        TASKS.add(new Task("Water the flowers","ramon"));
        TASKS.add(new Task("Plant something","gigel"));
        TASKS.add(new Task("Water the plants","ramon"));
        TASKS.add(new Task("Free task 1",""));
        TASKS.add(new Task("Free task 2",""));
        TASKS.add(new Task("Free task 3",""));
        TASKS.add(new Task("Free task 4",""));
    }

    public static boolean checkUser(String username,String password){
        if(username.isEmpty() || password.isEmpty()){
            return false;
        }

        if(!USER_DATA.containsKey(username) || !(USER_DATA.get(username).equals(password))){
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
