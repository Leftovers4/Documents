package presentation.userui.usercontroller;

import bl.hotelbl.impl.HotelBlServiceImpl;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import presentation.userui.userscene.OrderDetailUserPane;
import presentation.util.alert.AlertController;
import presentation.util.buttoncell.HotelPhotoButtonCell;
import presentation.util.buttoncell.UserHotelListButtonCell;
import presentation.util.other.CancelDateBefore;
import util.AddTradProducer;
import util.RoomType;
import vo.hotel.HotelConditionsVO;
import vo.hotel.HotelVO;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by wyj on 2016/11/19.
 * description: 客户搜索酒店
 */
public class UserSearchHotelController {

    private Stage stage;
    private Pane mainPane;
    private String userID;

    @FXML private Pane moreInfoChoice;
    @FXML private Pane upMoreInfo;
    @FXML private TableView hotelList;
    @FXML private Pane downMoreInfo;

    @FXML private ComboBox cityComBox;
    @FXML private ComboBox tradingAreaCombox;

    @FXML private TextField searchField;

    @FXML private CheckBox singleRoomCB;
    @FXML private CheckBox coupleRoomCB;
    @FXML private CheckBox standardRoomCB;
    @FXML private CheckBox queenCB;
    @FXML private CheckBox loverroomCB;
    @FXML private CheckBox familyRoomCB;
    @FXML private CheckBox suiteroomCB;
    @FXML private CheckBox presidentialsuiteroomCB;
    @FXML private CheckBox businessroomCB;

    @FXML private DatePicker checkInDate;
    @FXML private DatePicker checkOutDate;

    @FXML private CheckBox twoHundredLess;
    @FXML private CheckBox twoToFourHundred;
    @FXML private CheckBox fourToSixHundred;
    @FXML private CheckBox sixToEightHundred;
    @FXML private CheckBox eightHundredMore;

    @FXML private CheckBox threeStar;
    @FXML private CheckBox fourStar;
    @FXML private CheckBox fiveStar;
    @FXML private CheckBox twoStarLess;

    @FXML private CheckBox fourPoFiveMore;
    @FXML private CheckBox threeToFour;
    @FXML private CheckBox threeLess;
    @FXML private CheckBox fourToFourPoFive;

    @FXML private CheckBox onlyCheckRegistered;

    @FXML private TableColumn hotelNameCol;
    @FXML private TableColumn hotelAddressCol;
    @FXML private TableColumn hoteltracingAreaCol;
    @FXML private TableColumn hotelRankCol;
    @FXML private TableColumn hotelScoreCol;
    @FXML private TableColumn registerRecordCol;
    @FXML private TableColumn priceCol;
    @FXML private TableColumn btnCol;
    @FXML private ScrollPane scrollPane;

    private UserHotelListButtonCell userHotelListButtonCell;
    private HotelBlServiceImpl hotelBlService;

    private AddTradProducer addTradProducer;
    private AlertController alertController;

    public void launch(Stage primaryStage, Pane mainPane, String userID) {
        this.stage = primaryStage;
        this.mainPane = mainPane;
        this.userID = userID;


        hotelList.setVisible(false);
        FlowPane.setMargin(hotelList, new Insets(-170, 0, 0, 50));

        addTradProducer = new AddTradProducer();
        alertController = new AlertController();

        try {
            hotelBlService = new HotelBlServiceImpl();

            initData();
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }

    }


    private void initData() {
        Iterator<String> cityList = addTradProducer.getAllAddress();
        while (cityList.hasNext()) {
            cityComBox.getItems().add(cityList.next());
        }

        //城市选框
        cityComBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                initeBusinessAreaComBox(newValue.toString());
            }
        });

        //商圈选框
        tradingAreaCombox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    char ch[] = newValue.toString().toCharArray();
                    if (ch.length > 6) {
                        tradingAreaCombox.setStyle("-fx-font-size: 0.85em");
                    } else {
                        tradingAreaCombox.setStyle("-fx-font-size: 1em");
                    }
                }
            }
        });


        checkInDate.setDayCellFactory(new CancelDateBefore(checkInDate, LocalDate.now()));
        checkInDate.setOnAction(event -> {
            checkOutDate.setDayCellFactory(new CancelDateBefore(checkOutDate, checkInDate.getValue()));
        });
    }

    /**
     * 初始化商圈选框
     * @param city
     */
    private void initeBusinessAreaComBox(String city) {

        tradingAreaCombox.getItems().clear();
        Iterator<String> areaList = addTradProducer.getTradingAreasByAddress(city);
        while (areaList.hasNext()) {
            tradingAreaCombox.getItems().add(areaList.next());
        }
    }


    /**
     * 显示更多筛选
     */
    @FXML
    private void showMoreChoice() {
        moreInfoChoice.setVisible(true);
        FlowPane.setMargin(moreInfoChoice, new Insets(-70, 0, 0, 160));
        moreInfoChoice.setDisable(false);
        upMoreInfo.setDisable(false);
        upMoreInfo.setVisible(true);
        FlowPane.setMargin(hotelList, new Insets(90, 0, 0, 50));
        downMoreInfo.setVisible(false);
        downMoreInfo.setDisable(true);
    }

    /**
     * 隐藏更多筛选
     */
    @FXML
    private void hideMoreChoice() {
        moreInfoChoice.setVisible(false);
        FlowPane.setMargin(moreInfoChoice, new Insets(-130, 0, 0, 160));
        moreInfoChoice.setDisable(true);
        downMoreInfo.setVisible(true);
        downMoreInfo.setDisable(false);
        FlowPane.setMargin(hotelList, new Insets(40, 0, 0, 50));
        upMoreInfo.setVisible(false);
        upMoreInfo.setDisable(true);
    }



    @FXML
    /**
     * 重置筛选条件
     */
    private void resetChoice() {
        singleRoomCB.setSelected(false);
        standardRoomCB.setSelected(false);
        coupleRoomCB.setSelected(false);
        queenCB.setSelected(false);
        loverroomCB.setSelected(false);
        familyRoomCB.setSelected(false);
        suiteroomCB.setSelected(false);
        presidentialsuiteroomCB.setSelected(false);
        businessroomCB.setSelected(false);
        checkInDate.setValue(null);
        checkOutDate.setValue(null);
        twoHundredLess.setSelected(false);
        twoToFourHundred.setSelected(false);
        fourToSixHundred.setSelected(false);
        sixToEightHundred.setSelected(false);eightHundredMore.setSelected(false);
        threeStar.setSelected(false);
        fourStar.setSelected(false);
        fiveStar.setSelected(false);
        twoStarLess.setSelected(false);
        fourPoFiveMore.setSelected(false);
        threeToFour.setSelected(false);
        threeLess.setSelected(false);
        fourToFourPoFive.setSelected(false);
        onlyCheckRegistered.setSelected(false);
    }


    /**
     * 确认执行酒店搜索
     */
    @FXML
    private void confirmChoose() {

        if (cityComBox.getValue() == null || tradingAreaCombox.getValue() == null) {
            alertController.showNullWrongAlert("请选择地址", "错误提示");
        } else {

            if(((checkInDate.getValue() == null) == (checkOutDate.getValue()==null))){
                //同时选择或者不选择
                if(chooseselected() && checkInDate == null){
                    alertController.showUnSelectItemAlert("请选择入住、退房日期", "错误提示");
                }else{

                    HotelConditionsVO hotelConditionsVO = new HotelConditionsVO();
                    hotelConditionsVO.address = cityComBox.getValue().toString();
                    hotelConditionsVO.tradingArea = tradingAreaCombox.getValue().toString();

                    hotelConditionsVO.name = searchField.getText();

                    hotelConditionsVO.expectedCheckInTime = checkInDate.getValue();
                    hotelConditionsVO.expectedLeaveTime = checkOutDate.getValue();

                    hotelConditionsVO.roomTypeList = getRoomType();

                    hotelConditionsVO.priceLowerBound = getPriceBound()[0];
                    hotelConditionsVO.priceUpperBound = getPriceBound()[1];

                    hotelConditionsVO.starLowerBound = getStarBound()[0];
                    hotelConditionsVO.starUpperBound = getStarBound()[1];

                    hotelConditionsVO.ratingLowerBound = getRateBound()[0];
                    hotelConditionsVO.ratingUpperBound = getRateBound()[1];

                    hotelConditionsVO.hasOrdered = onlyCheckRegistered.isSelected();

                    ObservableList<HotelVO> list = getSearchedHotelList(hotelConditionsVO);
                    if(list.size() == 0){
                        alertController.showNullWrongAlert("无符合筛选条件的酒店", "搜索提示");
                    }else {
                        scrollPane.setVvalue(1.0d);
                        FlowPane.setMargin(hotelList, new Insets(60, 0, 0, 10));
                        initalTable(list);
                    }
                }
            }else{
                alertController.showUnSelectItemAlert("请选择入住、退房日期", "错误提示");
            }

        }

    }

    /**
     * 获取房间类型
     * @return
     */
    private ArrayList<RoomType> getRoomType() {
        ArrayList<RoomType> roomTypes = new ArrayList<>();

        boolean single = singleRoomCB.isSelected();
        boolean couple = coupleRoomCB.isSelected();
        boolean standard = standardRoomCB.isSelected();
        boolean queen = queenCB.isSelected();
        boolean lover = loverroomCB.isSelected();
        boolean family = familyRoomCB.isSelected();
        boolean suite = suiteroomCB.isSelected();
        boolean president = presidentialsuiteroomCB.isSelected();
        boolean business = businessroomCB.isSelected();

        if (single) {
            roomTypes.add(RoomType.Single);
        }
        if (couple) {
            roomTypes.add(RoomType.Couple);
        }
        if (standard) {
            roomTypes.add(RoomType.Standard);
        }
        if (queen) {
            roomTypes.add(RoomType.Queen);
        }
        if (lover) {
            roomTypes.add(RoomType.Lover);
        }
        if (family) {
            roomTypes.add(RoomType.Family);
        }
        if (suite) {
            roomTypes.add(RoomType.Suite);
        }
        if (president) {
            roomTypes.add(RoomType.PresidentialSuite);
        }
        if (business) {
            roomTypes.add(RoomType.BusinessSuite);
        }

        boolean isAll = single && couple && standard && queen &&
                lover && family && suite && president && business;

        if (roomTypes.size() == 0) {
            roomTypes.add(RoomType.All);
        }

        if (isAll) {
            roomTypes.clear();
            roomTypes.add(RoomType.All);
        }

        return roomTypes;
    }

    /**
     * 获取价格区间
     * @return
     */
    private double[] getPriceBound() {
        double[] bound = new double[2];

        double lowerprice = Double.MAX_VALUE, higherprice = 0;

        boolean twoless = twoHundredLess.isSelected();
        boolean twotofour = twoToFourHundred.isSelected();
        boolean fourtosix = fourToSixHundred.isSelected();
        boolean sixtoeight = sixToEightHundred.isSelected();
        boolean eightmore = eightHundredMore.isSelected();

        if (twoless) {
            if (lowerprice >= 0) {
                lowerprice = 0;
            }
            if (higherprice <= 200) {
                higherprice = 200;
            }
        }
        if (twotofour) {
            if (lowerprice >= 200) {
                lowerprice = 200;
            }
            if (higherprice <= 400) {
                higherprice = 400;
            }
        }
        if (fourtosix) {
            if (lowerprice >= 400) {
                lowerprice = 400;
            }
            if (higherprice <= 600) {
                higherprice = 600;
            }
        }
        if (sixtoeight) {
            if (lowerprice > 600) {
                lowerprice = 600;
            }
            if (higherprice <= 800) {
                higherprice = 800;
            }
        }
        if (eightmore) {
            if (lowerprice >= 800) {
                lowerprice = 800;
            }
            if (higherprice < Double.MAX_VALUE) {
                higherprice = Double.MAX_VALUE;
            }
        }

        boolean isAll = twoless || twotofour || fourtosix || sixtoeight || eightmore;

        if (!isAll) {
            lowerprice = 0;
            higherprice = Double.MAX_VALUE;
        }

        bound[0] = lowerprice;
        bound[1] = higherprice;

        return bound;
    }

    /**
     * 获取星级区间
     * @return
     */
    private int[] getStarBound() {
        int[] bound = new int[2];

        int lowerstar = 5, higherstar = 0;

        boolean twoless = twoStarLess.isSelected();
        boolean three = threeStar.isSelected();
        boolean four = fourStar.isSelected();
        boolean five = fiveStar.isSelected();

        if (twoless) {
            if (lowerstar >= 0) {
                lowerstar = 0;
            }
            if (higherstar <= 2) {
                higherstar = 2;
            }
        }
        if (three) {
            if (lowerstar >= 3) {
                lowerstar = 3;
            }
            if (higherstar <= 3) {
                higherstar = 3;
            }
        }
        if (four) {
            if (lowerstar >= 4) {
                lowerstar = 4;
            }
            if (higherstar <= 4) {
                higherstar = 4;
            }
        }
        if (five) {
            if (lowerstar >= 5) {
                lowerstar = 5;
            }
            if (higherstar <= 5) {
                higherstar = 5;
            }
        }

        boolean isAll = twoless || three || four || five;

        if (!isAll) {
            lowerstar = 0;
            higherstar = 5;
        }

        bound[0] = lowerstar;
        bound[1] = higherstar;

        return bound;
    }

    /**
     * 获取酒店评分区间
     * @return
     */
    private double[] getRateBound() {
        double[] bound = new double[2];

        double lowerrate = 5, higherrate = 0;

        boolean threeless = threeLess.isSelected();
        boolean threetofour = threeToFour.isSelected();
        boolean fourtofourpfive = fourToFourPoFive.isSelected();
        boolean fourpfivemore = fourPoFiveMore.isSelected();

        if (threeless) {
            if (lowerrate >= 0) {
                lowerrate = 0;
            }
            if (higherrate <= 3) {
                higherrate = 3;
            }
        }
        if (threetofour) {
            if (lowerrate >= 3) {
                lowerrate = 3;
            }
            if (higherrate <= 4) {
                higherrate = 4;
            }
        }
        if (fourtofourpfive) {
            if (lowerrate >= 4) {
                lowerrate = 4;
            }
            if (higherrate <= 4.5) {
                higherrate = 4.5;
            }
        }
        if (fourpfivemore) {
            if (lowerrate >= 4.5) {
                lowerrate = 4.5;
            }
            if (higherrate <= 5) {
                higherrate = 5;
            }
        }

        boolean isAll = threeless || threetofour || fourtofourpfive || fourpfivemore;

        if (!isAll) {
            lowerrate = 0;
            higherrate = 5;
        }

        bound[0] = lowerrate;
        bound[1] = higherrate;

        return bound;
    }


    /**
     * 初始化列表数据
     * @param
     */
    private void initalTable(ObservableList<HotelVO> list) {

        hotelNameCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                return new HotelPhotoButtonCell(hotelList);
            }
        });
        hotelAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        hoteltracingAreaCol.setCellValueFactory(new PropertyValueFactory<>("tradingArea"));
        hotelRankCol.setCellValueFactory(new PropertyValueFactory<>("star"));
        hotelScoreCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        registerRecordCol.setCellValueFactory(new PropertyValueFactory<>("hasOrdered"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        btnCol.setCellFactory(new Callback<TableColumn, TableCell>() {
            @Override
            public TableCell call(TableColumn param) {
                userHotelListButtonCell = new UserHotelListButtonCell(stage, mainPane, hotelList, userID);
                return userHotelListButtonCell;
            }
        });

            hotelList.setVisible(true);
            hotelList.setItems(list);

            hotelList.setFixedCellSize(170);
            hotelList.prefHeightProperty().bind(hotelList.fixedCellSizeProperty().multiply(Bindings.size(hotelList.getItems()).add(1.01)));
            hotelList.minHeightProperty().bind(hotelList.prefHeightProperty());
            hotelList.maxHeightProperty().bind(hotelList.prefHeightProperty());

    }


    private ObservableList getSearchedHotelList(HotelConditionsVO hotelConditionsVO) {
        ObservableList<HotelVO> list = null;
        try {
            list = FXCollections.observableArrayList(hotelBlService.searchHotelsByConditions(userID, hotelConditionsVO));
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }

        return list;
    }




    @FXML
    private void allRoomType() {
        singleRoomCB.setSelected(true);
        coupleRoomCB.setSelected(true);
        standardRoomCB.setSelected(true);
        queenCB.setSelected(true);
        loverroomCB.setSelected(true);
        familyRoomCB.setSelected(true);
        suiteroomCB.setSelected(true);
        presidentialsuiteroomCB.setSelected(true);
        businessroomCB.setSelected(true);
    }

    @FXML
    private void allPrice() {
        twoHundredLess.setSelected(true);
        twoToFourHundred.setSelected(true);
        fourToSixHundred.setSelected(true);
        sixToEightHundred.setSelected(true);
        eightHundredMore.setSelected(true);
    }

    @FXML
    private void allStar() {
        twoStarLess.setSelected(true);
        threeStar.setSelected(true);
        fourStar.setSelected(true);
        fiveStar.setSelected(true);
    }

    @FXML
    private void allRating() {
        fourPoFiveMore.setSelected(true);
        fourToFourPoFive.setSelected(true);
        threeToFour.setSelected(true);
        threeLess.setSelected(true);
    }


    /**
     * 判断是否有筛选条件
     * @return
     */
    private boolean chooseselected() {
        boolean single = singleRoomCB.isSelected();
        boolean couple = coupleRoomCB.isSelected();
        boolean standard = standardRoomCB.isSelected();
        boolean queen = queenCB.isSelected();
        boolean lover = loverroomCB.isSelected();
        boolean family = familyRoomCB.isSelected();
        boolean suite = suiteroomCB.isSelected();
        boolean president = presidentialsuiteroomCB.isSelected();
        boolean business = businessroomCB.isSelected();

        boolean twoless = twoHundredLess.isSelected();
        boolean twotofour = twoToFourHundred.isSelected();
        boolean fourtosix = fourToSixHundred.isSelected();
        boolean sixtoeight = sixToEightHundred.isSelected();
        boolean eightmore = eightHundredMore.isSelected();

        boolean twolessstar = twoStarLess.isSelected();
        boolean three = threeStar.isSelected();
        boolean four = fourStar.isSelected();
        boolean five = fiveStar.isSelected();

        boolean threeless = threeLess.isSelected();
        boolean threetofour = threeToFour.isSelected();
        boolean fourtofourpfive = fourToFourPoFive.isSelected();
        boolean fourpfivemore = fourPoFiveMore.isSelected();

        return single || couple || standard || queen || lover ||
                family || suite || president || business ||
                twoless || twotofour || fourtosix || sixtoeight ||
                eightmore || twolessstar || three || four || five ||
                threeless || threetofour || fourtofourpfive || fourpfivemore;
    }
}
