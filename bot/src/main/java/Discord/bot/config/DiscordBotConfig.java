package Discord.bot.config;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordBotConfig {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public JDA jda() {
        String botToken = dotenv.get("DISCORD_BOT_TOKEN");

        if (botToken == null || botToken.isBlank()) {
            throw new IllegalStateException("DISCORD_BOT_TOKEN non è definito nel file .env");
        }

        try {
            return JDABuilder.createDefault(botToken)
                    .build()
                    .awaitReady();
        } catch (Exception e) {
            throw new IllegalStateException("Errore durante l'inizializzazione del bot Discord", e);
        }
    }
}
