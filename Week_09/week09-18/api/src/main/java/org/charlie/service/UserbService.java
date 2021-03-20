package org.charlie.service;

import org.dromara.hmily.annotation.Hmily;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/3/20
 */

public interface UserbService {

    @Hmily
    void forex(Integer userId, Integer rmb, Integer dollar);
}
