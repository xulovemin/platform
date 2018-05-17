package com.jc.platform.shibie.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jc.platform.common.utils.JsonUtil;
import com.jc.platform.common.utils.NlpUtils;
import com.jc.platform.core.page.JPage;
import com.jc.platform.core.web.BaseController;
import com.jc.platform.shibie.domain.TrainData;
import com.jc.platform.shibie.service.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class TrainDataController extends BaseController {

    @Autowired
    private TrainDataService trainDataService;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "李旭");
        return "shibie/index";
    }

    @RequestMapping("/getTrainData")
    @ResponseBody
    public JPage getTrainData(JPage page) {
        logger.info("进入分页后台");
        PageHelper.offsetPage(page.getiDisplayStart(), page.getiDisplayLength());
        List<TrainData> trainDatas = trainDataService.getThousand();
        page.setPageInfo(new PageInfo(trainDatas));
        return page;
    }

    @RequestMapping("getTrainDataByBoson")
    @ResponseBody
    public int getTrainDataByBoson() {
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