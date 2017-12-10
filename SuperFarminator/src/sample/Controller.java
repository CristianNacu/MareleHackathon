package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Observable;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

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
    RadioButton radioButtonNone;
    @FXML
    RadioButton radioButtonSingle;
    @FXML
    RadioButton radioButtonMultiple;
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

    @FXML
    PieChart pieChart;

    private TileService tileService = new TileService(".\\src\\tiles.txt");
    private TaskService taskService = new TaskService();
    private UserService userService = new UserService(".\\src\\users.txt");
    private RoleService roleService = new RoleService();
    private final int sizeOfFarm = 10;
    private Tile selectedTile = null;
    private ArrayList<Tile> list = tileService.getAllTiles();
    private boolean parity = false;

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

                final ToggleGroup toggleGroup = new ToggleGroup();
                radioButtonNone.setToggleGroup(toggleGroup);
                radioButtonNone.setSelected(true);
                radioButtonSingle.setToggleGroup(toggleGroup);
                radioButtonMultiple.setToggleGroup(toggleGroup);

//                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//
//                        deselectActiveTile();
//
//                        ColorAdjust colorAdjust = new ColorAdjust();
//                        colorAdjust.setSaturation(2);
//
//                        imageView.setEffect(colorAdjust);
//                        double x = imageView.getLayoutX();
//                        double y = imageView.getLayoutY();
//                        int updatedX, updatedY;
//                        updatedX = (int) (x / (gridPane.getWidth() / sizeOfFarm));
//                        updatedY = (int) (y / (gridPane.getHeight() / sizeOfFarm));
//
//                        selectedTile = tileService.getTileByPosition(updatedX, updatedY);
//                        if (checkBoxCreator.isSelected()) {
//                            Image image = imageViewType.getImage();
//                            imageView.setImage(image);
//
//                            selectedTile.setLayoutX((int) updatedX);
//                            selectedTile.setLayoutY((int) updatedY);
//                            selectedTile.setType(choiceBoxType.getValue().toString());
//                            selectedTile.setImage(image);
//                            tileService.update(selectedTile);
//                        }
//                        displayInfo(selectedTile);
//                    }
//                });

                imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (radioButtonNone.isSelected()) {
                            deselectActiveTile();
//
                            ColorAdjust colorAdjust = new ColorAdjust();
                            colorAdjust.setSaturation(2);

                            imageView.setEffect(colorAdjust);
                            double x = imageView.getLayoutX();
                            double y = imageView.getLayoutY();
                            int updatedX, updatedY;
                            updatedX = (int) (x / (gridPane.getWidth() / sizeOfFarm));
                            updatedY = (int) (y / (gridPane.getHeight() / sizeOfFarm));

                            selectedTile = tileService.getTileByPosition(updatedX, updatedY);
                            displayInfo(selectedTile);


                        }
                        if (radioButtonSingle.isSelected()) {
                            deselectActiveTile();
//
                            ColorAdjust colorAdjust = new ColorAdjust();
                            colorAdjust.setSaturation(2);

                            imageView.setEffect(colorAdjust);
                            double x = imageView.getLayoutX();
                            double y = imageView.getLayoutY();
                            int updatedX, updatedY;
                            updatedX = (int) (x / (gridPane.getWidth() / sizeOfFarm));
                            updatedY = (int) (y / (gridPane.getHeight() / sizeOfFarm));

                            selectedTile = tileService.getTileByPosition(updatedX, updatedY);
                            Image image = imageViewType.getImage();
                            imageView.setImage(image);

                            selectedTile.setLayoutX((int) updatedX);
                            selectedTile.setLayoutY((int) updatedY);
                            selectedTile.setType(choiceBoxType.getValue().toString());
                            selectedTile.setImage(image);
                            tileService.update(selectedTile);

                            displayInfo(selectedTile);
                        }


                        if (radioButtonMultiple.isSelected()) {


                            if (parity) {
                                // Par
                                // => Execute updateMutiple

                                int newX = (int) imageView.getLayoutX();
                                int newY = (int) imageView.getLayoutY();
                                int updatedX = (int) (newX / (gridPane.getWidth() / sizeOfFarm));
                                int updatedY = (int) (newY / (gridPane.getHeight() / sizeOfFarm));
                                int top = min((int) selectedTile.getLayoutY(), updatedY);
                                int bottom = max((int) selectedTile.getLayoutY(), updatedY);
                                int left = min((int) selectedTile.getLayoutX(), updatedX);
                                int right = max((int) selectedTile.getLayoutX(), updatedX);

                                updateMultipleTiles(left, right, top, bottom);
                                deselectActiveTile();
                                selectedTile = tileService.getTileByPosition(updatedX, updatedY);

                            } else {
                                // Impar
                                // => Wait for next (select only)
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
                            }
                            parity = !parity;

                            deselectAllTiles();
                        }
                    }
                });

                textFieldPosition.setDisable(true);
                textFieldType.setDisable(true);
            }
        choiceBoxType.setItems(FXCollections.observableArrayList(TileTypes.allTypes));
        imageViewType.setImage(new Image(Main.class.getResourceAsStream("image/" + choiceBoxType.getSelectionModel().getSelectedItem() + ".jpg")));

        choiceBoxType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Image image = new Image(Main.class.getResourceAsStream("image/" + newValue.toString() + ".jpg"));
            imageViewType.setImage(image);
        });
        columnStartDate.setCellValueFactory(new PropertyValueFactory<DisplayableTask, String>("startDate"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<DisplayableTask, String>("description"));
        columnSlave.setCellValueFactory(new PropertyValueFactory<DisplayableTask, String>("slave"));


        ArrayList<DisplayableTask> displayableTasks = new ArrayList<>();
        for (Task task : taskService.getAllTasks()) {
            String slave = "-";
            User user;
            if (task.getUserID() != null) {
                user = userService.getUserByID(task.getUserID());
                slave = user.getSurName() + " " + user.getFirstName();
            }
            DisplayableTask displayableTask = new DisplayableTask(task.getDescription(), task.getStartDate(), slave);
            displayableTasks.add(displayableTask);
        }

        // TODO: Edited here
        updateDiagram();

//        new Thread(new Refresh()).run();

//        ObservableList<PieChart.Data> pieChartData;
//        ArrayList<PieChart.Data> data = new ArrayList<>();
//        if (userService.getUsersStatistics() == null)
//            return;
//        userService.getUsersStatistics().forEach(userStatistic -> {
//            int userID = userStatistic.getIdUser();
//            User user = userService.getUserByID(userID);
//            String name = user.getSurName() + " " + user.getFirstName();
//            data.add(new PieChart.Data(name, userStatistic.getNrTaskuri()));
//        });
//        pieChartData = FXCollections.observableArrayList(data);
//        pieChart.setData(pieChartData);
        tableViewTasks.setItems(FXCollections.observableArrayList(displayableTasks));
    }

    public void handleButtonRefresh(){
        refreshTilesOriginal();
        updateDiagram();
    }

    private void updateDiagram(){
        ObservableList<PieChart.Data> pieChartData;
        ArrayList<PieChart.Data> data = new ArrayList<>();
        if (userService.getUsersStatistics() == null)
            return;
        userService.getUsersStatistics().forEach(userStatistic -> {
            int userID = userStatistic.getIdUser();
            User user = userService.getUserByID(userID);
            String name = user.getSurName() + " " + user.getFirstName();
            data.add(new PieChart.Data(name, userStatistic.getNrTaskuri()));
        });
        pieChartData = FXCollections.observableArrayList(data);
        pieChart.setData(pieChartData);
    }

    private void deselectAllTiles(){
        for (int i = 0; i < sizeOfFarm; i++)
            for (int j = 0; j < sizeOfFarm; j++) {
                ImageView imageView = new ImageView(list.get(i * sizeOfFarm + j).getImage());
                imageView.setEffect(null);
            }

    }

    private void displayInfo(Tile tile) {

        textFieldType.setText(tile.getType());
        String positionText = "" + tile.getLayoutX() + " " + tile.getLayoutY();
        textFieldPosition.setText(positionText);
        tableViewTasks.setItems(FXCollections.observableArrayList(taskService.getTaskByTile(tile)));
    }

    private void updateMultipleTiles(int left, int right, int top, int bottom) {
        for (int i = top; i <= bottom; i++) {
            for (int j = left; j <= right; j++) {
                selectedTile = tileService.getTileByPosition(j, i);
                Image image = imageViewType.getImage();
                int index = (int) selectedTile.getLayoutX() * sizeOfFarm + (int) selectedTile.getLayoutY();
                ImageView view = (ImageView) gridPane.getChildren().get(index);
                view.setImage(image);
                selectedTile.setLayoutX((int) j);
                selectedTile.setLayoutY((int) i);
                selectedTile.setType(choiceBoxType.getValue().toString());
                selectedTile.setImage(image);
                tileService.update(selectedTile);
                displayInfo(selectedTile);
            }
        }
    }

    public void handleAddTask(MouseEvent event) {
        if (selectedTile != null) {
            showAddTask(selectedTile);
            updateDiagram();
            deselectActiveTile();
        }
    }

    private void showAddTask(Tile tile) {
        try {
            // create a new stage for the popup dialog.
            // TODO: here
            tableViewTasks.setItems(FXCollections.observableArrayList(taskService.getAllTasks()));

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addTask.fxml"));
            Pane root = (Pane) loader.load();
            // Create the dialog Stage.
            Stage stage = new Stage();
            stage.setTitle("Adaugare Task");
            stage.initModality(Modality.WINDOW_MODAL);
            AddTaskController addTaskController = loader.getController();
            addTaskController.setTaskService(this.taskService);
            addTaskController.setThisStage(stage);
            addTaskController.setUserService(this.userService);
            addTaskController.setTile(tile);
//            dialogStage.initOwner(primaryStage);
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
            AddUserController addTaskController = loader.getController();
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

    public void deselectActiveTile() {
        if (selectedTile != null) {
            int index = (int) selectedTile.getLayoutX() * sizeOfFarm + (int) selectedTile.getLayoutY();
            ImageView view = (ImageView) gridPane.getChildren().get(index);
            view.setEffect(null);
            textFieldType.setText("");
            textFieldPosition.setText("");

            ArrayList<DisplayableTask> displayableTasks = new ArrayList<>();
            // RefreshHere
            taskService.importUnfinishedTasksFromDB();
            for (Task task : taskService.getAllTasks()) {
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
        refreshTilesOriginal();
        selectedTile = null;
    }

    public void refreshTilesOriginal(){

        ArrayList<DisplayableTask> displayableTasks = new ArrayList<>();
        taskService.importUnfinishedTasksFromDB();
        for (Task task : taskService.getAllTasks()) {
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

    public void refreshTiles(){
    }

    public class Refresh implements Runnable {
        @Override
        public void run(){
            while(true){
                try {
                    updateDiagram();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
