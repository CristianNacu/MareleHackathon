package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Task;
import sample.model.Tile;
import sample.model.User;
import sample.service.RoleService;
import sample.service.TaskService;
import sample.service.UserService;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

public class AddUserController {
    @FXML
    TextField textFieldSurName;
    @FXML
    TextField textFieldFirstName;
    @FXML
    TextField textFieldUserName;
    @FXML
    PasswordField textFieldPassword;
    @FXML
    ChoiceBox choiceBox;

    private Stage thisStage;

    private UserService userService;
    private RoleService roleService;
    private Tile tile;

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
        choiceBox.setItems(FXCollections.observableArrayList(roleService.getAllRoles()));
    }

    public void setThisStage(Stage stage){this.thisStage=stage;}

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }


    public void initialize(){

    }

    public void handleAddUser(MouseEvent event){
        String nume=textFieldSurName.getText();
        String prenume=textFieldFirstName.getText();
        String userName=textFieldUserName.getText();
        String password=textFieldPassword.getText();
        sample.model.Role role=(sample.model.Role)choiceBox.getValue();
        User user=new User(-1,prenume,nume,userName,password,role.getId());
        userService.add(user);
        thisStage.close();
    }
}
