package com.novamaday.d4j.maven.springbot.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotionText extends NotionParameter{
    private List<Map<String, Object>> contents = new ArrayList<>();
    private HashMap<String, List<Map<String,Object>>> map = new HashMap<>();

    public NotionText(String value) {
        super("rich_text");
        map.put(getType(), contents);
        add(value);
    }

    public NotionText add(String content){
        HashMap<String,Object> inner = new HashMap<>();
        inner.put("type", "text");
        HashMap<String, String> mapi = new HashMap<>();
        mapi.put("content", content);
        inner.put("text", mapi);
        contents.add(inner);
        return this;
    }

    public HashMap<String, List<Map<String, Object>>> getMap() {
        return map;
    }
}
