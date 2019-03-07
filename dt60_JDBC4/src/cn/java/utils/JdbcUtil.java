/**
 * Project Name:dt60_JDBC2
 * File Name:JdbcUtil.java
 * Package Name:cn.java.utils
 * Date:下午5:31:21
 * Copyright (c) 2018, bluemobi All Rights Reserved.
 *
*/

package cn.java.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Description: JdbcUtil工具类工具类<br/>
 * Date: 下午5:31:21 <br/>
 * 
 * @author 丁鹏
 * @version
 * @see
 */
public class JdbcUtil {

    private static String driver = null;

    private static String url = null;

    private static String username = null;

    private static String password = null;

    static {
        try {
            Properties props = new Properties();
            InputStream ins = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            props.load(ins);
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            username = props.getProperty("username");
            password = props.getProperty("password");
            // 加载驱动
            Class.forName(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * 
     * @throws Exception
     */
    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("getConn()....发生错误");
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭资源
     * 
     * @throws Exception
     */
    public static void close(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            System.out.println("关闭ResultSet失败");
            e.printStackTrace();
        } finally {
            try {
                if (st != null)
                    st.close();
            } catch (SQLException e) {
                System.out.println("Statement对象关闭失败");
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    System.out.println("Connection对象关闭失败");
                    e.printStackTrace();
                }
            }

        }

    }

}
