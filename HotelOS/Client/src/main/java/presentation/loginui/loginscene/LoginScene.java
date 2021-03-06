package presentation.loginui.loginscene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.loginui.logincontroller.LoginSceneController;
import presentation.util.other.MyWindow;

import java.io.IOException;

public class LoginScene extends Scene {

    public LoginScene(Parent parent,Stage primaryStage) {
        super(parent);
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/login/loginscene.fxml"));
        Parent root= null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setRoot(root);

        //配置控制器
        LoginSceneController loginSceneController = fxmlLoader.getController();
        loginSceneController.setStage(primaryStage);

        //实现窗口可拖动
        MyWindow.enableWindowDrag(root, primaryStage);
    }

}
