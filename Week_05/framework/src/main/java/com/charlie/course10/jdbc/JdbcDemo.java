package com.charlie.course10.jdbc;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import com.mysql.cj.jdbc.result.ResultSetImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用 JDBC 原生接口，实现数据库的增删改查操作
 *
 * @author Charlie
 * @date 2021/2/6
 */
public class JdbcDemo {

    private ConnectionFactory factory;

    public JdbcDemo(ConnectionFactory factory) {
        this.factory = factory;
    }

    public void addUser() throws SQLException {
        try (Connection con = factory.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO user (`name`, address, arrive_time)values(?, ?, ?)");
            ps.setString(1, "charlie-" + RandomUtil.randomString(10));
            ps.setString(2, "beijing");
            ps.setObject(3, LocalDateTime.now());
            ps.execute();
        }
    }

    /**
     * 提取结果集到实体类
     */
    private List<User> retrieveResult(ResultSetImpl rs) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getLong(1));
            user.setName(rs.getString(2));
            user.setAddress(rs.getString(3));
            user.setArriveTime(rs.getLocalDateTime(4));
            users.add(user);
        }

        return users;
    }

    private void queryAll() throws SQLException {
        StopWatch watch = new StopWatch();
        watch.start();
        try (Connection con = factory.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user");
            ps.setFetchSize(10000);
            ResultSetImpl rs = (ResultSetImpl) ps.executeQuery();
            retrieveResult(rs).forEach(System.out::println);
        }
        watch.stop();
        System.out.println(watch.getTotalTimeMillis());
    }

    private void deleteById() throws SQLException {
        try (Connection con = factory.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM user LIMIT 1");
            ps.execute();
        }
    }

    private void updateUser() throws SQLException {
        try (Connection con = factory.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE user SET name=? ORDER BY id DESC LIMIT 1");
            ps.setString(1, "java");
            ps.execute();
        }
    }

    public static void main(String[] args) throws SQLException {
        JdbcDemo jdbc = new JdbcDemo(new ConnectionFactory());
        jdbc.addUser();
        jdbc.deleteById();
        jdbc.queryAll();
        jdbc.updateUser();
    }
}
