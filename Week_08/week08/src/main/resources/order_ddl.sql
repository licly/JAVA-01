CREATE TABLE t_order_%d (
`id` bigint(20) unsigned NOT NULL COMMENT '订单编号',
`seller_id` int(11) NOT NULL COMMENT '商家id',
`user_id` int(11) NOT NULL COMMENT '用户id',
`user_address_id` int(11) NOT NULL COMMENT '收货地址',
`goods_id` int(11) NOT NULL COMMENT '商品id',
`item_id` int(11) DEFAULT NULL COMMENT 'sku id',
`state` tinyint(4) NOT NULL COMMENT '订单状态，1：支付成功，2：未支付，3：已取消',
`buyer_message` varchar(255) DEFAULT NULL COMMENT '买家留言',
`logistics_code` varchar(32) DEFAULT NULL COMMENT '物流单号',
`payment` decimal(10,0) DEFAULT NULL COMMENT '实付金额',
`payment_time` datetime DEFAULT NULL COMMENT '付款时间',
`expire_time` datetime DEFAULT NULL COMMENT '过期时间',
`create_time` datetime NOT NULL COMMENT '创建时间',
`update_time` datetime NOT NULL COMMENT '修改时间',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单表';