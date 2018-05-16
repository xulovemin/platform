package com.jc.platform.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class BosonKeyUtils {

    private Logger logger = LoggerFactory.getLogger(BosonKeyUtils.class);

    public List<Map<String, Object>> initBosonKeyList() {
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("bosonkey.yml");
        List<Map<String, Object>> bosonKeys = yaml.load(inputStream);
        CacheUtils.put("bosonKeys", bosonKeys);
        return bosonKeys;
    }

    public String getTopBosonKey() {
        String key = "";
        List<Map<String, Object>> bosonKeys = (List<Map<String, Object>>) CacheUtils.get("bosonKeys");
        if (bosonKeys == null) {
            bosonKeys = initBosonKeyList();
        }
        for (Map<String, Object> bosonKey : bosonKeys) {
            if ("0".equals(String.valueOf(bosonKey.get("state")))) {
                key = (String) bosonKey.get("key");
                break;
            }
        }
        return key;
    }

    public void updateTopBosonKey(String key) {
        List<Map<String, Object>> bosonKeys = (List<Map<String, Object>>) CacheUtils.get("bosonKeys");
        if (bosonKeys == null) {
            bosonKeys = initBosonKeyList();
        }
        for (Map<String, Object> bosonKey : bosonKeys) {
            if (key.equals(String.valueOf(bosonKey.get("key")))) {
                bosonKey.put("key", key);
                bosonKey.put("state", 1);
            }
        }
        logger.info(JsonUtil.java2Json(bosonKeys));
        CacheUtils.put("bosonKeys", bosonKeys);
    }
}
