package com.farminator.farminator.utils;

import android.util.Log;

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
        try {
        String a[] = out.split(";");

            for (int i = 0; i < a.length; i += 2) {
                l.add(new Task(Integer.parseInt(a[i]), a[i + 1], username));
            }
        }
        catch (NumberFormatException e){
            return new ArrayList<Task>();
        }
        catch (NullPointerException e){
            return new ArrayList<Task>();
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
        try {
        String a[] = out.split(";");

            for (int i = 0; i < a.length; i += 2) {
                l.add(new Task(Integer.parseInt(a[i]), a[i + 1], ""));
            }
        }
        catch (NumberFormatException e){
            return new ArrayList<Task>();
        }
        catch (NullPointerException e){
            return new ArrayList<Task>();
        }

        return l;
    }

    public static void completeTask(int id){
        String out=null;
        try {
            out = new ServerRequest().execute("5|5|"+id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void claimTask(int id, String username){
        String out=null;
        try {
            out = new ServerRequest().execute("4|4|"+id+"|"+username).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void undo(int id){
        String out=null;
        try {
            out = new ServerRequest().execute("7|7|"+id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void reset(int id){
        String out=null;
        try {
            out = new ServerRequest().execute("6|6|-1").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
