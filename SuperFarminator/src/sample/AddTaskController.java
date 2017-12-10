package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Task;
import sample.model.Tile;
import sample.model.User;
import sample.service.TaskService;
import sample.service.UserService;

import java.time.LocalDate;

public class AddTaskController {
    @FXML
    TableView tableView;
    @FXML
    TableColumn columnName;
    @FXML
    TableColumn columnFirstName;
    @FXML
    TextArea textArea;

    private Stage thisStage;

    public void setThisStage(Stage stage){this.thisStage=stage;}

    private TaskService taskService;

    private UserService userService;
    private Tile tile;


    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
        tableView.setItems(FXCollections.observableArrayList(userService.getAllUsers()));
    }

    public void initialize(){
        columnName.setCellValueFactory(new PropertyValueFactory<User,String>("surName"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<User,String>("firstName"));
    }

    public void handleAddTask(MouseEvent event){
        String descriere=textArea.getText();
        double x=tile.getLayoutX();
        double y=tile.getLayoutY();
        String date= LocalDate.now().toString();
        User user=(User)tableView.getSelectionModel().getSelectedItem();
        Task task=new Task(-1,descriere,date,null,0,tile.getId(),null);
        if(user!=null)
            task.setUserID(user.getId());
        taskService.add(task);
        thisStage.close();
    }
}
