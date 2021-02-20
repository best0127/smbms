package cn.smbms.dao;

import cn.smbms.pojo.SmbmsUser;

import java.util.List;
import java.util.Map;

/**
 * 用户dao层接口
 * @author Tang
 *
 */
public interface SmbmsUserDao {

    //接口：定义抽象方法 --》jdk8可以默认方法
    //没有变量，只有常量，不能实例化

    /**
     * 根据账号获取用户对象
     * @param Code
     * @return
     * @throws Exception
     */
    SmbmsUser getUserByCode (String Code) throws Exception;

    /**
     * 根据map集合条件 查询页面所需显示的用户集合数据
     * @param params
     * @return
     */
    List<SmbmsUser> getUserListByMap(Map<String,Object> params) throws Exception;

    /**
     * 根据map集合条件 查询用户总条数
     * @param params
     * @return
     */
    int getUserCountByMap(Map<String,Object> params) throws Exception;

    /**
     * 检查用户账号是否存在
     * @param userCode
     * @return
     */
    int checkUserCode(String userCode) throws Exception;

    //增删改 的任何dao层方法返回值都是int

    /**
     * 添加用户
     * @param user
     * @return
     * @throws Exception
     */
    int addUser(SmbmsUser user) throws Exception;

}
