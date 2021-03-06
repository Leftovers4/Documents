package util.poalfactory.impl;

import util.Coder;
import util.Const;
import util.EnumFactory;
import util.poalfactory.Po2Al_Factory;
import po.hotel.HotelPO;
import po.hotel.RoomPO;
import po.order.OrderPO;
import po.personnel.PersonnelPO;
import po.promotion.PromotionMRPO;
import po.promotion.PromotionPO;
import po.promotion.PromotionTraAreaPO;
import po.user.CreditRecordPO;
import po.user.UserPO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Hiki on 11/22/2016.
 */
public class Po2Al_FactoryImpl implements Po2Al_Factory{

    // TODO 注意初始值的设定
    // TODO 类似memberpo 初始值不能为null
    // 特殊需求：用户的账号、密码、姓名（名称）、联系方式必须密文存储
    @Override
    public ArrayList<Object> toUserAl(UserPO userPO) {
        ArrayList<Object> userInfoContent = new ArrayList<>();
        // 加密
        userInfoContent.add(Coder.encode(userPO.getUsername()));
        userInfoContent.add(Coder.encode(userPO.getPassword()));
        userInfoContent.add(Coder.encode(userPO.getName()));
        userInfoContent.add(userPO.isGender());
        userInfoContent.add(Coder.encode(userPO.getPhone()));
        userInfoContent.add(enumToString(userPO.getMemberPO().getMemberType()));
        userInfoContent.add(userPO.getMemberPO().getLevel());
        userInfoContent.add(dateToString(userPO.getMemberPO().getBirthday()));
        userInfoContent.add(userPO.getMemberPO().getEnterprise());

        return userInfoContent;
    }

    @Override
    public ArrayList<Object> toPersonnelAl(PersonnelPO personnelPO) {
        ArrayList<Object> personnelInfoContent = new ArrayList<>();
        personnelInfoContent.add(personnelPO.getPersonnelID());
        personnelInfoContent.add(personnelPO.getPassword());
        personnelInfoContent.add(enumToString(personnelPO.getPersonnelType()));
        personnelInfoContent.add(personnelPO.getName());
        personnelInfoContent.add(personnelPO.getHotelID());

        return personnelInfoContent;
    }

    @Override
    public ArrayList<Object> toHotelAl(HotelPO hotelPO) {
        ArrayList<Object> hotelInfoContent = new ArrayList<>();
        hotelInfoContent.add(hotelPO.getHotelID());
        hotelInfoContent.add(hotelPO.getHotelName());
        hotelInfoContent.add(hotelPO.getStar());
        hotelInfoContent.add(hotelPO.getAddress());
        hotelInfoContent.add(hotelPO.getTradingArea());
        hotelInfoContent.add(hotelPO.getDescription());
        hotelInfoContent.add(hotelPO.getService());

        return hotelInfoContent;
    }

    @Override
    public ArrayList<Object> toRoomAl(RoomPO roomPO) {
        ArrayList<Object> roomInfoContent = new ArrayList<>();
        roomInfoContent.add(roomPO.getroomID());
        roomInfoContent.add(roomPO.gethotelID());
        roomInfoContent.add(enumToString(roomPO.getRoomType()));
        roomInfoContent.add(roomPO.getTotal());
        roomInfoContent.add(roomPO.getAvailable());
        roomInfoContent.add(roomPO.getPrice());

        return roomInfoContent;
    }

    @Override
    public ArrayList<Object> toOrderAl(OrderPO orderPO) {
        ArrayList<Object> orderInfoContent = new ArrayList<>();
        orderInfoContent.add(orderPO.getOrderID());
        orderInfoContent.add(orderPO.getHotelID());
        orderInfoContent.add(orderPO.getUsername());
        orderInfoContent.add(enumToString(orderPO.getOrderType()));
        orderInfoContent.add(orderPO.getHotelName());
        orderInfoContent.add(enumToString(orderPO.getRoomType()));
        orderInfoContent.add(orderPO.getRoomAmount());
        orderInfoContent.add(orderPO.getRoomNumber());
        orderInfoContent.add(orderPO.getPersonAmount());
        orderInfoContent.add(orderPO.isWithChildren());
        // 时间
        orderInfoContent.add(dateTimeToString(orderPO.getOrderTimePO().getGenerateTime()));
        orderInfoContent.add(dateTimeToString(orderPO.getOrderTimePO().getExpectedCheckinTime()));
        orderInfoContent.add(dateTimeToString(orderPO.getOrderTimePO().getCheckinTime()));
        orderInfoContent.add(dateTimeToString(orderPO.getOrderTimePO().getExpectedLeaveTime()));
        orderInfoContent.add(dateTimeToString(orderPO.getOrderTimePO().getLeaveTime()));
        orderInfoContent.add(dateTimeToString(orderPO.getOrderTimePO().getLastExecuteTime()));
        orderInfoContent.add(dateTimeToString(orderPO.getOrderTimePO().getCancelTime()));
        // 价格
        orderInfoContent.add(orderPO.getOrderPricePO().getOriginPrice());
        orderInfoContent.add(orderPO.getOrderPricePO().getActualPrice());
        // 订单采用的促销策略类型
        orderInfoContent.add(enumToString(orderPO.getPromotionType()));
        // 评价
        orderInfoContent.add(dateTimeToString(orderPO.getReviewPO().getReviewTime()));
        orderInfoContent.add(orderPO.getReviewPO().getRating());
        orderInfoContent.add(orderPO.getReviewPO().getReview());
        // 申诉处理
        orderInfoContent.add(dateTimeToString(orderPO.getOrderHandleAppealPO().getHaTime()));
        orderInfoContent.add(enumToString(orderPO.getOrderHandleAppealPO().getHa_result()));

        return orderInfoContent;
    }

    @Override
    public ArrayList<Object> toPromotionAl(PromotionPO promotionPO) {
        ArrayList<Object> promotionInfoContent = new ArrayList<>();
        promotionInfoContent.add(promotionPO.getPromotionID());
        promotionInfoContent.add(enumToString(promotionPO.getPromotionType()));
        promotionInfoContent.add(promotionPO.getHotelID());
        promotionInfoContent.add(promotionPO.getDiscount());
        promotionInfoContent.add(promotionPO.getLeastRooms());
        // 起止时间
        promotionInfoContent.add(dateTimeToString(promotionPO.getPromotionTimePO().getBeginTime()));
        promotionInfoContent.add(dateTimeToString(promotionPO.getPromotionTimePO().getEndTime()));
        // 合作企业
        for (String eachEnt : promotionPO.getPromotionEnterprises()) {
            promotionInfoContent.add(eachEnt);
        }
        // 商圈优惠
        for (PromotionTraAreaPO eachTra : promotionPO.getPromotionTraAreaPOs()) {
            promotionInfoContent.add(eachTra.getTradingArea());
            promotionInfoContent.add(eachTra.getTraDiscount());
        }
        // 会员等级优惠
        for (PromotionMRPO eachMr : promotionPO.getPromotionMRPOs()) {
            promotionInfoContent.add(eachMr.getCredit());
            promotionInfoContent.add(eachMr.getMemberDiscount());
        }


        // TODO：满减还不用做
//        promotionInfoContent.add(0);
//        promotionInfoContent.add(0);

        return promotionInfoContent;

    }

    @Override
    public ArrayList<Object> toCreditRecordAl(CreditRecordPO creditRecordPO) {
        ArrayList<Object> crInfoContent = new ArrayList<>();
        crInfoContent.add(creditRecordPO.getrecordID());
        crInfoContent.add(creditRecordPO.getUsername());
        crInfoContent.add(creditRecordPO.getCurrentCredit());
        crInfoContent.add(creditRecordPO.getChangedCredit());
        crInfoContent.add(dateTimeToString(creditRecordPO.getChangedTime()));
        crInfoContent.add(enumToString(creditRecordPO.getCreditChangedCause()));
        crInfoContent.add(creditRecordPO.getOrderID());

        return crInfoContent;

    }

/*-------------------------------------------------辅助方法-------------------------------------------------------------*/

    /**
     * 把enum转换成string存储
     * @param e
     * @return
     */
    private static String enumToString(Enum e){
        if(e == null){
            return "";
        }else{
            return EnumFactory.getString(e);
        }
    }


    /**
     * 把datetime转换成String存储
     * @param input
     * @return
     */
    private static String dateTimeToString(LocalDateTime input){
        if(input == null){
            return Const.DefaultDateTime;
        }else{
            return input.toString();
        }
    }
    /**
     * 把date转换成String存储
     * @param input
     * @return
     */
    private static String dateToString(LocalDate input){
        if(input == null){
            return Const.DefaultDate;
        }else{
            return input.toString();
        }
    }



    private static boolean intToBool(int bool){
        if(bool == 0){
            return false;
        }else {
            return true;
        }

    }


}


