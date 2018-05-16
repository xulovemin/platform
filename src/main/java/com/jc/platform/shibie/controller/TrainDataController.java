package com.jc.platform.shibie.controller;

import com.jc.platform.core.web.BaseController;
import com.jc.platform.shibie.domain.TrainData;
import com.jc.platform.shibie.service.TrainDataService;
import com.jc.platform.common.utils.JsonUtil;
import com.jc.platform.common.utils.NlpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TrainDataController extends BaseController {

    @Autowired
    private TrainDataService trainDataService;

    @RequestMapping("getTrainData")
    @ResponseBody
    public int getTrainData() {
        List<TrainData> trainDatas = trainDataService.getThousand();
        System.out.println(trainDatas.size());
        int i = 1;
        for (TrainData trainData : trainDatas) {
            String content = trainData.getContent();
            Map<String, List<String>> map = NlpUtils.posonNerStr(content);
            if (map.isEmpty()) {
                break;
            } else {
                trainData.setPerson(JsonUtil.java2Json(map.get("person_name")));
                trainData.setLocation(JsonUtil.java2Json(map.get("location")));
                trainData.setOrg(JsonUtil.java2Json(map.get("org_name")));
                trainData.setCompany(JsonUtil.java2Json(map.get("company_name")));
                trainDataService.update(trainData);
                logger.info("第" + i + "条数据");
                i++;
            }
        }
        return i;
    }
}