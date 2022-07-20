package com.novamaday.d4j.maven.springbot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

@Component
public class notiondb {
        // !!!!! DO THIS FIRST !!!!!
        // Replace this constant with your Internal Integration Token
        // that you will find by following the instructions here: https://developers.notion.com/docs/getting-started
        private static final String TOKEN = "secret_XXX";

        // Replace this constant with a user id that exists
        private static final String USER_ID = "00000000-0000-0000-0000-000000000000";

        // Replace this constant with a database id that exists
        private static final String DATABASE_ID = "00000000-0000-0000-0000-000000000000";

        // Replace this constant with a page id that exists
        private static final String PAGE_ID = "00000000-0000-0000-0000-000000000000";

//        @Value("${notion.integration.key}")
        String notion_token = System.getenv("notion.integration.key");

        public String create(String properties) throws IOException, InterruptedException {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.notion.com/v1/pages"))
                .header("Authorization", "Bearer " + notion_token)
//                .header("Accept", "application/json")
                .header("Notion-Version", "2022-06-28")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(properties))
                .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
//            response.statusCode()
            return response.body();
        }



}


//
////
//curl -X POST https://api.notion.com/v1/pages \
//    -H 'Authorization: Bearer '"secret_TEiuTsYbYvieocWjr4CcAb8QagdEEJDvWswGyILvkIB"'' \
//    -H "Content-Type: application/json" \
//    -H "Notion-Version: 2022-06-28" \
//    --data '{
//    "parent": { "type": "database_id", "database_id": "dcb2fd124b114d349484887e6090cb5b" },
//    "properties": {
//        "tag": {
//            "multi_select": [
//                {
//                "name": "B"
//                },
//                {
//                "name": "C"
//                }
//            ]
//        },
//        "tag": {
//            "multi-select":[
//                {
//                    "name":"first"
//    },
//    {"name":"test"}]}
//    }
//}'
