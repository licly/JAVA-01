package org.charlie.sharding;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.charlie.po.Order;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * 15节课作业：对前面的订单表数据进行水平分库分表，拆分2个库，每个库16张表。并在新结构在演示常见的增删改查操作
 *
 * @author Charlie
 * @date 2021/3/12
 */
public class ShardingDemo {

    static final String DB1_URL = "jdbc:mysql://localhost:3306/java0?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
    static final String DB2_URL = "jdbc:mysql://localhost:3306/java1?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
    static final String USER = "root";
    static final String PWD = "root";
    static final String INSERT_SQL = "INSERT INTO t_order (seller_id, user_id, user_address_id, goods_id, item_id, state, buyer_message, logistics_code, payment, payment_time, expire_time, create_time, update_time) VALUES (1, ?, 1, ?, 1, 1, 'buyer_message', '3246987634207802', 123, '2021-03-06 08:40:32', '2021-03-06 08:40:35', '2021-03-06 08:40:38', '2021-03-06 08:40:41')";

    static DataSource dataSource;

    /**
     * 创建分库分表策略
     */
    private static void init() throws SQLException {
        ShardingRuleConfiguration ruleConfig = new ShardingRuleConfiguration();
        // 设置分库策略
        ruleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", getDbAlgorithm());
        // 设置分表策略
        ruleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", getTableAlgorithm());
        // 设置主键生成策略
        ruleConfig.getKeyGenerators().put("snowflake", getKeyGenerator());
        // 添加order表规则
        ruleConfig.getTables().add(getOrderRule());

        dataSource = ShardingSphereDataSourceFactory.createDataSource(getDataSources(), Collections.singletonList(ruleConfig), getProps());
    }

    private static Map<String, DataSource> getDataSources() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        MysqlDataSource dataSource1 = new MysqlDataSource();
        dataSource1.setURL(DB1_URL);
        dataSource1.setUser(USER);
        dataSource1.setPassword(PWD);
        dataSourceMap.put("java0", dataSource1);

        MysqlDataSource dataSource2 = new MysqlDataSource();
        dataSource2.setURL(DB2_URL);
        dataSource2.setUser(USER);
        dataSource2.setPassword(PWD);
        dataSourceMap.put("java1", dataSource2);

        return dataSourceMap;
    }

    private static ShardingTableRuleConfiguration getOrderRule() {
        ShardingTableRuleConfiguration orderRule = new ShardingTableRuleConfiguration("t_order", "java${0..1}.t_order_${0..15}");
        orderRule.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("user_id", "dbShardingAlgorithm"));
        orderRule.setTableShardingStrategy(new StandardShardingStrategyConfiguration("goods_id", "tableShardingAlgorithm"));
        orderRule.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration("id", "snowflake"));
        return orderRule;
    }

    private static ShardingSphereAlgorithmConfiguration getDbAlgorithm() {
        Properties dbAlgorithmProp = new Properties();
        dbAlgorithmProp.setProperty("algorithm-expression", "java${user_id % 2}");
        return new ShardingSphereAlgorithmConfiguration("INLINE", dbAlgorithmProp);
    }

    private static ShardingSphereAlgorithmConfiguration getTableAlgorithm() {
        Properties tableAlgorithmProp = new Properties();
        tableAlgorithmProp.setProperty("algorithm-expression", "t_order_${goods_id % 16}");
        return new ShardingSphereAlgorithmConfiguration("INLINE", tableAlgorithmProp);
    }

    private static Properties getProps() {
        Properties props = new Properties();
        props.setProperty("sql-show", "true");
        return props;
    }

    private static ShardingSphereAlgorithmConfiguration getKeyGenerator() {
        Properties snowflakeProperties = new Properties();
        snowflakeProperties.setProperty("worker-id", "123");
        return new ShardingSphereAlgorithmConfiguration("SNOWFLAKE", snowflakeProperties);
    }

    public static void insert() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
        for (int i = 0; i < 100; i++) {
            ps.setInt(1, i);
            ps.setInt(2, i);
            ps.executeUpdate();
        }
        connection.close();
    }

    /**
     * 查询所有数据
     */
    public static void queryAll() throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM t_order ORDER BY goods_id");
        ResultSet rs = ps.executeQuery();
        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getLong(1));
            order.setSellerId(rs.getInt(2));
            order.setUserId(rs.getInt(3));
            order.setUserAddress_id(rs.getInt(4));
            order.setGoodsId(rs.getInt(5));
            order.setItemId(rs.getInt(6));
            order.setState(rs.getByte(7));
            order.setBuyerMessage(rs.getString(8));
            order.setLogisticsCode(rs.getString(9));
            order.setPayment(rs.getBigDecimal(10));
            orders.add(order);
        }
        orders.forEach(System.out::println);

        connection.close();
    }

    /**
     * 刪除order表所有数据
     */
    public static void deleteAll() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM t_order");
        connection.close();
    }

    public static void main(String[] args) throws SQLException {
        init();
        // insert();
        // deleteAll();
        queryAll();
    }

}
