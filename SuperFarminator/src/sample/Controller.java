package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import sample.model.Tile;

import java.util.ArrayList;

public class Controller {
    @FXML
    GridPane gridPane;
    @FXML
    ChoiceBox choiceBoxType;
    @FXML
    ImageView imageViewType;
    @FXML
    CheckBox checkBoxCreator;

    private final int sizeOfFarm=10;
    Tile[] tiles=new Tile[sizeOfFarm*sizeOfFarm];

    public Controller() {
    }

    @FXML
    public void initialize() {
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        Image image = new Image(Main.class.getResourceAsStream("image/iarba.jpg"));
        for (int i = 0; i < sizeOfFarm*sizeOfFarm; i++) {
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            int imageCol = i % 10;
            int imageRow = i / 10;
            GridPane.setConstraints(imageView, imageCol, imageRow);
            gridPane.getChildren().add(imageView);
            tiles[i] = new Tile(image, gridPane.getChildren().get(i).getLayoutX(),gridPane.getChildren().get(i).getLayoutY());
        }
        ArrayList<String> typeList = new ArrayList<>();
        typeList.add("iarba");
        typeList.add("porumb");
        choiceBoxType.setItems(FXCollections.observableArrayList(typeList));
        imageViewType.setImage(new Image(Main.class.getResourceAsStream("image/" + choiceBoxType.getSelectionModel().getSelectedItem() + ".jpg")));
        choiceBoxType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            imageViewType.setImage(new Image(Main.class.getResourceAsStream("image/" + newValue.toString() + ".jpg")));
        });
    }

    public void handleGridClick(MouseEvent event) {
        double imageX=gridPane.getScaleX()/sizeOfFarm;
        double colIndex = event.getX();
        double rowIndex=event.getY();
    }
}
