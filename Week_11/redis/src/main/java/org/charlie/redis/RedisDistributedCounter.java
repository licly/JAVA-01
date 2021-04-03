package org.charlie.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/4/3
 */

@Component
public class RedisDistributedCounter {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    public void decrementBy(String key) {
        redisTemplate.opsForValue().decrement(key);
    }

    public void incrementBy(String key) {
        redisTemplate.opsForValue().increment(key);
    }

}
