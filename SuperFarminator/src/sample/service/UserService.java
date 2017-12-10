package sample.service;

import sample.model.Task;
import sample.model.User;
import sample.model.Tile;
import sample.utils.ClientTcp;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class UserService {
    private ArrayList<User> list = new ArrayList<>();
    private String fileName;

    public UserService(String fileName) {
        this.fileName = fileName;
        importUsersFromDB();
    }

    public void importUsersFromDB(){
        try {
            String result= ClientTcp.makeRequest(ClientTcp.requestCodes.get("AllUsers"));
            String[] tokens=result.split(";");
            list = parseTokens(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<User> parseTokens(String[] tokens){
        ArrayList<User> list = new ArrayList<>();
        for(int i=0;i<tokens.length;i++){
            int id=Integer.parseInt(tokens[i++]);
            String nume=tokens[i++];
            String prenume=tokens[i++];
            int role=Integer.parseInt(tokens[i++]);
            User user=new User(id,prenume,nume,"","",role);
            i--;
            list.add(user);
        }
        return list;
    }

    // TODO: getUserByID();
    public User getUserByID(int id){
        for (User user: list)
            if (user.getId() == id)
                return user;
        return null;
    }

    public void add(User user){
        list.add(user);
        try {
            ClientTcp.makeRequest(ClientTcp.requestCodes.get("AddUser")+"|"
                    +user.getSurName()+"|"
                    +user.getFirstName()+"|"
                    +user.getUserName()+"|"
                    +user.getPassword()+"|"
                    +user.getIdRol());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public final ArrayList<User> getAllUsers(){
        return list;
    }
}
