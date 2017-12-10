package sample.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class TileTypes {
    public static HashSet<String> allTypes=getTypesFromDB();
    public static HashSet<String> getTypesFromDB(){
        HashSet<String> resultSet=new HashSet<>();
        resultSet.add("iarba");
        try {
            String types = ClientTcp.makeRequest(ClientTcp.requestCodes.get("AllTileTypes"));
            String[] allTypes=types.split(";");
            for(int i=0;i<allTypes.length;i++)
                resultSet.add(allTypes[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
