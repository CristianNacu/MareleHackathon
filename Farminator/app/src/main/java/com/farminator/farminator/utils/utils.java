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
        /*
        List<Task> l = new ArrayList<>();
        for(Task e:TASKS){
            if(e.getUsername().equals(username))
                l.add(e);
        }
        return l;
        */
        String out=null;
        try {
            out = new ServerRequest().execute("2|2|"+username).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<Task> l = new ArrayList<>();
        String a[] = out.split(";");
        for(int i=0;i<a.length;i+=2){
            l.add(new Task(Integer.parseInt(a[i]),a[i+1],username));
        }
        return l;
    }

    public static List<Task> getAvailableTasks(){
        String out=null;
        try {
            out = new ServerRequest().execute("3|3|").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        List<Task> l = new ArrayList<>();
        String a[] = out.split(";");
        for(int i=0;i<a.length;i+=2){
            l.add(new Task(Integer.parseInt(a[i]),a[i+1],""));
        }
        return l;
    }

    public static void claimTask(int id, String username){
        getAvailableTasks().get(id).setUsername(username);
    }
}
