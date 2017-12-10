package sample.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class ClientTcp {
    public static HashMap<String, String> requestCodes = initializeCodes();

    private static HashMap<String, String> initializeCodes(){
        HashMap<String, String> codes = new HashMap<>();
        codes.put("UnfinishedTasks", "100|100");
        codes.put("UnfinishedTasksOnTile", "101|101");//idTile
        codes.put("AllTileTypes", "102|102");
        codes.put("AllTiles","103|103");
        codes.put("UpdateTile","120|120");//id,type
        codes.put("AddUser","110|110");//nume,prenume,username,parola,idRole
        codes.put("AddTask","111|111");//descr,dataStart,idParcela,idMuncitor
        codes.put("AllUsers","104|104");
        codes.put("AllRoles","105|105");
        codes.put("UserStatistics","130|130");
        return codes;
    }

    public static String makeRequest(String s) throws IOException
    {
        Socket clientSocket = new Socket();
        SocketAddress sockaddr = new InetSocketAddress("192.168.43.62", 4300);
        clientSocket.connect(sockaddr);
        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
        output.writeUTF(s);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String answer = inFromServer.readLine();
        output.close();
        inFromServer.close();
        clientSocket.close();
        return  answer;

    }

}
