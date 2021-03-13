package org.charlie.service;

import org.apache.dubbo.config.annotation.DubboReference;
import org.charlie.WmsService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * 订单服务
 *
 * @author Charlie
 * @date 2021/3/13
 */

@Service
public class OrderService {

    @DubboReference
    private WmsService wmsService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    Logger logger = Logger.getLogger(OrderService.class.getName());

    private final String INSERT_SQL = "INSERT INTO t_order_0 (id, seller_id, user_id, user_address_id, goods_id, item_id, state, buyer_message, logistics_code, payment, payment_time, expire_time, create_time, update_time) VALUES (?,1, ?, 1, ?, 1, 1, 'buyer_message', '3246987634207802', 123, NOW(), NOW(), NOW(), NOW())";

    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Integer userId, Long orderId, Integer goodsId) {
        logger.info("creating order....");
        jdbcTemplate.update(INSERT_SQL, orderId, userId, goodsId);
        wmsService.reduceInventory(goodsId);
    }

}
