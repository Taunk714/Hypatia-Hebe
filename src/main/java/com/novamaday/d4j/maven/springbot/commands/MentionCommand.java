package com.novamaday.d4j.maven.springbot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public interface MentionCommand {

    String getName();

    Mono<Void> handle(MessageCreateEvent event);


}
