package command.subcommand.create;

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
        var name = context.getSessionData(NAME);
        message(context.getForWhom() instanceof Player player
                        ? WHITE + player.getDisplayName() + RESET + " has opened a new poll named '" + WHITE
                          + name + RESET + "'!"
                        : "A new poll named '" + WHITE + name + RESET + "' has been opened!",
                GOLD);
        return END_OF_CONVERSATION;
    }

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        return GOLD + "Poll '" + WHITE + context.getSessionData(NAME) + GOLD
               + "' has successfully been created! You can chat again.";
    }
}