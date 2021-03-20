package org.charlie.service;

import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * 账户B
 *
 * @author Charlie
 * @date 2021/3/20
 */

@Service
@DubboService
@Transactional(rollbackFor = Exception.class)
public class UserbServiceImpl implements UserbService {

    private static Logger logger = Logger.getLogger(UserbServiceImpl.class.getName());

    @Resource
    private JdbcTemplate jdbcTemplate;

    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Override
    public void forex(Integer userId, Integer rmb, Integer dollar) {
        // 扣除账户B人民币额度
        jdbcTemplate.update("UPDATE user SET rmb_account_balance = rmb_account_balance - ? WHERE id = ?", rmb, userId);
        // 冻结账户B人民币
        jdbcTemplate.update("UPDATE frozen_assets SET rmb = rmb + ? WHERE user_id = ?", rmb, userId);
    }

    public void confirm(Integer userId, Integer rmb, Integer dollar) {
        // 账户B增加美元额度
        jdbcTemplate.update("UPDATE user SET dollar_account_balance = dollar_account_balance + ? WHERE id = ?", dollar, userId);
        // 账户B解冻额度
        jdbcTemplate.update("UPDATE frozen_assets SET rmb = rmb - ? WHERE user_id = ?", rmb, userId);
        logger.info("账户B兑换成功。。。");
    }

    public void cancel(Integer userId, Integer rmb, Integer dollar) {
        // 解账户B冻人民币额度
        jdbcTemplate.update("UPDATE frozen_assets SET rmb = rmb - ? WHERE id = ?", rmb, userId);
        // 账户B恢复人民币额度
        jdbcTemplate.update("UPDATE user SET rmb_account_balance = rmb_account_balance + ? WHERE user_id = ?", rmb, userId);

        logger.info("账户B兑换失败。。。");
    }
}
