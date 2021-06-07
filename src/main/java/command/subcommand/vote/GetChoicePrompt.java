package command.subcommand.vote;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.Poll;

import java.util.Iterator;

import static command.subcommand.VoteSubcommand.CHOICE;
import static constant.EasyPollPermission.POLL;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.WHITE;

/**
 * @author funkyFangs
 */
public class GetChoicePrompt extends ValidatingPrompt
{
    @Override
    protected boolean isInputValid(@NotNull ConversationContext context, @NotNull String input)
    {
        Object poll = context.getSessionData(POLL);
        return poll instanceof Poll
               && ((Poll) poll).getVotes().computeIfPresent(input, (key, value) -> value + 1) != null;
    }

    @Nullable
    @Override
    protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input)
    {
        context.setSessionData(CHOICE, input);
        return new VoteSubmittedPrompt();
    }

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        Poll poll = (Poll) context.getSessionData(POLL);

        StringBuilder builder = new StringBuilder(GOLD.toString()).append("Please enter a valid choice:\n");
        if (poll != null)
        {
            Iterator<String> iterator = poll.getVotes().keySet().iterator();

            boolean hasNext = iterator.hasNext();
            while (hasNext)
            {
                builder.append(GOLD).append(" - ").append(WHITE).append(iterator.next());
                if (hasNext = iterator.hasNext())
                {
                    builder.append("\n");
                }
            }
            return builder.toString();
        }

        return builder.toString();
    }
}