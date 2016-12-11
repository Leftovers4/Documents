package presentation.webmanagerui.webmanagercontroller;

import bl.personnelbl.impl.PersonnelBLServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import presentation.webmanagerui.webmanagerscene.HotelworkerManagePane;
import util.PersonnelType;
import util.ResultMessage;
import vo.hotel.HotelVO;
import vo.personnel.PersonnelVO;

import java.rmi.RemoteException;

/**
 * Created by wyj on 2016/11/29.
 */
public class HotelworkerManageController {

    private Stage stage;
    private Pane pane;

    @FXML private TableView hotelworkerList;
    @FXML private TableColumn hotelIDCol;
    @FXML private TableColumn hotelworkerIDCol;
    @FXML private TableColumn hotelworkerNameCol;
    @FXML private TableColumn btnCol;

    @FXML private Pane modifyhotelworkerPane;
    @FXML private TextField workernameField;
    @FXML private Button confirmBtn;
    @FXML private Button cancelBtn;

    private PersonnelBLServiceImpl personnelBLService;
    private WebManHotelworkerButtonCell webManHotelworkerButtonCell;

    public void launch(Stage primaryStage, Pane mainPane) {
        this.stage = primaryStage;
        this.pane = mainPane;

        try {
            personnelBLService = new PersonnelBLServiceImpl();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        initialData();
    }


    private void initialData() {
        hotelIDCol.setCellValueFactory(new PropertyValueFactory<>("hotelID"));
        hotelworkerIDCol.setCellValueFactory(new PropertyValueFactory<>("personnelID"));
        hotelworkerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        btnCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                webManHotelworkerButtonCell = new WebManHotelworkerButtonCell();
                return webManHotelworkerButtonCell;
            }
        });
        hotelworkerList.setItems(getHotelWorkerList());
    }
    private ObservableList getHotelWorkerList() {
        ObservableList<PersonnelVO> list = null;
        try {
            list = FXCollections.observableArrayList(personnelBLService.viewTypePersonnelList(PersonnelType.HotelWorker));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return list;
    }



    /**
     * Created by wyj on 2016/12/7.
     * Description: 网站管理人员工具类---酒店工作人员列表按钮
     */
    private class WebManHotelworkerButtonCell extends TableCell<HotelVO, Boolean> {

        final private HBox btnBox = new HBox();
        final private Button editBtn = new Button();
        final private Button deleteBtn = new Button();
        private int selectedIndex;

        public WebManHotelworkerButtonCell() {

            Image editImg = new Image("/img/webmanager/edit.png");
            ImageView editimgview = new ImageView(editImg);
            editimgview.setFitHeight(20);
            editimgview.setFitWidth(20);
            editBtn.setGraphic(editimgview);
            editBtn.getStyleClass().add("tableCellBtn");
            Image deleteImg = new Image("/img/webmanager/delete.png");
            ImageView deleteimgview = new ImageView(deleteImg);
            deleteimgview.setFitWidth(20);
            deleteimgview.setFitHeight(20);
            deleteBtn.setGraphic(deleteimgview);
            deleteBtn.getStyleClass().add("tableCellBtn");

            editBtn.setOnAction(event -> {
                selectedIndex = getTableRow().getIndex();

                hotelworkerList.setPrefHeight(300);
                modifyhotelworkerPane.setVisible(true);
            });

            deleteBtn.setOnAction(event -> {
                selectedIndex = getTableRow().getIndex();

                PersonnelVO personnelVO = (PersonnelVO) hotelworkerList.getItems().get(selectedIndex);

                try {
                    ResultMessage resultMessage = personnelBLService.deletePersonnel(personnelVO.personnelID);

                    if (resultMessage == ResultMessage.Success) {
                        System.out.println("delete success");

                        pane.getChildren().remove(0);
                        pane.getChildren().add(new HotelworkerManagePane(stage, pane));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });


        }


        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (empty) {
                setGraphic(null);
                setText(null);
            } else {
                btnBox.getChildren().clear();
                btnBox.getChildren().add(editBtn);
                btnBox.getChildren().add(deleteBtn);
                setGraphic(btnBox);
                setText(null);
            }
        }

        public int getSelectedIndex() {
            return selectedIndex;
        }
    }


    /**
     * 确认修改工作人员信息
     */
    @FXML
    private void confirmModify() {
        PersonnelVO personnelVO = (PersonnelVO) hotelworkerList.getItems().get(webManHotelworkerButtonCell.getSelectedIndex());

        personnelVO.name = workernameField.getText();

        try {
            ResultMessage resultMessage = personnelBLService.updatePersonnelInfo(personnelVO);

            if (resultMessage == ResultMessage.Success) {
                System.out.println("modify success");

                hotelworkerList.setPrefHeight(400);
                modifyhotelworkerPane.setVisible(false);
                pane.getChildren().remove(0);
                pane.getChildren().add(new HotelworkerManagePane(stage, pane));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消修改工作人员信息
     */
    @FXML
    private void cancelModify() {
        hotelworkerList.setPrefHeight(400);
        modifyhotelworkerPane.setVisible(false);
    }
}
