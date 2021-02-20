package cn.smbms.utils;

import java.sql.*;

/**
 * 通用的数据库操作工具类
 * @author Tang
 *
 */
public class BaseDao {

    //数据库连接对象
    public Connection conn = null;

    //结果集
    public ResultSet rs = null;

    //数据库操作对象
    public PreparedStatement pstat = null;

    public boolean  openConnection(){
        //获取配置文件中的4个参数
        String driver = ConfigUtil.getValue("driver");
        String url = ConfigUtil.getValue("url");
        String user = ConfigUtil.getValue("user");
        String password = ConfigUtil.getValue("password");

        try{
            //通过java反射加载驱动
            Class.forName(driver);
            //获取连接对象
            conn = DriverManager.getConnection(url,user,password);
            return true;
        } catch (ClassNotFoundException e) {
            System.err.print("数据库加载失败！");
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通用查询
     * @param sql
     * @param params
     * @return
     */
    public ResultSet executeQuery(String sql,Object[] params) throws SQLException {

        //打开数据库连接
        if(openConnection()){

            //通过传入的sql语句创建了sql操作对象
            this.pstat = this.conn.prepareStatement(sql);

            //对sql语句的占位符进行替换
            for(int i=0;i<params.length;i++){
                this.pstat.setObject(i+1,params[i]);
            }

            //执行sql语句
            return this.pstat.executeQuery();
        }

        return null;
    }

    /**
     * 通用的增删改
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public int  executeUpdate(String sql,Object[] params) throws SQLException {

        //打开数据库连接
        if(openConnection()){

            //通过传入的sql语句创建了sql操作对象
            this.pstat = this.conn.prepareStatement(sql);

            //对sql语句的占位符进行替换
            for(int i=0;i<params.length;i++){
                this.pstat.setObject(i+1,params[i]);
            }

            //执行sql语句
            return this.pstat.executeUpdate();
        }

        return 0;
    }

    /**
     *关闭数据库资源
     */
    public void closeConnection(){
        try{
            if(this.rs!=null){
                this.rs.close();
            }
            if(this.pstat!=null){
                this.pstat.close();
            }
            if(this.conn!=null){
                this.conn.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
