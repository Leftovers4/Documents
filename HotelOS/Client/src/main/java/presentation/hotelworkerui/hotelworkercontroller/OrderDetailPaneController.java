package presentation.hotelworkerui.hotelworkercontroller;

import bl.orderbl.OrderBLService;
import bl.orderbl.impl.OrderBlServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import presentation.hotelworkerui.hotelworkerscene.FindOrderPane;
import presentation.hotelworkerui.hotelworkerscene.OrderListPane;
import presentation.hotelworkerui.hotelworkerscene.UserReviewPane;
import presentation.util.alert.AlertController;
import presentation.util.other.MyOrderLabel;
import util.DateTimeFormat;
import util.EnumFactory;
import util.OrderType;
import vo.order.OrderVO;
import vo.order.ReviewVO;

import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;

/**
 * Created by Hitiger on 2016/11/20.
 * Description : 订单详情界面控制器
 */
public class OrderDetailPaneController {

    //订单号、状态、价格
    @FXML private Label orderIDLabel;
    @FXML private Label orderTypeLabel;
    @FXML private Label orderOriPriceLabel;
    @FXML private Label orderProLabel;
    @FXML private Label orderActPriceLabel;

    //生成时间
    @FXML private Label generateTimeLabel;

    //最晚执行时间
    @FXML private Label lastExecuteTimeLabel;

    //实际离开时间
    @FXML private Label actLeaveTimeLabel;

    //入住时间
    @FXML private Label checkInTimeLabel;

    //预计离开时间
    @FXML private Label expLeaveTimeLabel;

    //客户名、入住房间信息
    @FXML private Label userNameLabel;
    @FXML private Label peopleAmountLabel;
    @FXML private Label withChildrenLabel;
    @FXML private Label roomTypeLabel;
    @FXML private Label roomAmountLabel;
    @FXML private Label roomIDLabel;

    //查看客户评价
    @FXML private Button showReviewBtn;


    private Pane mainPane;
    private AlertController alertController;
    private OrderVO orderVO;
    //是否从更新入住信息进入
    private Boolean isCheckIn;
    //是否从订单列表进入
    private Boolean isFromList;

    public void launch(Pane mainPane,Boolean isCheckIn,Boolean isFromList,OrderVO orderVO) {
        this.mainPane = mainPane;
        this.isFromList = isFromList;
        this.isCheckIn = isCheckIn;
        this.orderVO = orderVO;
        alertController = new AlertController();

        //初始化便签
        initOrderLabel(orderVO);
        initReviewBtn(orderVO.orderType);
    }

    /**
     * 初始化标签
     * @param orderVO
     */
    private void initOrderLabel(OrderVO orderVO) {
        orderIDLabel.setText(orderVO.orderID);

        orderTypeLabel.setText(EnumFactory.getString(orderVO.orderType));
        MyOrderLabel.changeColor(orderVO.orderType, orderTypeLabel);
        orderOriPriceLabel.setText(String.valueOf(orderVO.orderPriceVO.originPrice));
        orderProLabel.setText(String.valueOf(orderVO.orderPromoInfoVO.promotionType == null ? "无优惠" : EnumFactory.getString(orderVO.orderPromoInfoVO.promotionType)));
        orderActPriceLabel.setText(String.valueOf(orderVO.orderPriceVO.actualPrice));

        generateTimeLabel.setText(orderVO.orderTimeVO.generateTime.format(DateTimeFormat.dateHourFormat));
        lastExecuteTimeLabel.setText(orderVO.orderTimeVO.lastExecuteTime.format(DateTimeFormat.dateHourFormat));
        checkInTimeLabel.setText(orderVO.orderTimeVO.checkinTime == null ? "尚未入住" : orderVO.orderTimeVO.checkinTime.format(DateTimeFormat.dateHourFormat));
        expLeaveTimeLabel.setText(orderVO.orderTimeVO.expectedLeaveTime == null ? "尚未入住" : orderVO.orderTimeVO.expectedLeaveTime.format(DateTimeFormat.dateHourFormat));
        actLeaveTimeLabel.setText(orderVO.orderTimeVO.leaveTime == null ? (orderVO.orderType == OrderType.Executed ? "尚未退房" : "尚未入住") : orderVO.orderTimeVO.leaveTime.format(DateTimeFormat.dateHourFormat));

        userNameLabel.setText(orderVO.username);
        withChildrenLabel.setText(orderVO.withChildren ? "有" : "无");
        peopleAmountLabel.setText(String.valueOf(orderVO.personAmount));
        roomTypeLabel.setText(EnumFactory.getString(orderVO.roomType));
        roomAmountLabel.setText(String.valueOf(orderVO.roomAmount));
        roomIDLabel.setText(orderVO.roomNumber.equals("") ? "未入住" : orderVO.roomNumber);
    }

    private void initReviewBtn(OrderType orderType) {
        if(orderType != OrderType.Executed) showReviewBtn.setVisible(false);
    }

    /**
     * 查看评价
     */
    @FXML
    private void showReview(){
        ReviewVO reviewVO = null;
        try {
            OrderBLService orderBLService = new OrderBlServiceImpl();
            reviewVO = orderBLService.viewOrderReview(orderVO.orderID);
        } catch (RemoteException e) {
            alertController.showNetConnectAlert();
        }

        if(reviewVO == null) {
            alertController.showNullWrongAlert("客户尚未评价","查看失败");
        }else {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(new UserReviewPane(mainPane,isCheckIn,isFromList,orderVO,reviewVO));
        }
    }

    @FXML
    private void back(){
        mainPane.getChildren().clear();
        if(isFromList) {
            mainPane.getChildren().add(new OrderListPane(mainPane));
        }else {
            if(isCheckIn) mainPane.getChildren().add(new FindOrderPane(mainPane,isCheckIn));
        }
    }
}
