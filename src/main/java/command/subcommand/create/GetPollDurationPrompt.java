package command.subcommand.create;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import util.DurationUtil;

import java.time.Duration;

import static command.subcommand.CreateSubcommand.DURATION;
import static org.bukkit.ChatColor.RED;

/**
 * @author funkyFangs
 */
public class GetPollDurationPrompt implements Prompt
{
    private static final Duration MAX_DURATION = Duration.ofHours(12);
    private static final Duration MIN_DURATION = Duration.ofSeconds(30);

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        return "What is the poll duration (e.g. 30 seconds, 1 hour 30 minutes)?";
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context)
    {
        return true;
    }

    @Nullable
    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input)
    {
        Duration duration = DurationUtil.parse(input);
        Conversable forWhom = context.getForWhom();
        if (duration == null)
        {
            forWhom.sendRawMessage(RED + "That is not a valid duration!");
        }
        else if (duration.compareTo(MAX_DURATION) > 0)
        {
            forWhom.sendRawMessage(RED + "That duration is too long!");
        }
        else if (duration.compareTo(MIN_DURATION) < 0)
        {
            forWhom.sendRawMessage(RED + "That duration is too short!");
        }
        else
        {
            context.setSessionData(DURATION, duration);
            return new GetPollPromptPrompt();
        }
        return this;
    }
}