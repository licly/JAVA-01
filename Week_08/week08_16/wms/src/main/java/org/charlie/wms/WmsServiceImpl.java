package org.charlie.wms;

import com.mysql.cj.util.TimeUtil;
import org.apache.dubbo.config.annotation.DubboService;
import org.charlie.WmsService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * 库存服务
 *
 * @author Charlie
 * @date 2021/3/13
 */

@DubboService
@Service
public class WmsServiceImpl implements WmsService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    Logger logger = Logger.getLogger(WmsService.class.getName());

    @Transactional(rollbackFor = Exception.class)
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Override
    public boolean reduceInventory(Integer goodsId) {
        logger.info("reduce inventory...");
        jdbcTemplate.update("UPDATE item SET stock_num = stock_num-1 WHERE goods_id = ?", goodsId);
        return false;
    }

    public void confirm(Integer goodsId) {
        logger.info("reduce inventory success");
    }

    public void cancel(Integer goodsId) {
        logger.info("reduce inventory failed");
        jdbcTemplate.update("UPDATE item SET stock_num = stock_num+1 WHERE goods_id = ?", goodsId);
    }
}
