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

    private void importUnfinishedTasksFromDB() {
        try {
            String result=ClientTcp.makeRequest(ClientTcp.requestCodes.get("UnfinishedTasks"));
            String[] tokens=result.split(";");
            list = parseTokens(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Task> parseTokens(String[] tokens){
        ArrayList<Task> list = new ArrayList<>();
        for(int i=0;i<tokens.length;i++){
            int id=Integer.parseInt(tokens[i++]);
            String desc=tokens[i++];
            String sdate=tokens[i++];
            String edate=null;
            Integer idUser=null;
            if(!tokens[i++].equals(""))
                idUser=Integer.parseInt(tokens[i-1]);
            int state=1; i++;
            int idParcela=Integer.parseInt(tokens[i++]);
            Task task= new Task(id,desc,sdate,edate,state,idParcela,idUser);
            i--;
            list.add(task);
        }
        return list;
    }


    public ArrayList<Task> getTaskByTile(Tile tile){
        ArrayList<Task> resultList=new ArrayList<>();
        try {
            String result=ClientTcp.makeRequest(ClientTcp.requestCodes.get("UnfinishedTasksOnTile")+"|"+tile.getId());
            if(result==null)
                return resultList;
            String[] tokens=result.split(";");
            resultList = parseTokens(tokens);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultList;
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