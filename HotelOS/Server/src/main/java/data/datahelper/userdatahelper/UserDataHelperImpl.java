package data.datahelper.userdatahelper;

import data.datahelper.DataHelperParent;
import util.ResultMessage;
import util.TableName;

import java.util.ArrayList;

/**
 * Created by Hiki on 11/21/2016.
 */
public class UserDataHelperImpl extends DataHelperParent implements UserDataHelper{

    private final static String USER_TABLENAME = TableName.user.toString();



    @Override
    public ResultMessage insertToSQL(ArrayList<Object> userInfo) {
        return insertToSQL(USER_TABLENAME, userInfo);
    }

    @Override
    public ResultMessage deleteFromSQL(String username) {
        return delFromSQL(USER_TABLENAME, username);
    }

    @Override
    public ResultMessage updateFromSQL(ArrayList<Object> userInfo) {
        return updateFromSQL(USER_TABLENAME, userInfo);
    }

    @Override
    public ArrayList<ArrayList<Object>> findFromSQL() {
        return findFromSQL(USER_TABLENAME);
    }


    @Override
    public ArrayList<Object> findByIDFromSQL(String username) {
        return findFromSQLById(USER_TABLENAME, username);
    }
}
