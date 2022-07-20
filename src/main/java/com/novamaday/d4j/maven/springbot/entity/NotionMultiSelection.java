package com.novamaday.d4j.maven.springbot.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotionMultiSelection extends NotionParameter{
    private List<Map<String, String>> selections = new ArrayList<>();
    private HashMap<String, List<Map<String,String>>> map = new HashMap<>();
    public NotionMultiSelection(String[] tags) {
        super("multi_select");
//        map.put(name, new HashMap<>());
        map.put(getType(), selections);
        for (String tag : tags) {
            add(tag);
        }
    }

    public NotionMultiSelection add(String selection){
        HashMap<String,String> inner = new HashMap<>();
        inner.put("name", selection);
        selections.add(inner);
        return this;
    }


    public HashMap<String, List<Map<String, String>>> getMap() {
        return map;
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
