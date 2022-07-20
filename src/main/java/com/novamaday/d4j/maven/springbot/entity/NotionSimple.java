package com.novamaday.d4j.maven.springbot.entity;

import java.util.HashMap;
import java.util.Map;

public class NotionSimple extends NotionParameter{
    private Map<String, String> map = new HashMap<>();
    public NotionSimple(String type, String value) {
        super(type);
        put(value);
    }

    public void put(String value){
        map.put(getType(), value);
    }

    public Map<String, String> getMap() {
        return map;
    }
}
