package org.charlie.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author Charlie
 * @date 2021/4/3
 */

@Component
public class RedisDistributedLock {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public boolean lock(String key){
        while (!redisTemplate.opsForValue().setIfAbsent(key, "true", Duration.ofSeconds(5))) {
            try {
                TimeUnit.MICROSECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean unlock(String key) {
        return redisTemplate.delete(key);
    }
}
