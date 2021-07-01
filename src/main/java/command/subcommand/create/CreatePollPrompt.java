package command.subcommand.create;

import command.PollCloseRunnable;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.EasyPollPlugin;
import plugin.Poll;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static command.subcommand.CreateSubcommand.*;
import static org.bukkit.ChatColor.*;
import static org.bukkit.ChatColor.RESET;
import static util.CommandUtil.getUUID;
import static util.CommandUtil.message;
import static util.DurationUtil.parse;

/**
 * @author funkyFangs
 */
public class CreatePollPrompt implements Prompt
{
    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        if (context.getSessionData(NAME) instanceof String name
            && context.getSessionData(DURATION) instanceof Duration duration
            && context.getSessionData(PROMPT) instanceof String prompt
            && context.getSessionData(CHOICES) instanceof Set<?> choicesReference
            && context.getPlugin() instanceof EasyPollPlugin plugin)
        {
            Map<String, Poll> polls = plugin.getPolls();
            if (polls.containsKey(name))
            {
                return RED + "Error: that poll already exists!";
            }
            else
            {
                @SuppressWarnings("unchecked")
                Set<String> choices = (Set<String>) choicesReference;
                UUID creatorId = context.getForWhom() instanceof Player player
                                         ? player.getUniqueId()
                                         : null;

                polls.put(name, new Poll(creatorId, prompt, duration, choices, new PollCloseRunnable(plugin, name)));

                return GOLD + "Poll '" + WHITE + name + GOLD + "' has successfully been created! You can chat again!";
            }
        }
        return RED + "Error: failed to create prompt due to unexpected issue!";
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context)
    {
        return false;
    }

    @NotNull
    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, String input)
    {
        // Announces that a poll has been created, and if by a player, who made it
        var name = context.getSessionData(NAME);
        message(context.getForWhom() instanceof Player player
                        ? WHITE + player.getDisplayName() + RESET + " has opened a new poll named '" + WHITE
                          + name + RESET + "'!"
                        : "A new poll named '" + WHITE + name + RESET + "' has been opened!",
                GOLD);
        return END_OF_CONVERSATION;
    }
}