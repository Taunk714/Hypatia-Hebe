package com.novamaday.d4j.maven.springbot.entity;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class Idea extends NotionPage{
    Map<String,Map<String, Object>> map = new HashMap<>();

    public Idea(String db, String publisher, String content, String submitter, String url, String[] tag) {
        map.put("parent", new HashMap<>());
        map.put("properties", new HashMap<>());
        map.get("parent").put("type","database_id");
        map.get("parent").put("database_id", db);
        map.get("properties").put("发布者", new NotionSelect(publisher).getMap());
        map.get("properties").put("正文", new NotionText(content).getMap());
        map.get("properties").put("提交人", new NotionSelect(submitter).getMap());
        map.get("properties").put("DiscordURL", new NotionSimple("url", url).getMap());
        map.get("properties").put("tag", new NotionMultiSelection(tag).getMap());
//        map.get("properties").put("提交时间", new)
    }

    public String getJSONString(){
        return JSON.toJSONString(map);
    }
}
