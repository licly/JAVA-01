package com.charlie.course10.jdbc;

import cn.hutool.core.util.RandomUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * 使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
 *
 * @author Charlie
 * @date 2021/2/13
 */
public class JdbcBatchDemo {


    private ConnectionFactory factory;

    public JdbcBatchDemo(ConnectionFactory factory) {
        this.factory = factory;
    }

    /**
     * 批量插入数据
     */
    public void addUser() {
        Connection con = null;
        boolean autoCommit = false;
        try {
            con = factory.getConnection();
            autoCommit = con.getAutoCommit();
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement("INSERT INTO user (`name`, address, arrive_time)values(?, ?, ?)");
            for (int i = 0; i < 10000; i++) {
                ps.setString(1, "charlie-" + RandomUtil.randomString(10));
                ps.setString(2, "beijing");
                ps.setObject(3, LocalDateTime.now());
                ps.addBatch();
            }
            ps.executeBatch();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                con.setAutoCommit(autoCommit);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) throws SQLException {
        JdbcBatchDemo jdbc = new JdbcBatchDemo(new ConnectionFactory());
        jdbc.addUser();
    }
}
