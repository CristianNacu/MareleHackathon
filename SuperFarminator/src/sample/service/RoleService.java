package sample.service;

import javafx.scene.image.Image;
import sample.Main;
import sample.model.Role;
import sample.model.Tile;
import sample.utils.ClientTcp;
import sample.utils.ImageUrls;

import java.io.IOException;
import java.util.ArrayList;

public class RoleService {
    private ArrayList<Role> list = new ArrayList<>();
    public RoleService() {
        importRolesFromDB();
    }

    private void importRolesFromDB() {
        try {
            String result = ClientTcp.makeRequest(ClientTcp.requestCodes.get("AllRoles"));
            String[] cimpanzei = result.split(";");
            for (int i = 0; i < cimpanzei.length; i++) {
                int id=Integer.parseInt(cimpanzei[i++]);
                String nume=cimpanzei[i++];
                String desc=cimpanzei[i++];
                Role role=new Role(id,nume,desc);
                list.add(role);
                i--;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final ArrayList<Role> getAllRoles() {
        return list;
    }
}
