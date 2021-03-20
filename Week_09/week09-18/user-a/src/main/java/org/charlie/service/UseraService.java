package org.charlie.service;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * 账户A
 *
 * @author Charlie
 * @date 2021/3/20
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UseraService {

    private static Logger logger = Logger.getLogger(UseraService.class.getName());

    @DubboReference
    private UserbService userbService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void forex(Integer userId, Integer rmb, Integer dollar) {
        // 冻结账户A美元额度
        jdbcTemplate.update("UPDATE frozen_assets SET dollar = dollar + ? WHERE user_id = ?", dollar, userId);
        // 扣除账户A美元冻结额度
        jdbcTemplate.update("UPDATE user SET dollar_account_balance = dollar_account_balance - ? WHERE id = ?", dollar, userId);

        userbService.forex(userId, rmb, dollar);

        // 账户A增加人民币额度
        jdbcTemplate.update("UPDATE user SET rmb_account_balance = rmb_account_balance + ? WHERE id = ?", rmb, userId);
        // 账户A清空冻结额度
        jdbcTemplate.update("UPDATE frozen_assets SET dollar = dollar - ? WHERE user_id = ?", dollar, userId);

        logger.info("账户A兑换成功。。。");
    }

}
