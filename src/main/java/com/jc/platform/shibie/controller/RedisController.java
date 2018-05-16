package com.jc.platform.shibie.controller;

import com.jc.platform.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController extends BaseController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "redis")
    public String redisTest(String key, String value) {
        String redisValue = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(redisValue)) {
            stringRedisTemplate.opsForValue().set(key, value);
            return "操作成功！";
        }

        if (!redisValue.equals(value)) {
            stringRedisTemplate.opsForValue().set(key, value);
            return "操作成功！";
        }
        return String.format("redis中已存在[key=%s,value=%s]的数据！", key, value);
    }
}
