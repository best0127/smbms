package cn.smbms.service;

import cn.smbms.pojo.SmbmsUser;

import java.util.List;
import java.util.Map;

/**
 * 用户逻辑业务层接口
 * @author Tang
 */
public interface SmbmsUserService {

    /**
     * 登录业务
     * @param code
     * @param password
     * @return
     * @throws Exception
     */

    SmbmsUser login(String code,String password) throws Exception;

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

    /**
     * 添加用户
     * @param user
     * @return
     * @throws Exception
     */
    int addUser(SmbmsUser user) throws Exception;

}
