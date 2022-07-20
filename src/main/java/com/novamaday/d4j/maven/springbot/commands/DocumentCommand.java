package com.novamaday.d4j.maven.springbot.commands;

import com.alibaba.fastjson.JSON;
import com.novamaday.d4j.maven.springbot.entity.Doc;
import com.novamaday.d4j.maven.springbot.services.notiondb;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;
import discord4j.discordjson.json.MessageData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DocumentCommand implements MentionCommand{

//    @Value("${notion.database.document}")
    String docDB = System.getenv("notion.database.document");

    @Autowired
    private notiondb notiondb;

    @Override
    public String getName() {
        return "document";
    }

    @Override
    public Mono<Void> handle(MessageCreateEvent event) {
        String response;
        try {
            parseDocument(event);
//            response = notiondb.create(json.toString());
        } catch (AssertionError e){
            response = "You can't mention bot directly, please reply to a message";
        }

        System.out.println(event);
//        event.getMessage().getChannel().block().createMessage(response).block();
        return null;
    }

    private JSON parseDocument(MessageCreateEvent event) throws AssertionError{
        assert event.getMessage().getReferencedMessage().isPresent();
        User user = event.getMessage().getAuthor().get();
        String submitter = user.getUsername() + "#" + user.getDiscriminator();
        String message = event.getMessage().getContent();
        String title = "";
        String coreURL = "";

        User user1 = event.getMessage().getReferencedMessage().get().getAuthor().get();
        String publisher = user1.getUsername() + "#" + user1.getDiscriminator();

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

        String content = event.getMessage().getReferencedMessage().get().getContent();
        String[] s2 = content.split(" ");
        Pattern compile = Pattern.compile("(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]");
        for (String sin : s2) {
            Matcher matcher = compile.matcher(sin);
            System.out.println(matcher.find());
            coreURL = matcher.group(0);
            try {
                Document document = Jsoup.connect(coreURL).get();
                title = document.title();
                Doc doc = new Doc(docDB, title,"链接", coreURL, publisher, content, submitter, getMessageLink(event), tags.toArray(new String[0]));
                notiondb.create(doc.getJSONString());
                event.getMessage().getChannel().block().createMessage("成功添加document至notion").block();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }


        return null;
    }

    private String getMessageLink(MessageCreateEvent event){
        MessageData data = event.getMessage().getReferencedMessage().get().getData();
        return "https://discord.com/channels/" + event.getGuildId().get().asString() + "/" + data.channelId() + "/" + data.id();
    }
}
