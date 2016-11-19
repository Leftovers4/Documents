package data.datahelper;

import util.ResultMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hiki on 11/19/2016.
 * 所有datahelper类的父类，提供增删改查的各种方法，子类只需调用这些接口就可以实现特定的数据库操作
 * 全部放在这个地方有利于减小耦合，便于当数据库发生改变时处理数据和映射关系
 */
public class DataHelperParent {

    /**
     * 数据库连接
     */
    protected Connection conn;

    /**
     * 未编译的SQL语句
     */
    protected PreparedStatement preparedStatement;

    /**
     * SQL语句
     */
    protected String sql;

    /**
     * 查找的结果集
     */
    protected ResultSet resultSet;

    /**
     * 返回的消息
     */
    protected ArrayList<Object> result;

    /*
     * 影响条数（Update, Delete, Insert）
     */
    protected int affectedRows;

    /**
     * 表名 —> 列
     */
    private final static Map<String, ArrayList<String>> TB_TO_COL = new HashMap<String, ArrayList<String>>();

//    private final static Map<String, Integer> TYPEMAP = new HashMap<String, Integer>();

    /**
     * 将map表初始化
     */
    static{
        // 初始化TB_TO_COL
        TB_TO_COL.put("user", strsToList("username", "password", "name", "gender", "phone", "member_type", "level", "birthday", "enterprise"));
        TB_TO_COL.put("credit_record", strsToList("record_id", "username", "current_credit", "changed_credit", "changed_time", "cause", "order_id"));
        TB_TO_COL.put("personnel", strsToList("personnel_id", "password", "personnel_type", "hotel_id"));
        TB_TO_COL.put("hotel", strsToList("hotel_id", "hotel_name", "star", "address", "trading_area", "description", "service"));
        TB_TO_COL.put("room", strsToList("room_id", "hotel_id", "room_type", "total", "available", "price"));
        TB_TO_COL.put("order_info", strsToList("order_id", "hotel_id", "username", "order_type", "hotel_name", "room_type", "room_amount",
                                               "room_number", "person_amount", "with_children", "generate_time", "expected_checkin_time",
                                               "checkin_time", "leave_time", "last_execute_time", "cancel_time", "original_price", "actual_price",
                                               "review_time", "rating", "review", "ha_time", "ha_result"));
        TB_TO_COL.put("promotion", strsToList("promotion_id", "promotion_type", "discount", "least_rooms", "begin_time", "end_time", "threshold", "reduction"));
        TB_TO_COL.put("enterprise", strsToList("match_id", "hotel_id", "enterprise"));
        TB_TO_COL.put("member_regulation", strsToList("address_id", "address", "trading_area", "discount"));

    }


    public DataHelperParent(){
        // 连接数据库
        this.conn = DBInit.connect();
        if(conn != null)
            System.out.println("Succeed to connect database...");
        else
            System.out.println("Fail to connect database...");
    }


    /**
     * 往表中添加一条数据
     * @param tableName 表名
     * @param parameters 参数
     * @return
     */
    public ResultMessage addToSQL(String tableName, ArrayList<Object> parameters){

        if(TB_TO_COL.get(tableName).size() != parameters.size()){
            System.out.println("Wrong inputs' number to " + tableName + ".");
        }

        // 确定参数个数
        int paraNum = parameters.size();

        // 将参数传进preparedStatement来构造完整的SQL语句
        try {

            preparedStatement = conn.prepareStatement(this.buildAddSQL(tableName));
            for(int i = 0; i < paraNum; i++){
                preparedStatement.setObject(i+1, parameters.get(i));
            }

            System.out.println(preparedStatement.toString());

            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return ResultMessage.FAIL;
        }


        if(affectedRows == 0){
            return ResultMessage.FAIL;
        }

        return ResultMessage.SUCCESS;

    }


    /**
     * 从表中删除一条数据（根据ID/username）
     * @param tableName 表名
     * @param id 主键
     * @return
     */
    public ResultMessage delFromSQL(String tableName, Object id){

        try {
            // 将参数传进preparedStatement来构造完整的SQL语句
            preparedStatement = conn.prepareStatement(this.buildDelSQL(tableName));

            preparedStatement.setObject(1, id);

            System.out.println(preparedStatement.toString());

            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return ResultMessage.FAIL;
        }

        if(affectedRows == 0){
            return ResultMessage.FAIL;
        }

        return ResultMessage.SUCCESS;
    }


    /**
     * 从数据库中更新一条数据（根据ID/username）
     * @param tableName
     * @param newParameters
     * @return
     */
    public ResultMessage updateFromSQL(String tableName, ArrayList<Object> newParameters){

        if(TB_TO_COL.get(tableName).size() != newParameters.size()){
            System.out.println("Wrong inputs' number to " + tableName + ".");
        }

        // 确定参数个数
        int paraNum = newParameters.size();

        // 将参数传进preparedStatement来构造完整的SQL语句
        try {
            preparedStatement = conn.prepareStatement(this.buildUpdateSQL(tableName));
            for(int i = 0; i < paraNum; i++){

                preparedStatement.setObject(i+1, newParameters.get(i));

                if(i == paraNum - 1){
                    // 若已经到了末尾，还应该设置id
                    preparedStatement.setObject(i+2, newParameters.get(0));
                }
            }


            System.out.println(preparedStatement.toString());

            affectedRows = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return ResultMessage.FAIL;
        }


        if(affectedRows == 0){
            return ResultMessage.FAIL;
        }

        return ResultMessage.SUCCESS;


    }


    /**
     * 从数据库中获得一条数据（根据ID/username）
     * @param tableName
     * @param id
     * @return
     */
    public ArrayList<Object> findFromSQLById(String tableName, String id){
        // 确定参数个数，以便于遍历全部列的内容
        int paraNum = TB_TO_COL.get(tableName).size();

        try {
            preparedStatement = conn.prepareStatement(this.buildFindByIdSQL(tableName));

            if(tableName == "user"){
                preparedStatement.setObject(1, id);
            }else{
                preparedStatement.setLong(1, Long.parseLong(id));
            }

            System.out.println(preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();

            resultSet.next();

            if(resultSet == null){
                System.out.println("resultSet null");
            }
//            System.out.println(resultSet.getObject(1));

            // 如果查找到对应的ID
            result = new ArrayList<>(paraNum);
            for (int i = 0; i < paraNum; i++) {
                result.add(resultSet.getObject(i+1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;



    }


    /**
     * TODO 根据条件获得信息
     * 从数据库中获得n条数据（根据ID/username）
     * @param tableName
     * @return
     */
    public ArrayList<ArrayList<String>> findFromSQLByConditions(String tableName) {

        return null;
    }


    /**
     * 从数据库中获得n条数据（没有条件），注意跟上边的避免重复
     * @param tableName 表名
     * @return
     */
    public ArrayList<ArrayList<Object>> findFromSQL(String tableName){
        // 确定参数个数，以便于遍历全部列的内容
        int paraNum = TB_TO_COL.get(tableName).size();
        // 创建存放结果的容器
        ArrayList<ArrayList<Object>> resultContent = new ArrayList<>();

        try {
            preparedStatement = conn.prepareStatement(this.buildFindSQL(tableName));
            System.out.println(preparedStatement.toString());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                result = new ArrayList<Object>(paraNum);

                for (int i = 0; i < paraNum; i++) {
                    result.add(resultSet.getObject(i+1));
                }

                resultContent.add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return resultContent;


    }


    /* ---------------------------------------------------以下都为辅助类--------------------------------------------------------- */

    /**
     * 辅助方法，根据表名创建相应的insert语句
     * @param tableName 表名
     * @return
     */
    private String buildAddSQL(String tableName){

        // 获取tableName相应的columns
        ArrayList<String> colums = TB_TO_COL.get(tableName);
        int length = colums.size();

        // 创建StringBuffer对象，用于存储SQL语句
        StringBuffer buffer = new StringBuffer("INSERT INTO `" + tableName + "` VALUES (");

        for(int i = 0; i < length - 1; i++){
            buffer.append(" ? ,");
        }

        buffer.append("? )");

        return buffer.toString();
    }


    /**
     * 辅助方法，根据表名创建相应的delete语句
     * @param tableName 表名
     * @return
     */
    private String buildDelSQL(String tableName){

        // 获取tableName相应的columns
        ArrayList<String> colums = TB_TO_COL.get(tableName);
        String primaryKey = colums.get(0);

        return "DELETE FROM `" + tableName + "` WHERE " + primaryKey + " = ?";
    }


    /**
     * 辅助方法，根据表名创建相应的update语句
     * @param tableName 表名
     * @return
     */
    private String buildUpdateSQL(String tableName){

        // 获取tableName相应的columns
        ArrayList<String> colums = TB_TO_COL.get(tableName);
        int length = colums.size();
        String primaryKey = colums.get(0);

        // 创建StringBuffer对象，用于存储SQL语句
        StringBuffer buffer = new StringBuffer("UPDATE `" + tableName + "` SET ");

        for(int i = 0; i < length; i++){
            buffer.append(colums.get(i)).append("=").append("?, ");
        }

        buffer.deleteCharAt(buffer.length() - 1);
        buffer.deleteCharAt(buffer.length() - 1);

        // TODO 最后这个？存疑
        buffer.append(" WHERE " + primaryKey + " = ?");

        return buffer.toString();

    }


    /**
     * 辅助方法，根据表名创建相应的findById语句
     * @param tableName 表名
     * @return
     */
    private String buildFindByIdSQL(String tableName){

        // 获取tableName相应的columns
        ArrayList<String> colums = TB_TO_COL.get(tableName);
        String primaryKey = colums.get(0);

        return "SELECT * FROM `" + tableName + "` WHERE " + primaryKey + " = ?";

    }


    /**
     * 辅助方法，根据表名创建相应的find语句（无条件）
     * @param tableName 表名
     * @return
     */
    private String buildFindSQL(String tableName){

        return "SELECT * FROM `" + tableName + "`";
    }


    /**
     * 辅助方法，用于将strings转成ArrayList<String>
     * @param parameters
     * @return
     */
    private static ArrayList<String> strsToList(String... parameters){

        // 获取strings参数的个数
        int n = parameters.length;

        ArrayList<String> result = new ArrayList<String>();

        for(int i = 0; i < n; i++){
            result.add(parameters[i]);
        }

        return result;
    }




}