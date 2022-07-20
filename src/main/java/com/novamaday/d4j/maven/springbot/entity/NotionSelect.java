package com.novamaday.d4j.maven.springbot.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotionSelect extends NotionParameter{

    private String selection;

    private HashMap<String, Map<String,String>> map = new HashMap<>();

    public NotionSelect(String value) {
        super("select");
        map.put(getType(), new HashMap<>());
        put(value);
    }

    public void put(String selection){
        map.get(getType()).put("name", selection);
    }

    public HashMap<String, Map<String, String>> getMap() {
        return map;
    }
}
