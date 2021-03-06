package presentation.util.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * Created by Hitiger on 2016/12/7.
 * Description : 服务器连接异常提示框
 */
public class RemoteAlert extends Alert{

    public RemoteAlert(String contentText,String title){
        this(AlertType.ERROR,contentText, ButtonType.OK);
        this.setTitle(title);
        this.setHeaderText("");
        this.initStyle(StageStyle.UTILITY);
    }

    public RemoteAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }
}
