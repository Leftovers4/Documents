package presentation.hotelworkerui.hotelworkerscene;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentation.hotelworkerui.hotelworkercontroller.UpdateOfflinePaneController;

import java.io.IOException;

/**
 * Created by Hitiger on 2016/11/23.
 * Description :
 */
public class UpdateOfflinePane extends Pane{

    public UpdateOfflinePane(Pane mainPane, Boolean isCheckIn) {
        loadFxml(mainPane,isCheckIn);
    }

    private void loadFxml(Pane mainPane, Boolean isCheckIn) {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/fxml/hotelworker/hoteloffline.fxml"));
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateOfflinePaneController updateOfflinePaneController = fxmlLoader.getController();
        updateOfflinePaneController.launch(mainPane,isCheckIn);
    }
}
