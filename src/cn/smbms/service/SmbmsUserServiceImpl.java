package cn.smbms.service;

import cn.smbms.dao.SmbmsUserDao;
import cn.smbms.dao.SmbmsUserDaoImpl;
import cn.smbms.pojo.SmbmsUser;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class SmbmsUserServiceImpl implements SmbmsUserService{

    //声明dao层 多态
    private SmbmsUserDao userDao = new SmbmsUserDaoImpl();

    @Override
    public SmbmsUser login(String code, String password) throws Exception {

        //获取dao层中的数据
        SmbmsUser user = userDao.getUserByCode(code);

        //判断账号是否存在 以及密码是否正确
        if(user!=null && user.getUserPassword().equals(password)){
            return user;
        }

        //如果账号或密码错误 则抛出异常
        throw new Exception("账号或密码错误！");

    }

    @Override
    public List<SmbmsUser> getUserListByMap(Map<String, Object> params) throws Exception {
        return userDao.getUserListByMap(params);
    }

    @Override
    public int getUserCountByMap(Map<String, Object> params) throws Exception {
        return userDao.getUserCountByMap(params);
    }

    @Override
    public int checkUserCode(String userCode) throws Exception {
        return userDao.checkUserCode(userCode);
    }

    @Override
    public int addUser(SmbmsUser user) throws Exception {
        return userDao.addUser(user);
    }
}
