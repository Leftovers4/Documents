package vo.order;

import po.hotel.HotelPO;
import po.order.OrderPO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lib on 2016/12/1.
 */
public class OrderVOCreator {

    /**
     * 传给界面：创建包含酒店工作人员查看酒店评价的界面信息的vo
     *
     */
    public ReviewVO createOrdinaryReviewVO(OrderPO orderPO) {
        ReviewVO res = new ReviewVO();

        res.orderID = orderPO.getOrderID();
        res.review = orderPO.getReviewPO().getReview();
        res.rating = orderPO.getReviewPO().getRating();
        res.roomType = orderPO.getRoomType();
        res.reviewTime = orderPO.getReviewPO().getReviewTime();
        res.username = orderPO.getUsername();

        return res;
    }

    /**
     * 传给界面：创建包含酒店工作人员查看订单详情的界面信息的vo
     *
     */
    public OrderVO createDetailedOrderVO(OrderPO orderPO){
        OrderVO res = new OrderVO();

        res.orderID = orderPO.getOrderID();
        res.orderType = orderPO.getOrderType();
        res.orderPriceVO.actualPrice = orderPO.getOrderPricePO().getActualPrice();
        res.orderTimeVO.generateTime = orderPO.getOrderTimePO().getGenerateTime();
        res.orderTimeVO.lastExecuteTime = orderPO.getOrderTimePO().getLastExecuteTime();
        res.orderTimeVO.checkinTime = orderPO.getOrderTimePO().getCheckinTime();
        res.orderTimeVO.expectedLeaveTime = orderPO.getOrderTimePO().getExpectedLeaveTime();
        res.orderTimeVO.leaveTime = orderPO.getOrderTimePO().getLeaveTime();
        res.username = orderPO.getUsername();
        res.personAmount = orderPO.getPersonAmount();
        res.withChildren = orderPO.isWithChildren();
        res.roomType = orderPO.getRoomType();
        res.roomAmount = orderPO.getRoomAmount();
        res.roomNumber = orderPO.getRoomNumber();

        return res;
    }

    public OrderVO createExtraOrderVO(OrderPO orderPO, HotelPO hotelPO){
        OrderVO res = new OrderVO();

        res.orderID = orderPO.getOrderID();
        res.orderTimeVO.generateTime = orderPO.getOrderTimePO().getGenerateTime();
        res.orderType = orderPO.getOrderType();
        res.hotelName = orderPO.getHotelName();
        res.hotelAddress = hotelPO.getAddress();
        res.hotelTradingArea = hotelPO.getTradingArea();
        res.hotelService = hotelPO.getService();
        res.orderTimeVO.expectedCheckinTime = orderPO.getOrderTimePO().getExpectedCheckinTime();
        res.orderTimeVO.expectedLeaveTime = orderPO.getOrderTimePO().getExpectedLeaveTime();
        res.roomType = orderPO.getRoomType();
        res.roomAmount = orderPO.getRoomAmount();
        res.personAmount = orderPO.getPersonAmount();
        res.withChildren = orderPO.isWithChildren();
        res.orderPriceVO.actualPrice = orderPO.getOrderPricePO().getActualPrice();

        return res;
    }

    public List<OrderVO> createAllExtraOrderVO(List<OrderPO> orderPOList, List<HotelPO> hotelPOList){
        List<OrderVO> res = new ArrayList<>();

        for (int i = 0; i < orderPOList.size(); i++) {
            res.add(createExtraOrderVO(orderPOList.get(i), hotelPOList.get(i)));
        }

        return res;
    }

}