package vo.personnel;

import vo.user.UserVO;

/**
 * Created by Hiki on 2016/10/27.
 */
public class HotelWorkerVO extends PersonnelVO {

    public long hotelID;

    public HotelWorkerVO(long hotelworkerID, String password, long hotelID) {
        super(hotelworkerID, password);
        this.hotelID = hotelID;
    }


}