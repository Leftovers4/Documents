package blservice_stub;

import blservice.hotelblservice.HotelBLService;
import util.Address;
import util.ResultMessage;
import util.RoomType;
import util.TradingArea;
import vo.hotel.HotelVO;
import vo.hotel.RoomVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 2016/10/16.
 */
public class HotelBLService_Stub implements HotelBLService {
    @Override
    public ResultMessage addHotel(HotelVO hotelVO) {
        return null;
    }

    @Override
    public ResultMessage deleteHotel(long hotelID) {
        return null;
    }

    @Override
    public ResultMessage updateHotelInfo(HotelVO hotelVO) {
        return null;
    }

    @Override
    public HotelVO findHotelByID(long hotelID) {
        return null;
    }

    @Override
    public List<HotelVO> findHotelsByConditions(HotelVO hotelVO) {
        return null;
    }

    @Override
    public List<HotelVO> findHotelsByUsername(String username) {
        return null;
    }

    @Override
    public void sortHotels(List<HotelVO> hotelVOs, String key, int mode) {

    }

    @Override
    public ResultMessage addRoom(RoomVO roomVO) {
        return null;
    }

    @Override
    public ResultMessage deleteRoom(RoomType roomType) {
        return null;
    }

    @Override
    public ResultMessage updateRoomInfo(RoomVO roomVO) {
        return null;
    }

    @Override
    public List<RoomVO> findRoomsByHotelID(long hotelID) {
        return null;
    }
//    @Override
//    public HotelVO findByHotelID(long hotelID) {
//        return new HotelVO("如家", Address.NANJING, TradingArea.XIANLIN_CENTER, "好", "Wifi", null);
//    }
//
//    @Override
//    public ArrayList<HotelVO> showList(HotelVO hotelVO) {
//        ArrayList<HotelVO> list = new ArrayList<HotelVO>();
//        list.add(new HotelVO("如家", Address.NANJING, TradingArea.XIANLIN_CENTER, "好", "Wifi", null));
//        return list;
//    }
//
//    @Override
//    public ResultMessage add(HotelVO hotelVO) {
//        return ResultMessage.SUCCESS;
//    }
//
//    @Override
//    public ResultMessage del(long hotelID) {
//        return ResultMessage.SUCCESS;
//    }
//
//    @Override
//    public ResultMessage modify(HotelVO hotelVO) {
//        return ResultMessage.SUCCESS;
//    }
//
//    @Override
//    public ArrayList<RoomVO> getRooms() {
//        return null;
//    }
//
//    @Override
//    public ArrayList<RoomVO> setRooms() {
//        return null;
//    }
//
//    @Override
//    public double getRating() {
//        return 0;
//    }
}
