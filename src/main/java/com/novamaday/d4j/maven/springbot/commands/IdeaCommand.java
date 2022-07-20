package com.novamaday.d4j.maven.springbot.commands;

import com.novamaday.d4j.maven.springbot.entity.Idea;
import com.novamaday.d4j.maven.springbot.services.notiondb;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import discord4j.discordjson.json.MessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class IdeaCommand implements MentionCommand{

//    @Value("${notion.database.idea}")
    String ideaDB = System.getenv("notion.database.idea");

    @Autowired
    private notiondb notiondb;

    @Override
    public String getName() {
        return "idea";
    }

    @Override
    public Mono<Void> handle(MessageCreateEvent event) {
        String response;
        try {
            String json = parseIdea(event);
//            response = notiondb.create(json);
        } catch (AssertionError e){
            response = "You can't mention bot directly, please reply to a message";
        }

        System.out.println(event);
//        event.getMessage().getChannel().block().createMessage(response).block();
        return null;
    }

    private String parseIdea(MessageCreateEvent event) throws AssertionError{
        assert event.getMessage().getReferencedMessage().isPresent();
        User user = event.getMessage().getAuthor().get();
        String submitter = user.getUsername() + "#" + user.getDiscriminator();
        String message = event.getMessage().getContent();

        User user1 = event.getMessage().getReferencedMessage().get().getAuthor().get();
        String publisher = user1.getUsername() + "#" + user1.getDiscriminator();
        String content = event.getMessage().getReferencedMessage().get().getContent();
//        event.getMessage().getComponen
//        event.getMessage().
        List<String> tags = new ArrayList<>();
        String[] s = message.split(" ");
        String next = "";
        for (String s1 : s) {
            if (s1.startsWith("@")){
                continue;
            }
            if (s1.equals("tag")){
                next = "tag";
                continue;
            }
            if (next.equals("tag")){
                tags.add(s1);
            }
        }


        Idea idea = new Idea(ideaDB, publisher, content, submitter, getMessageLink(event), tags.toArray(new String[0]));
        try {
            notiondb.create(idea.getJSONString());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        event.getMessage().getChannel().block().createMessage("成功添加idea至notion").block();
        return idea.getJSONString();
    }

    private String getMessageLink(MessageCreateEvent event){
        MessageData data = event.getMessage().getReferencedMessage().get().getData();
        return "https://discord.com/channels/" + event.getGuildId().get().asString() + "/" + data.channelId() + "/" + data.id();
    }
}
