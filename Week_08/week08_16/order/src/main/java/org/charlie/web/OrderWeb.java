package org.charlie.web;

import org.charlie.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/3/13
 */

@RestController
@RequestMapping("/v1/order")
public class OrderWeb {

    @Resource
    private OrderService service;

    @RequestMapping("/createOrder")
    public void createOrder(Integer userId, Long orderId, Integer goodsId) {
        service.createOrder(userId, orderId, goodsId);
    }
}
