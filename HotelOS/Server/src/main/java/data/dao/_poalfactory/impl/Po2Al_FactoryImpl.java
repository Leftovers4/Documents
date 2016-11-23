package data.dao._poalfactory.impl;

import data.dao._poalfactory.Po2Al_Factory;
import po.hotel.HotelPO;
import po.hotel.RoomPO;
import po.order.OrderPO;
import po.personnel.PersonnelPO;
import po.promotion.PromotionPO;
import po.user.UserPO;

import java.util.ArrayList;

/**
 * Created by Hiki on 11/22/2016.
 */
public class Po2Al_FactoryImpl implements Po2Al_Factory{
    @Override
    public ArrayList<Object> toUserAl(UserPO userPO) {
        ArrayList<Object> userInfoContent = new ArrayList<>();
        userInfoContent.add(userPO.getUsername());
        userInfoContent.add(userPO.getPassword());
        userInfoContent.add(userPO.getName());
        userInfoContent.add(userPO.isGender());
        userInfoContent.add(userPO.getPhone());
        userInfoContent.add(userPO.getMemberPO().getMemberType());
        userInfoContent.add(userPO.getMemberPO().getLevel());
        userInfoContent.add(userPO.getMemberPO().getBirthday());
        userInfoContent.add(userPO.getMemberPO().getMemberType());

        return userInfoContent;
    }

    @Override
    public ArrayList<Object> toPersonnelAl(PersonnelPO personnelPO) {
        return null;
    }

    @Override
    public ArrayList<Object> toHotelAl(HotelPO hotelPO) {
        return null;
    }

    @Override
    public ArrayList<Object> toRoomAl(RoomPO roomPO) {
        return null;
    }

    @Override
    public ArrayList<Object> toOrderAl(OrderPO orderPO) {
        return null;
    }

    @Override
    public ArrayList<Object> toPromotionAl(PromotionPO promotionPO) {
        return null;
    }
}