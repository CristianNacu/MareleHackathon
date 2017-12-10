package sample.service;

import javafx.scene.image.Image;
import sample.Main;
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
import java.util.List;
import java.util.stream.Stream;

public class TileService {
    private ArrayList<Tile> list = new ArrayList<>();
    private String fileName;
    private int tileSize = 0;

    public TileService(String fileName) {
        this.fileName = fileName;
        importTilesFromDB();
    }

    public int getTileSize() {
        return tileSize;
    }

    private void importTilesFromDB() {
        try {
            String result = ClientTcp.makeRequest(ClientTcp.requestCodes.get("AllTiles"));
            if (result == null)
                return;
            String[] cimpanzei = result.split(";");
            for (int i = 0; i < cimpanzei.length; i++) {
               double x=Double.parseDouble(cimpanzei[i++]);
               double y=Double.parseDouble(cimpanzei[i++]);
               String type=cimpanzei[i++];
               Image image=new Image(Main.class.getResourceAsStream(ImageUrls.getUrl(type)));
               int id=Integer.parseInt(cimpanzei[i++]);
               Tile tile=new Tile(id,image,x,y,type);
               list.add(tile);
               i--;
               tileSize++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Tile tile) {
        for (Tile parcela : list)
            if (parcela.equals(tile)) {
                parcela.setImage(tile.getImage());
                parcela.setType(tile.getType());
                try {
                    ClientTcp.makeRequest(ClientTcp.requestCodes.get("UpdateTile")+"|"+tile.getId()+"|"+tile.getType());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
    }


    public Tile getTileByPosition(double layoutX, double layoutY) {
        for (Tile parcela : list)
            if (parcela.getLayoutX() == layoutX && parcela.getLayoutY() == layoutY)
                return parcela;
        return null;
    }


    public final ArrayList<Tile> getAllTiles() {
        return list;
    }

}