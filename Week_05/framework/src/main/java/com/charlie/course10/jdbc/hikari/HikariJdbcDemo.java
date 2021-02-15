package com.charlie.course10.jdbc.hikari;

import cn.hutool.core.util.RandomUtil;
import com.charlie.course10.jdbc.ConnectionFactory;
import com.charlie.course10.jdbc.JdbcDemo;
import com.charlie.course10.jdbc.User;
import com.mysql.cj.jdbc.result.ResultSetImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/2/6
 */
public class HikariJdbcDemo {

    private HikariConnectionPool pool;

    public HikariJdbcDemo(HikariConnectionPool pool) {
        this.pool = pool;
    }

    public void addUser() throws SQLException {
        try (Connection con = pool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO user (`name`, address, arrive_time)values(?, ?, ?)");
            ps.setString(1, "charlie-" + RandomUtil.randomString(10));
            ps.setString(2, "beijing");
            ps.setObject(3, new Date());
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
            rs.updateString(2, "licly");
            rs.updateRow();
        }

        return users;

    }

    private void queryAll() throws SQLException {
        try (Connection con = pool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user");
            ps.execute();
            ResultSetImpl rs = (ResultSetImpl) ps.getResultSet();
            retrieveResult(rs).forEach(System.out::println);
        }
    }

    private void deleteById() throws SQLException {
        try (Connection con = pool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM user LIMIT 1");
            ps.execute();
        }
    }

    private void updateUser() throws SQLException {
        try (Connection con = pool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE user SET name=? ORDER BY id DESC LIMIT 1");
            ps.setString(1, "java");
            ps.execute();
        }
    }

    public static void main(String[] args) throws SQLException {
        HikariJdbcDemo jdbc = new HikariJdbcDemo(new HikariConnectionPool());
        jdbc.addUser();
        jdbc.deleteById();
        jdbc.queryAll();
        jdbc.updateUser();
    }
}
