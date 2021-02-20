package cn.smbms.utils;

import sun.security.krb5.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件工具类
 *
 * @author Tang
 */
public class ConfigUtil {

    //配置文件对象（实现单例模式）
    private static Properties  properties = new Properties();

    //当项目启动时 初始化配置文件的加载
    static {
        //通过反射特性找到配置所在的目录
        InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream(
                "database.properties");

        //把IO流中的数据导入到配置文件对象
        try {
            properties.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过key值获得配置文件中的value值
     * @param key
     * @return
     */
    public static String getValue(String key){
        return properties.getProperty(key);
    }
}
