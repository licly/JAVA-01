package com.charlie.course10.jdbc;

import java.io.PrintWriter;
import java.sql.*;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/2/8
 */
public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/jdbc?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.setLogWriter(new PrintWriter(System.out));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
