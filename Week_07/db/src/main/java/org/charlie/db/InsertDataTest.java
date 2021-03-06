package org.charlie.db;

import com.google.common.base.Stopwatch;

import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * 插入数据测试
 *
 * @author Charlie
 * @date 2021/3/5
 */
public class InsertDataTest {

    private static String URL = "jdbc:mysql://localhost:3306/java01?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";

    private static String USER = "root";

    private static String PASSWORD = "root";

    private static Connection connection;

    private static PreparedStatement statement;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            statement = connection.prepareStatement("INSERT INTO `order` (seller_id, user_id, user_address_id, goods_id, item_id, state, buyer_message, logistics_code, payment, payment_time, expire_time, create_time, update_time) VALUES (1, 1, 1, 1, 1, 1, 'buyer_message', '3246987634207802', 123, '2021-03-06 08:40:32', '2021-03-06 08:40:35', '2021-03-06 08:40:38', '2021-03-06 08:40:41')");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        connection.setAutoCommit(false);

        Stopwatch stopwatch = Stopwatch.createStarted();

        singleInsert();
        batchInsert();
        batchInsert2();
        batchInsert3();

        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.MILLISECONDS));

        connection.setAutoCommit(true);
        statement.execute("TRUNCATE table `order`");
    }

    /**
     * 插入10万条数据耗时大约20s
     * @throws SQLException
     */
    private static void singleInsert() throws SQLException {
        for (int i = 1; i <= 100000; i++) {
            statement.execute();
        }
    }

    /**
     * 批量插入
     * batchSize:
     * 5：耗时大约13s
     * 10 耗时大约12s
     * 20：耗时大约12s
     * 30: 耗时大约12s
     * 50: 耗时大约12s
     * 100 耗时大约12s
     * 200 耗时大约12s
     * 500 耗时大约11s
     * 1000 耗时大约11.5s
     * 1500 耗时大约11.5s
     */
    private static void batchInsert() throws SQLException {
        for (int i = 1; i <= 100000; i++) {
            statement.addBatch();
            if (i % 1500 == 0) {
                statement.executeBatch();
            }
        }
    }

    /**
     * 单条SQL多条数据插入
     * 5条 ：耗时大约5s
     * 10条：耗时大约3s
     * 20条: 耗时大约2.5s
     */
    private static void batchInsert2() throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO `order` (seller_id, user_id, user_address_id, goods_id, item_id, state, buyer_message, logistics_code, payment, payment_time, expire_time, create_time, update_time) VALUES ");
        for (int i = 0; i < 20; i++) {
            sql.append("(1, 1, 1, 1, 1, 1, 'buyer_message', '3246987634207802', 123, '2021-03-06 08:40:32', '2021-03-06 08:40:35', '2021-03-06 08:40:38', '2021-03-06 08:40:41'),");
        }
        sql.deleteCharAt(sql.length() - 1);

        PreparedStatement ps = connection.prepareStatement(sql.toString());
        for (int i = 0; i <= 5000; i++) {
            ps.execute();
        }
    }

    /**
     * 单条SQL多条数据插入 + 批处理
     * batchSize
     * 10 2s
     * 20 2s
     * 50 2s
     * 100 2s
     */
    private static void batchInsert3() throws SQLException {
        StringBuilder sql = new StringBuilder("INSERT INTO `order` (seller_id, user_id, user_address_id, goods_id, item_id, state, buyer_message, logistics_code, payment, payment_time, expire_time, create_time, update_time) VALUES ");
        for (int i = 0; i < 20; i++) {
            sql.append("(1, 1, 1, 1, 1, 1, 'buyer_message', '3246987634207802', 123, '2021-03-06 08:40:32', '2021-03-06 08:40:35', '2021-03-06 08:40:38', '2021-03-06 08:40:41'),");
        }
        sql.deleteCharAt(sql.length() - 1);

        int batchSize = 100;
        PreparedStatement ps = connection.prepareStatement(sql.toString());
        for (int i = 1; i <= 10000; i++) {
            ps.addBatch();
            if (i % batchSize == 0) {
                ps.executeBatch();
            }
        }
    }

}
