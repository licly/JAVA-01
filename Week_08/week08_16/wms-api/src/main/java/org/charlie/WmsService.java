package org.charlie;

import org.dromara.hmily.annotation.Hmily;

/**
 * 库存服务API
 *
 * @author Charlie
 * @date 2021/3/13
 */

public interface WmsService {

    @Hmily
    boolean reduceInventory(Integer goodsId);
}
