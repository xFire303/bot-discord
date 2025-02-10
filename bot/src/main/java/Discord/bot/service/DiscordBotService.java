package Discord.bot.service;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscordBotService {

    private JDA jda;

    private final Dotenv dotenv = Dotenv.load();

    @Autowired
    public void DiscordService(JDA jda) {
        this.jda = jda;
    }

    public void sendMessageToThread(String message) {
        String channelId = dotenv.get("DISCORD_CHANNEL_ID");
        String threadId = dotenv.get("DISCORD_THREAD_ID");

        TextChannel channel = jda.getTextChannelById(channelId);
        if (channel != null) {
            ThreadChannel thread = channel.getThreadChannels().stream()
                    .filter(t -> t.getId().equals(threadId))
                    .findFirst()
                    .orElse(null);

            if (thread != null) {
                thread.sendMessage(message).queue(messageQueue ->{
                    messageQueue.addReaction(Emoji.fromUnicode("👍")).queue();
                });
            } else {
                System.err.println("Thread non trovato!");
            }
        } else {
            System.err.println("Canale non trovato!");
        }
    }
}
