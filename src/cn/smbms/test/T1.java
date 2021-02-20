package cn.smbms.test;

import cn.smbms.utils.BaseDao;
import org.junit.Test;
import java.sql.SQLException;

public class T1 extends BaseDao {


    public static void main(String[] args)throws SQLException{
        T1 t = new T1();
        t.test1();
    }

    @Test
    public void test1() throws SQLException {
        String sql = "SELECT * FROM SMBmS_user";
        Object[] params = {};

        //调用父级中封装的通用查询
        rs = executeQuery(sql,params);

        //遍历结果集
        while (rs.next()){
            System.out.println("名称" + rs.getString("userName") + ",地址" + rs.getString("address"));
        }
    }
}
