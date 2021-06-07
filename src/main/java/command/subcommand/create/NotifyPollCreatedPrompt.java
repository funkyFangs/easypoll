package command.subcommand.create;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static command.subcommand.CreateSubcommand.NAME;
import static org.bukkit.ChatColor.*;
import static util.CommandUtil.message;

/**
 * @author funkyFangs
 */
public class NotifyPollCreatedPrompt extends MessagePrompt
{
    @Nullable
    @Override
    protected Prompt getNextPrompt(@NotNull ConversationContext context)
    {
        Conversable conversable = context.getForWhom();
        if (conversable instanceof Player)
        {
            message(WHITE + ((Player) conversable).getDisplayName() + RESET + " has opened a new poll named '"
                    + WHITE + context.getSessionData(NAME) + RESET + "'!", GOLD);
        }
        else
        {
            message("A new poll named '" + WHITE + context.getSessionData(NAME) + RESET + "' has been opened!",
                    GOLD);
        }
        return END_OF_CONVERSATION;
    }

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        return GOLD + "Poll '" + WHITE + context.getSessionData(NAME) + GOLD + "' has successfully been created!";
    }
}