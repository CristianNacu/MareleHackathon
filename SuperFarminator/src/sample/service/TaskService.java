package sample.service;

import javafx.scene.image.Image;
import javafx.util.Pair;
import sample.Main;
import sample.model.Task;
import sample.model.Task;
import sample.model.Tile;
import sample.utils.ClientTcp;
import sample.utils.ImageUrls;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TaskService {
    private ArrayList<Task> list = new ArrayList<>();
    public TaskService() {
        importUnfinishedTasksFromDB();
    }

    public void importUnfinishedTasksFromDB() {
        try {
            String result=ClientTcp.makeRequest(ClientTcp.requestCodes.get("UnfinishedTasks"));
            if (result==null)
                return;
            String[] tokens=mySplit(result);
            this.list = parseTokens(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Task> parseTokens(String[] tokens){
        ArrayList<Task> list = new ArrayList<>();
        for(int i=0;i<tokens.length;i= i+6){
            try {
                int id = Integer.parseInt(tokens[i]);
                String desc = tokens[i + 1];
                String sdate = tokens[i + 2];
                Integer idUser = null;
                if (!tokens[i + 3].equals(""))
                    idUser = Integer.parseInt(tokens[i + 3]);
                int state = 1;
                String edate = null;
                int idParcela = Integer.parseInt(tokens[i + 5]);
                Task task = new Task(id, desc, sdate, edate, state, idParcela, idUser);
                list.add(task);
            }
            catch(Exception e){}
        }
        return list;
    }


    public ArrayList<Task> getTaskByTile(Tile tile){
        ArrayList<Task> resultList=new ArrayList<>();
        try {
            String result=ClientTcp.makeRequest(ClientTcp.requestCodes.get("UnfinishedTasksOnTile")+"|"+tile.getId());
            if(result==null)
                return resultList;
            String[] tokens=mySplit(result);
            resultList = parseTokens(tokens);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public String[] mySplit(String r){
        return r.split(";", -1);
    }

    public void add(Task task){
        list.add(task);//World|2016-01-11|15|4")
        try {
            ClientTcp.makeRequest(ClientTcp.requestCodes.get("AddTask")+"|"+task.getDescription()+"|"
                    +task.getStartDate()+"|"+task.getUserID());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public final ArrayList<Task> getAllTasks(){
        return list;
    }

}