package org.charlie.service;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.charlie.annotation.ReadOnly;
import org.charlie.po.Order;
import org.charlie.config.SimpleDataSourceRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/3/6
 */

@Component
public class DbService {

    @Resource
    private SimpleDataSourceRegistry registry;

    public List<Order> execute(String sql) throws SQLException, IntrospectionException {
        DataSource dataSource = registry.getDataSource(sql);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSetImpl rs = (ResultSetImpl) statement.executeQuery(sql);
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
            order.setPaymentTime(rs.getLocalDateTime(11));
            order.setExpireTime(rs.getLocalDateTime(12));
            order.setCreateTime(rs.getLocalDateTime(13));
            order.setUpdateTime(rs.getLocalDateTime(14));
            orders.add(order);
        }
        return orders;
    }
}
