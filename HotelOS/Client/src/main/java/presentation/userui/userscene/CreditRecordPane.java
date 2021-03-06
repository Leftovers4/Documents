package presentation.userui.userscene;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.userui.usercontroller.CreditRecordController;

import java.io.IOException;

/**
 * Created by wyj on 2016/11/25.
 * Description: 查看信用历史记录
 */
public class CreditRecordPane extends Pane {

    public CreditRecordPane(Stage primaryStage, Pane mainPane, String userID) {
        loadFxml(primaryStage, mainPane, userID);
    }

    private void loadFxml(Stage primaryStage, Pane mainPane, String userID) {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/user/creditrecord.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CreditRecordController creditRecordController = fxmlLoader.getController();
        creditRecordController.launch(primaryStage, mainPane, userID);
    }
}
