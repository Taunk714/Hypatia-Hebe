package com.novamaday.d4j.maven.springbot.commands;

import com.novamaday.d4j.maven.springbot.entity.Feedback;
import com.novamaday.d4j.maven.springbot.services.notiondb;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;

@Component
public class BotFeedbackCommand implements SlashCommand{
    @Override
    public String getName() {
        return "feedback";
    }

//    @Value("${notion.database.feedback}")
    String feedbackdb = System.getenv("notion.database.feedback");

    @Autowired
    private notiondb notiondb;

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        List<ApplicationCommandInteractionOption> options = event.getOptions();
        long type = options.get(0).getValue().get().asLong();
        String tag = "";
        String response = "";
        if (type == 1){
            tag = "问题反馈";
            response += tag;
        } else{
            tag = "需求";
            response += tag;
        }
        response += "...";
        String content = options.get(1).getValue().get().asString();
        response += content;
        String username = event.getInteraction().getUser().getUsername();
        String discriminator = event.getInteraction().getUser().getDiscriminator();
        String submitter = username + "#" + discriminator;
        Feedback feedback = new Feedback(feedbackdb, content, tag, submitter);
        try {
            notiondb.create(feedback.getJSONString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return event.reply().withEphemeral(false).withContent(response);
    }
}
