package com.jc.platform.core.listener;

import com.jc.platform.common.utils.BosonKeyUtils;
import com.jc.platform.common.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationReadyEvent> {

    protected Logger logger = LoggerFactory.getLogger(ApplicationStartedEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        BosonKeyUtils bosonKeyUtils = new BosonKeyUtils();
        List<Map<String, Object>> bosonKeys = bosonKeyUtils.initBosonKeyList();
        logger.info(JsonUtil.java2Json(bosonKeys));
    }
}
