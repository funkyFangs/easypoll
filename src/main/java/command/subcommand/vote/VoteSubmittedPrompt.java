package command.subcommand.vote;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static command.subcommand.VoteSubcommand.CHOICE;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.WHITE;

/**
 * @author funkyFangs
 */
public class VoteSubmittedPrompt extends MessagePrompt
{
    @Nullable
    @Override
    protected Prompt getNextPrompt(@NotNull ConversationContext context)
    {
        return END_OF_CONVERSATION;
    }

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        return GOLD + "Successfully voted for '" + WHITE + context.getSessionData(CHOICE) + GOLD + "'!";
    }
}