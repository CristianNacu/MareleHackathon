package sample;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.model.DisplayableTask;
import sample.model.Task;
import sample.model.Tile;
import sample.model.User;
import sample.service.RoleService;
import sample.service.TaskService;
import sample.service.TileService;
import sample.service.UserService;
import sample.utils.ClientTcp;
import sample.utils.TileTypes;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Controller {
    @FXML
    GridPane gridPane;
    @FXML
    ChoiceBox choiceBoxType;
    @FXML
    ImageView imageViewType;
    @FXML
    CheckBox checkBoxCreator;
    @FXML
    TextField textFieldType;
    @FXML
    TextField textFieldPosition;
    @FXML
    TableView tableViewTasks;
    @FXML
    TableColumn columnSlave;
    @FXML
    TableColumn columnDescription;
    @FXML
    TableColumn columnStartDate;

    private TileService tileService = new TileService(".\\src\\tiles.txt");
    private TaskService taskService = new TaskService();
    private UserService userService = new UserService(".\\src\\users.txt");
    private RoleService roleService=new RoleService();
    private final int sizeOfFarm = 10;
    private Tile selectedTile = null;
    private ArrayList<Tile> list = tileService.getAllTiles();

    public Controller() {
    }

    public void initialize() {

        gridPane.setHgap(1);
        gridPane.setVgap(1);
        for (int i = 0; i < sizeOfFarm; i++)
            for (int j = 0; j < sizeOfFarm; j++) {
                ImageView imageView = new ImageView(list.get(i * sizeOfFarm + j).getImage());
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                GridPane.setConstraints(imageView, i, j);
                gridPane.getChildren().add(imageView);

                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        deselectActiveTile();

                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setSaturation(2);

                        imageView.setEffect(colorAdjust);
                        double x = imageView.getLayoutX();
                        double y = imageView.getLayoutY();
                        int updatedX, updatedY;
                        updatedX = (int) (x / (gridPane.getWidth() / sizeOfFarm));
                        updatedY = (int) (y / (gridPane.getHeight() / sizeOfFarm));

                        selectedTile = tileService.getTileByPosition(updatedX, updatedY);
                        if (checkBoxCreator.isSelected()) {
                            Image image = imageViewType.getImage();
                            imageView.setImage(image);

                            selectedTile.setLayoutX((int) updatedX);
                            selectedTile.setLayoutY((int) updatedY);
                            selectedTile.setType(choiceBoxType.getValue().toString());
                            selectedTile.setImage(image);
                            tileService.update(selectedTile);
                        }
                        displayInfo(selectedTile);
                    }
                });
            textFieldPosition.setDisable(true);
            textFieldType.setDisable(true);
            }
        choiceBoxType.setItems(FXCollections.observableArrayList(TileTypes.allTypes));
        imageViewType.setImage(new Image(Main.class.getResourceAsStream("image/" + choiceBoxType.getSelectionModel().getSelectedItem() + ".jpg")));
        choiceBoxType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            imageViewType.setImage(new Image(Main.class.getResourceAsStream("image/" + newValue.toString() + ".jpg")));
        });
        columnStartDate.setCellValueFactory(new PropertyValueFactory<DisplayableTask, String>("startDate"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<DisplayableTask, String>("description"));
        columnSlave.setCellValueFactory(new PropertyValueFactory<DisplayableTask, String>("slave"));


        ArrayList<DisplayableTask> displayableTasks = new ArrayList<>();
        for (Task task: taskService.getAllTasks()) {
            String slave = "-";
            User user;
            if (task.getUserID() != null) {
                user = userService.getUserByID(task.getUserID());
                slave = user.getSurName() + " " + user.getFirstName();
            }
            DisplayableTask displayableTask = new DisplayableTask(task.getDescription(), task.getStartDate(), slave);
            displayableTasks.add(displayableTask);
        }
//        tableViewTasks.setItems(FXCollections.observableArrayList(taskService.getAllTasks()));
        tableViewTasks.setItems(FXCollections.observableArrayList(displayableTasks));
    }


    private void displayInfo(Tile tile) {

        textFieldType.setText(tile.getType());
        String positionText = "" + tile.getLayoutX() + " " + tile.getLayoutY();
        textFieldPosition.setText(positionText);
        tableViewTasks.setItems(FXCollections.observableArrayList(taskService.getTaskByTile(tile)));
    }

    public void handleAddTask(MouseEvent event) {
        if(selectedTile!=null)
            showAddTask(selectedTile);
        deselectActiveTile();
    }
    private void showAddTask(Tile tile) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addTask.fxml"));
            Pane root = (Pane) loader.load();
            // Create the dialog Stage.
            Stage stage = new Stage();
            stage.setTitle("Adaugare Task");
            stage.initModality(Modality.WINDOW_MODAL);
            AddTaskController addTaskController=loader.getController();
            addTaskController.setTaskService(this.taskService);
            addTaskController.setThisStage(stage);
            addTaskController.setUserService(this.userService);
            addTaskController.setTile(tile);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddUser(MouseEvent event) {
        try {
            // create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addUser.fxml"));
            Pane root = (Pane) loader.load();
            // Create the dialog Stage.
            Stage stage = new Stage();
            stage.setTitle("Adaugare Task");
            stage.initModality(Modality.WINDOW_MODAL);
            AddUserController addTaskController=loader.getController();
            addTaskController.setThisStage(stage);
            addTaskController.setUserService(this.userService);
            addTaskController.setRoleService(this.roleService);
            //dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deselectActiveTile(){
        if (selectedTile != null) {
            int index = (int) selectedTile.getLayoutX() * sizeOfFarm + (int) selectedTile.getLayoutY();
            ImageView view = (ImageView) gridPane.getChildren().get(index);
            view.setEffect(null);
            textFieldType.setText("");
            textFieldPosition.setText("");

            ArrayList<DisplayableTask> displayableTasks = new ArrayList<>();
            for (Task task: taskService.getAllTasks()) {
                String slave = "-";
                User user;
                if (task.getUserID() != null) {
                    user = userService.getUserByID(task.getUserID());
                    slave = user.getSurName() + " " + user.getFirstName();
                }
                DisplayableTask displayableTask = new DisplayableTask(task.getDescription(), task.getStartDate(), slave);
                displayableTasks.add(displayableTask);
            }
//            tableViewTasks.setItems(FXCollections.observableArrayList(taskService.getAllTasks()));
            tableViewTasks.setItems(FXCollections.observableArrayList(displayableTasks));
        }

    }

    public void handleClick(MouseEvent event) {
        deselectActiveTile();
        selectedTile = null;
    }
}
