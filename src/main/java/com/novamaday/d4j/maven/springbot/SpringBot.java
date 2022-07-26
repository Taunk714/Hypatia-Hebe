package com.novamaday.d4j.maven.springbot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.rest.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBot {
    public static void main(String[] args) {
        //Start spring application
        new SpringApplicationBuilder(SpringBot.class)
            .build()
            .run(args);
    }

//    @Value("${discord.bot.token}")
    String token = System.getenv("discord.bot.token");

    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return DiscordClientBuilder.create(/*System.getenv("BOT_TOKEN")*/token).build()
            .gateway()
            .setInitialPresence(ignore -> ClientPresence.online(ClientActivity.listening("to /commands")))
            .login()
            .block();
    }

    @Bean
    public RestClient discordRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }
}
