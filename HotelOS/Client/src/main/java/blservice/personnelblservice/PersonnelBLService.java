package blservice.personnelblservice;

import util.ResultMessage;
import vo.personnel.PersonnelVO;

import java.util.ArrayList;

/**
 * Created by Hiki on 2016/10/29.
 */
public interface PersonnelBLService {

    // 员工登录
    public ResultMessage login(PersonnelVO personnelVO);

    // 员工登出
    public ResultMessage logout();

    // 显示员工列表 TODO 按类型
    public ArrayList<PersonnelVO> showList();

    // 增加员工
    public ResultMessage add(PersonnelVO personnelVO);

    // 删除员工
    public ResultMessage del(PersonnelVO personnelVO);

    // 修改员工信息
    public ResultMessage modify(PersonnelVO personnelVO);

    // 查找员工
    public PersonnelVO find(long personnelID);



}