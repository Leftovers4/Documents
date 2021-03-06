package util;

/**
 * Created by Hiki on 2016/10/15.
 */

public enum ResultMessage {

    /**
     * 成功
     */
    Success,

    /**
     * 失败
     */
    Fail,

    /**
     * 合作企业名称已存在
     */
    CoEnterpriseExists,

    /**
     * 连接服务器失败
     */
    ServerConnectionFail,

    /**
     * 登录失败：用户名不存在
     */
    UsernameNotExisted,

    /**
     * 登录失败：密码错误
     */
    PasswordWrong,

    /**
     * 传入值为null
      */
    NullInput,

    /**
     * 传入值为空
     */
    EmptyInput,

    /**
     * 中断
     */
    Pause,

    /**
     * 数据已存在
     */
    DataExisted,

    /**
     * 数据不存在
     */
    DataNotExisted,

    /**
     * 数据库错误
     */
    SqlError,

    /**
     * 信用值不足
     */
    CreditNotEnough,

    /**
     * 订单状态不正确
     */
    OrederStatusIncorrect;

}

