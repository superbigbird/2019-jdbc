/**
 * Project Name:dt60_JDBC4
 * File Name:UserDaoImpl.java
 * Package Name:cn.java.dao.impl
 * Date:下午2:06:23
 * Copyright (c) 2018, bluemobi All Rights Reserved.
 *
*/

package cn.java.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import cn.java.utils.JdbcUtil;

/**
 * Description: dao层类-操作数据库<br/>
 * Date: 下午2:06:23 <br/>
 * 
 * @author 丁鹏
 * @version
 * @see
 */



//哈哈，张三到此一游
public class UserDaoImpl {
    private QueryRunner qr = new QueryRunner();

    private Connection conn = JdbcUtil.getConn();

    /**
     * DbUtils.closeQuietly(conn)：关闭连接
     */
    @Test
    public void selectAll() throws SQLException {
        List<Map<String, Object>> mapList = qr.query(conn, "SELECT * FROM users", new MapListHandler());
        // 关闭3个对象(ResultSet、Statement、Connection)
        DbUtils.closeQuietly(conn);
        // 遍历结果
        mapList.forEach((temp) -> {
            System.out.println(temp);
        });
    }

    @Test
    public void getUserById() throws SQLException {
        Object[] objects = qr.query(conn, "select * from users where id=1044", new ArrayHandler());
        DbUtils.closeQuietly(conn);
        for (Object o : objects) {
            System.out.print(o + "\t");
        }
    }

    @Test
    public void getUsers() throws SQLException {
        List<Object[]> objList = qr.query(conn, "select * from users", new ArrayListHandler());
        DbUtils.closeQuietly(conn);
        objList.forEach((temp) -> {
            System.out.println(Arrays.toString(temp));
        });
    }

    @Test
    public void getUserIsLogin() throws Exception {
        Map<String, Object> aMap = qr.query(conn, "SELECT COUNT(*) AS flag FROM users WHERE username=? AND PASSWORD=?",
                new MapHandler(), new Object[] { "老马", "888" });
        DbUtils.closeQuietly(conn);
        Long obj = (Long) aMap.get("flag");
        if (obj == 1) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }

    }

    @Test
    public void getUserIsLogin2() throws Exception {
        Long flag = qr.query(conn, "SELECT COUNT(*) AS flag FROM users WHERE username=? AND PASSWORD=?",
                new ScalarHandler<Long>(), new Object[] { "老马", "888" });
        DbUtils.closeQuietly(conn);
        if (flag == 1) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }

    }

    /**
     * ResultSetHandler：结果集处理器，告诉Dbutils框架最终返回的结果使用何种类型来封装<br>
     * 常见的结果集处理器：<br>
     * (1)、BeanHandler：处理JavaBean数据类型<br>
     * (2)、MapHandeler(推荐)：处理Map集合类型<br>
     * (3)、ArrayHander：使用一个数组对象来封装一条记录<br>
     * (4)、BeanListHandler：处理多条记录，每条记录用JavaBean来封装<br>
     * (5)、MapListHander(推荐)：....<br>
     * (6)、ArrayListHandler：封装多条记录<br>
     * (7)、ScalarHandler处理器：当返回只有一条记录，并且该记录只有一个字段时，很方便
     * 
     * @throws SQLException
     */
    @Test
    public void getUserInfo() throws Exception {
        String username = qr.query(conn, "SELECT username FROM users WHERE username=? AND PASSWORD=?",
                new ScalarHandler<String>(), new Object[] { "老马", "888" });
        DbUtils.closeQuietly(conn);
        System.out.println(username);

    }
}
