package dataservice_stub;

import dataservice.orderdataservice.OrderDataService;
import po.order.ReviewPO;
import po.order.OrderPO;
import util.OrderType;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by kevin on 2016/10/16.
 */
public class OrderDataService_Stub implements OrderDataService {


    @Override
    public OrderPO findByOrderID(String orderID) throws RemoteException {
//        return new OrderPO("12345620161111001", 123456, "如家", "张三", null, 2, false, null, null);
        return null;
    }

    @Override
    public ArrayList<OrderPO> find(OrderPO orderPO) throws RemoteException {
        ArrayList<OrderPO> list = new ArrayList<>();
        list.add(new OrderPO("12345620161111001", 123456,"张三", OrderType.Abnormal,"如家", null, 0, null, 2, false, null, null,null,null));
        return list;
    }

    @Override
    public void insert(OrderPO orderPO) throws RemoteException {

    }

    @Override
    public void update(OrderPO orderPO) throws RemoteException {

    }

    @Override
    public ArrayList<OrderPO> getList() throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<ReviewPO> findReviewListByHotelID(long hotelID) throws RemoteException {
        return null;
    }
}
