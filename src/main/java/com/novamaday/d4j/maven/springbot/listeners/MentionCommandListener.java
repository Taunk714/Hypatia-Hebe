package com.novamaday.d4j.maven.springbot.listeners;

import com.novamaday.d4j.maven.springbot.commands.MentionCommand;
import com.novamaday.d4j.maven.springbot.commands.SlashCommand;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class MentionCommandListener {

    private final Collection<MentionCommand> commands;

    public MentionCommandListener(List<MentionCommand> mentionCommands, GatewayDiscordClient client) {
        commands = mentionCommands;

        client.on(MessageCreateEvent.class, this::handle).subscribe();
    }


    public Mono<Void> handle(MessageCreateEvent event) {
        //Convert our list to a flux that we can iterate through
//        Optional<Member> member = event.getMember();
        List<User> userMentions = event.getMessage().getUserMentions();
        boolean flag = false;
        for (User userMention : userMentions) {
            if (userMention.isBot()) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return null;
        }

        String content = event.getMessage().getContent();
        String[] s = content.split(" ");
        return Flux.fromIterable(commands)
            //Filter out all commands that don't match the name this event is for
            .filter(command -> (("."+ command.getName()).equals(s[0]) || ("."+command.getName()).equals(s[1])))
            //Get the first (and only) item in the flux that matches our filter
            .next()
            //Have our command class handle all logic related to its specific command.
            .flatMap(command -> command.handle(event));
    }
}
