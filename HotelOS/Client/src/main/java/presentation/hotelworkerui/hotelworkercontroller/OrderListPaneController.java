package presentation.hotelworkerui.hotelworkercontroller;

import bl.orderbl.OrderBLService;
import bl.orderbl.impl.OrderBlServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import presentation.util.alert.AlertController;
import presentation.util.other.CancelDateBefore;
import presentation.util.other.DisableColumnChangeListener;
import presentation.util.buttoncell.HotelListButtonCell;
import util.OrderType;
import vo.order.OrderVO;

import java.rmi.RemoteException;
import java.time.LocalDate;


/**
 * Created by Hitiger on 2016/11/20.
 * Description : 订单列表界面控制器
 */
public class OrderListPaneController {

    //列表
    @FXML
    private TableView orderTable;
    //列表栏
    @FXML
    private TableColumn idCol;
    @FXML
    private TableColumn userCol;
    @FXML
    private TableColumn priceCol;
    @FXML
    private TableColumn typeCol;
    @FXML
    private TableColumn checkInTimeCol;
    @FXML
    private TableColumn opCol;
    //订单类型框
    @FXML
    private ComboBox orderTypeBox;
    @FXML
    private TextField searchField;

    //提示框控制器
    private AlertController alertController;

    private Pane mainPane;
    private ObservableList<OrderVO> orderVoList;
    private OrderBLService orderBLService;

    public void launch(Pane mainPane) {
        this.mainPane = mainPane;
        alertController = new AlertController();
        initService();
        initBox();
        initTable();
    }

    private void initService() {
        try {
            orderBLService = new OrderBlServiceImpl();
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }
    }

    private void initBox() {
        orderTypeBox.getItems().addAll("所有订单", "未执行订单", "已执行订单", "异常订单", "撤销订单");
        orderTypeBox.setValue("所有订单");
        addBoxListener();
    }

    /**
     * 初始化列表
     */
    private void initTable() {
        orderVoList = getOrderVoList();

        idCol.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("actualPrice"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        checkInTimeCol.setCellValueFactory(new PropertyValueFactory<>("checkinTime"));
        //操作列添加按钮
        opCol.setCellFactory(new Callback<TableColumn<OrderVO, Boolean>, TableCell<OrderVO, Boolean>>() {
            @Override
            public TableCell<OrderVO, Boolean> call(TableColumn<OrderVO, Boolean> param) {
                return new HotelListButtonCell(mainPane, orderTable);
            }
        });


        orderTable.setItems(orderVoList);

        final TableColumn[] columns = {idCol, userCol, checkInTimeCol, priceCol, typeCol, opCol};
        orderTable.getColumns().addListener(new DisableColumnChangeListener(orderTable, columns));
    }


    /**
     * 设置组合框的监听
     */
    private void addBoxListener() {
        orderTypeBox.getSelectionModel().selectedItemProperty().addListener(
                (o, oldValue, newValue) -> {
                    try {
                        switch ((String) newValue) {
                            case "所有订单":
                                orderTable.setItems(FXCollections.observableArrayList(orderBLService.viewFullHotelOrderList(ComWorkerSceneController.hotelID)));
                                break;
                            case "未执行订单":
                                orderTable.setItems(FXCollections.observableArrayList(orderBLService.viewTypeHotelOrderList(ComWorkerSceneController.hotelID, OrderType.Unexecuted)));
                                break;
                            case "已执行订单":
                                orderTable.setItems(FXCollections.observableArrayList(orderBLService.viewTypeHotelOrderList(ComWorkerSceneController.hotelID, OrderType.Executed)));
                                break;
                            case "异常订单":
                                orderTable.setItems(FXCollections.observableArrayList(orderBLService.viewTypeHotelOrderList(ComWorkerSceneController.hotelID, OrderType.Abnormal)));
                                break;
                            case "撤销订单":
                                orderTable.setItems(FXCollections.observableArrayList(orderBLService.viewTypeHotelOrderList(ComWorkerSceneController.hotelID, OrderType.Canceled)));
                                break;
                        }
                    } catch (RemoteException e) {
                        alertController.showNetConnectAlert();
                    }
                });
    }

    private ObservableList<OrderVO> getOrderVoList() {
        ObservableList<OrderVO> list = null;
        try {
            list = FXCollections.observableArrayList(orderBLService.viewFullHotelOrderList(ComWorkerSceneController.hotelID));
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }
        return list;
    }

    @FXML
    private void searchOrderByID(){
        ObservableList<OrderVO> list = FXCollections.observableArrayList();
        OrderVO orderVO = null;
        try {
            orderVO = orderBLService.searchOrderByID(searchField.getText());
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }
        if(orderVO != null) list.add(orderVO);
        orderTable.setItems(list);
    }
}
