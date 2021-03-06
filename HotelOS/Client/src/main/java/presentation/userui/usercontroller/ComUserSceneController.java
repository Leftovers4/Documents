package presentation.userui.usercontroller;

import bl.hotelbl.HotelBLService;
import bl.hotelbl.impl.HotelBlServiceImpl;
import bl.userbl.UserBLService;
import bl.userbl.impl.UserBlServiceImpl;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import presentation.loginui.loginscene.LoginScene;
import presentation.userui.userscene.*;
import presentation.util.alert.AlertController;
import presentation.util.other.ChangePhoto;
import presentation.util.other.LeftBarEffect;
import util.ResultMessage;
import vo.hotel.HotelVO;
import vo.user.UserVO;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static presentation.util.other.MyTimeLabel.EnableShowTime;

/**
 * Created by Hitiger on 2016/11/19.
 * Description : 客户主界面控制器，负责基本信息、浏览订单、搜索酒店等界面的跳转
 */
public class ComUserSceneController {

    private Stage stage;
    private String userID;

    @FXML private Pane mainPane;
    @FXML private Button userInfoBtn;
    @FXML private Button orderListBtn;
    @FXML private Button searchHotelBtn;
    @FXML private Button hotelRegisteredBtn;
    @FXML private ImageView leftBarSlider;

    @FXML private ImageView topbarphoto;
    @FXML private Label timeLabel;

    @FXML private SplitMenuButton topname;

    private Button currentBtn = null;
    private AlertController alertController;

    //左边栏按钮集合
    private ArrayList<Button> leftBarBtnArr;

    private HotelBLService hotelBLService;
    private UserBLService userBLService;

    LeftBarEffect leftBarEffect = new LeftBarEffect();

    public void launch(Stage primaryStage, String username){
        this.stage = primaryStage;
        this.userID = username;
        topname.setText(username);
        changeSliderPos(260);
        try {
            hotelBLService = new HotelBlServiceImpl();
            userBLService = new UserBlServiceImpl();
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }

        getAllHotelPhoto();

        leftBarBtnArr = new ArrayList<>(Arrays.asList(userInfoBtn, orderListBtn, searchHotelBtn,
                hotelRegisteredBtn));
        alertController = new AlertController();

        currentBtn = userInfoBtn;
        userInfoBtn.setStyle("-fx-background-color: #0F81C7");
        mainPane.getChildren().add(new InfoPane(primaryStage, mainPane, topbarphoto, userID, leftBarBtnArr));

        EnableShowTime(timeLabel);

    }


    @FXML
    private void closeWindow(){
        if(alertController.showConfirmExitAlert()) stage.close();
    }
    @FXML
    private void minWindow(){
        stage.setIconified(true);
    }

    /**
     * 滑块位置改变
     * @param y
     */
    private void changeSliderPos(double y) {
        leftBarSlider.setVisible(true);
        leftBarSlider.setLayoutX(184);
        leftBarSlider.setLayoutY(y);
    }

    /**
     * 鼠标点击按钮效果
     * @param button
     */
    private void leftBarBtnEffect(Button button) {
        leftBarEffect.buttonActionEffect(button, leftBarBtnArr);
    }

    @FXML
    private void userInfo() {
        getAllHotelPhoto();
        changeSliderPos(260);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(new InfoPane(stage, mainPane, topbarphoto, userID, leftBarBtnArr));
        leftBarBtnEffect(userInfoBtn);
        currentBtn = userInfoBtn;
    }
    @FXML
    private void orderList() {
        changeSliderPos(305);

        getAllHotelPhoto();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(new UserOrderListPane(stage, mainPane, userID, false));
        leftBarBtnEffect(orderListBtn);
        currentBtn = orderListBtn;
    }
    @FXML
    private void searchHotel() {
        getAllHotelPhoto();
        changeSliderPos(350);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(new SearchHotelPane(stage, mainPane, userID));
        leftBarBtnEffect(searchHotelBtn);
        currentBtn = searchHotelBtn;
    }
    @FXML
    private void hotelRegistered() {
        getAllHotelPhoto();
        changeSliderPos(395);
        mainPane.getChildren().clear();
        mainPane.getChildren().add(new RegisteredHotelPane(stage, mainPane, userID));
        leftBarBtnEffect(hotelRegisteredBtn);
        currentBtn = hotelRegisteredBtn;
    }


    /**
     * 鼠标悬停按钮效果
     * @param button
     */
    private void mouseOnEffect(Button button) {
        leftBarEffect.buttonMouseOnEffect(button, leftBarBtnArr, currentBtn);
    }
    @FXML
    private void mouseOnUserInfo() {
        mouseOnEffect(userInfoBtn);
    }
    @FXML
    private void mouseOnorderList() {
        mouseOnEffect(orderListBtn);
    }
    @FXML
    private void mouseOnSearchHotel() {
        mouseOnEffect(searchHotelBtn);
    }
    @FXML
    private void mouseOnRegisteredHotel() {
        mouseOnEffect(hotelRegisteredBtn);
    }


    /**
     * 鼠标移出按钮效果
     * @param button
     */
    private void mouseOutEffect(Button button) {
        leftBarEffect.buttonMouseOutEffect(button, currentBtn);
    }
    @FXML
    private void mouseOutUserInfoBtn() {
        mouseOutEffect(userInfoBtn);
    }
    @FXML
    private void mouseOutOrderListBtn() {
        mouseOutEffect(orderListBtn);
    }
    @FXML
    private void mouseOutSearchHotelBtn() {
        mouseOutEffect(searchHotelBtn);
    }
    @FXML
    private void mouseOutHotelRegisteredBtn() {
        mouseOutEffect(hotelRegisteredBtn);
    }


    private void getAllHotelPhoto() {
        String path = "C:/Leftovers/client/user/hotelImg/";
        String userpath = "C:/Leftovers/client/user/userImage/" + userID + "/";
        try {
            List<HotelVO> hotelVOList = hotelBLService.viewFullHotelList();

            try {
                UserVO userVO = userBLService.viewBasicUserInfo(userID);

                if (userVO.image != null) {
                    ChangePhoto.setImage(userpath, userVO.username, userVO.image);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            for (int i = 0; i<hotelVOList.size(); i++) {

                if (hotelVOList.get(i).image != null) {
                    ChangePhoto.setImage(path, hotelVOList.get(i).hotelID, hotelVOList.get(i).image);
                }
            }
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }
    }

    @FXML
    private void logout() {
        ResultMessage resultMessage = userBLService.logout(userID);

        if (resultMessage == ResultMessage.Success) {
            stage.setScene(new LoginScene(new Group(), stage));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } else {
            System.out.println(resultMessage+"=====================================================");
        }
    }
}
