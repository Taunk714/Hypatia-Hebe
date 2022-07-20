package com.novamaday.d4j.maven.springbot.entity;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends NotionPage{
    Map<String, Map<String, Object>> map = new HashMap<>();

    public Feedback(String db, String content, String tag, String submitter) {
        map.put("parent", new HashMap<>());
        map.put("properties", new HashMap<>());
        map.get("parent").put("type","database_id");
        map.get("parent").put("database_id", db);
        map.get("properties").put("content", new NotionTitle(content).getMap());
        map.get("properties").put("tag", new NotionSelect(tag).getMap());
        map.get("properties").put("提交人", new NotionSelect(submitter).getMap());

//        map.get("properties").put("提交时间", new)
    }

    public String getJSONString(){
        return JSON.toJSONString(map);
    }

}
