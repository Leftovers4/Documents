package presentation.userui.userscene;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.userui.usercontroller.RegisterCompanyVIPController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wyj on 2016/11/25.
 */
public class RegisterCompanyVIPPane extends Pane {

    public RegisterCompanyVIPPane(Stage primaryStage, Pane mainPane, String userID, ImageView topbarphoto, ArrayList<Button> leftBarArr) {
        loadFxml(primaryStage, mainPane, userID, topbarphoto, leftBarArr);
    }

    private void loadFxml(Stage primaryStage, Pane mainPane, String userID, ImageView topbarphoto, ArrayList<Button> leftBarArr) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user/registercompanyvip.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RegisterCompanyVIPController registerCompanyVIPController = fxmlLoader.getController();
        registerCompanyVIPController.launch(primaryStage, mainPane, userID, topbarphoto, leftBarArr);
    }
}
