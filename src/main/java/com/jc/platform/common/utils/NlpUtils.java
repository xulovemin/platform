package com.jc.platform.common.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class NlpUtils {

    private static Logger logger = LoggerFactory.getLogger(NlpUtils.class);

    public static final String NER_URL = "http://api.bosonnlp.com/ner/analysis";// 实体识别

    private static BosonKeyUtils bosonKeyUtils = new BosonKeyUtils();

    private static Map<String, List<String>> getList(HttpResponse<JsonNode> jsonResponse) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> person_names = new ArrayList<>();
        List<String> locations = new ArrayList<>();
        List<String> org_names = new ArrayList<>();
        List<String> company_names = new ArrayList<>();
        // 存储数据
        String jsonStr = jsonResponse.getBody().toString();
        List list = (List) JsonUtil.json2Java(jsonStr, List.class);
        if (list != null && list.size() > 0) {
            for (int j = 0; j < list.size(); j++) {
                Map<String, Object> Jsonmap = (Map<String, Object>) list.get(j);
                List<List<Object>> entity = (List<List<Object>>) Jsonmap
                        .get("entity");
                List<String> word = (List<String>) Jsonmap.get("word");
                for (List<Object> item : entity) {
                    if ("time".equals(item.get(2))) {
                        continue;
                    }
                    if ("job_title".equals(item.get(2))) {
                        continue;
                    }
                    if ("product_name".equals(item.get(2))) {
                        continue;
                    }
                    String keyWord = "";
                    int startKey = (Integer) item.get(0);
                    int endKey = (Integer) item.get(1);
                    for (int i = startKey; i < endKey; i++) {
                        keyWord += word.get(i);
                    }
                    if (40 < keyWord.length()) {
                        continue;
                    }
                    if ("location".equals(item.get(2))) {// 地理位置
                        locations.add(keyWord);
                    }
                    if ("person_name".equals(item.get(2))) {// 人名
                        person_names.add(keyWord);
                    }
                    if ("org_name".equals(item.get(2))) {// 机构名
                        org_names.add(keyWord);
                    }
                    if ("company_name".equals(item.get(2))) {// 公司名
                        company_names.add(keyWord);
                    }
                }
            }
        }
        locations = new ArrayList<>(new HashSet<>(locations));
        person_names = new ArrayList<>(new HashSet<>(person_names));
        org_names = new ArrayList<>(new HashSet<>(org_names));
        company_names = new ArrayList<>(new HashSet<>(company_names));
        map.put("location", locations);
        map.put("person_name", person_names);
        map.put("org_name", org_names);
        map.put("company_name", company_names);
        return map;
    }

    public static Map<String, List<String>> posonNerStr(String str) {
        str = str.replace(" ", "");
        List<String> list = new ArrayList<>();
        transFormLength(list, str, 4999);
        String[] strings = new String[list.size()];
        list.toArray(strings);
        Map<String, List<String>> map = new HashMap<>();
        try {
            String body = new JSONArray(strings).toString();
            String xToken = bosonKeyUtils.getTopBosonKey();
            if (!"".equals(xToken)) {
                HttpResponse<JsonNode> jsonResponse = Unirest.post(NER_URL)
                        .header("Accept", "application/json")
                        .header("X-Token", xToken).body(body).asJson();
                if (jsonResponse.getStatus() == 429) {
                    bosonKeyUtils.updateTopBosonKey(xToken);
                    return posonNerStr(str);
                } else if (jsonResponse.getStatus() == 200) {
                    map = getList(jsonResponse);
                }
                logger.info(JsonUtil.java2Json(map));
            } else {
                logger.info("今日免费key已用完，请明日再发");
            }
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return map;
        }
    }

    /**
     * 将文本切分定长
     *
     * @param list
     * @param content
     * @param length
     */
    public static void transFormLength(List<String> list, String content, int length) {
        if (content.length() > length) {
            String sub = content.substring(0, length);
            list.add(sub);
            transFormLength(list, content.substring(sub.length()), length);
        } else {
            list.add(content);
        }
    }

}
