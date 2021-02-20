package cn.smbms.dao;

import cn.smbms.pojo.SmbmsUser;
import cn.smbms.utils.BaseDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户Dao层实现类
 * @author Tang
 */
public class SmbmsUserDaoImpl extends BaseDao implements SmbmsUserDao{


    @Override
    public SmbmsUser getUserByCode(String code) throws Exception {

        //1、编写SQL语句
        String sql = "SELECT * FROM smbms_user WHERE userCode = ?";
        //2、填充参数
        Object[] params = {code};
        //3、执行sql
        rs = executeQuery(sql,params);

        //4、把数据库结果集对象 转换为 Java bean （pojo）
        if(rs.next()){
            SmbmsUser user = new SmbmsUser();
            user.setId(rs.getInt("id"));
            user.setUserCode(rs.getString("userCode"));
            user.setUserName(rs.getString("userName"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setGender(rs.getInt("gender"));
            user.setBirthday(rs.getDate("birthday"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setUserRole(rs.getInt("userRole"));
            user.setCreatedBy(rs.getInt("createdBy"));
            user.setCreationDate(rs.getDate("creationDate"));
            user.setModifyby(rs.getInt("modifyBy"));
            user.setModifyDate(rs.getDate("modifyDate"));
            return user;
        }
        return null;
    }


    @Override
    public List<SmbmsUser> getUserListByMap(Map<String, Object> params) throws Exception{

        //用于配合动态sql所封装的参数列表
        List array = new ArrayList();

        //1、编写sql
        //优化运行速度
        //StringBuffer是String的增强类，提高拼接速度
        StringBuffer sql = new StringBuffer(" SELECT u.*,r.roleName ");
        sql.append(" FROM smbms_user AS u ");
        sql.append(" INNER JOIN smbms_role AS r ON u.userRole = r.id ");
        sql.append(" WHERE 1=1 ");

        //2、动态sql
        if(!params.get("queryname").equals("")){
            sql.append(" AND u.userName LIKE CONCAT( '%', ?, '%' ) ");
            array.add(params.get("queryname"));
        }
        if(!params.get("queryUserRole").equals("0")){
            sql.append(" u.userRole = ? ");
            array.add(params.get("queryUserRole"));
        }
        sql.append(" LIMIT ?,? ");
        array.add(params.get("page"));
        array.add(params.get("limit"));

        //3、执行sql 获取结果集
        rs = executeQuery(sql.toString(),array.toArray());

        //4、把数据库结果集对象 转换为 Java bean （pojo）
        List<SmbmsUser> list = new ArrayList<SmbmsUser>();
        while (rs.next()){
            SmbmsUser user = new SmbmsUser();
            user.setId(rs.getInt("id"));
            user.setUserCode(rs.getString("userCode"));
            user.setUserName(rs.getString("userName"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setGender(rs.getInt("gender"));
            user.setBirthday(rs.getDate("birthday"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setUserRole(rs.getInt("userRole"));
            user.setCreatedBy(rs.getInt("createdBy"));
            user.setCreationDate(rs.getDate("creationDate"));
            user.setModifyby(rs.getInt("modifyBy"));
            user.setModifyDate(rs.getDate("modifyDate"));
            //连表查询新增角色查询
            user.setUserRoleName(rs.getString("roleName"));


            list.add(user);
        }
        return list;
    }

    @Override
    public int getUserCountByMap(Map<String, Object> params) throws Exception {


            //用于配合动态sql所封装的参数列表
            List array = new ArrayList();

            //1、编写sql
            //优化运行速度
            //StringBuffer是String的增强类，提高拼接速度
            StringBuffer sql = new StringBuffer(" SELECT COUNT(1) ");
            sql.append(" FROM smbms_user AS u ");
            sql.append(" WHERE 1=1 ");

            //2、动态sql
            if(!params.get("queryname").equals("")){
                sql.append(" AND u.userName LIKE CONCAT( '%', ?, '%' ) ");
                array.add(params.get("queryname"));
            }
            if(!params.get("queryUserRole").equals("0")){
                sql.append(" u.userRole = ? ");
                array.add(params.get("queryUserRole"));
            }

            //3、执行sql 获取结果集
            rs = executeQuery(sql.toString(),array.toArray());

            //4、把数据库结果集对象 转换为 Java bean （pojo）

            while (rs.next()){

               return rs.getInt(1);
            }
            return 0;
    }

    @Override
    public int checkUserCode(String userCode) throws Exception {

        //1、sql
        String sql = "SELECT COUNT(1) FROM `smbms_user` WHERE userCode = ?";
        //2、封装参数
        Object[] params = {userCode};
        //3、执行sql
        rs = executeQuery(sql,params);

        if(rs.next()){
            return rs.getInt(1);
        }

        return 0;
    }

    @Override
    public int addUser(SmbmsUser user) throws Exception {

        //1、sql
        String sql = " INSERT INTO smbms_user(userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate) "
                +" VALUES(?,?,?,?,?,?,?,?,?,?) ";
        Object[] params = {user.getUserCode(),user.getUserName(),user.getUserPassword(),
                            user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),
                            user.getUserRole(),user.getCreatedBy(),user.getCreationDate()};

        return executeUpdate(sql,params);
    }
}
