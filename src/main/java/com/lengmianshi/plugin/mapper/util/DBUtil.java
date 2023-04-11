package com.lengmianshi.plugin.mapper.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

@Slf4j
@Data
public class DBUtil {
    private String driverClass;
    private String url;
    private String user;
    private String password;
    private String database;

    public DBUtil(String driverClass, String url, String user, String password, String database) {
        this.driverClass = driverClass;
        this.url = url;
        this.user = user;
        this.password = password;
        this.database = database;
    }

    /**
     * 获取数据库连接
     */
    public Connection getConnection() throws Exception {
        Class.forName(this.driverClass);
        return DriverManager.getConnection(this.url, this.user, this.password);
    }

    /**
     * 关闭数据库连接
     *
     * @param rs
     * @param st
     * @param con
     */
    public void closeConnection(ResultSet rs, Statement st, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            log.error("关闭数据库连接发生异常：" + e.getMessage(), e);
        }
    }


}
