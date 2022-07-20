package com.novamaday.d4j.maven.springbot.entity;

import java.util.Map;

public abstract class NotionParameter {

    private String name;
    private String type;

    public NotionParameter(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public NotionParameter(String type) {
//        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
