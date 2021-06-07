package command.subcommand;

import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import plugin.EasyPollPlugin;
import plugin.Poll;
import util.DurationUtil;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static command.PollCommand.NO_ARGUMENTS;
import static constant.EasyPollPermission.INFO_COUNTS;
import static java.lang.String.format;
import static org.bukkit.ChatColor.*;
import static util.CommandUtil.message;
import static util.CommandUtil.messageError;

/**
 * @author funkyFangs
 */
public class InfoSubcommand extends PollSubcommand
{
    public InfoSubcommand(@NotNull EasyPollPlugin plugin)
    {
        super("/poll info <name>", plugin);
    }

    @Override
    public boolean onCommand(@NotNull String[] arguments, @NotNull CommandSender sender)
    {
        int length = arguments.length;
        boolean result = length == 1;
        if (!result)
        {
            messageError(length > 1 ? "Too many arguments!" : "Not enough arguments!", sender);
        }
        else
        {
            String name = arguments[0];
            Poll poll = getPlugin().getPolls().get(name);
            if (poll == null)
            {
                messageError("There is no poll named '" + RED + name + RESET + "'!", sender);
            }
            else
            {
                Duration timeRemaining = poll.getDuration().minus(Duration.between(poll.getStartTime(), Instant.now()));

                message("Prompt: " + WHITE + poll.getPrompt(), GOLD, sender);
                message("Time remaining: " + WHITE + DurationUtil.toString(timeRemaining), GOLD, sender);

                Map<String, Integer> votes = poll.getVotes();
                if (sender.hasPermission(INFO_COUNTS.toString()))
                {
                    int winningCount = votes.values().stream().mapToInt(Integer::intValue).max().orElse(0);
                    boolean hasVotes = winningCount != 0;
                    int width = votes.keySet().stream().mapToInt(String::length).max().orElse(0);

                    for (Map.Entry<String, Integer> choice : votes.entrySet())
                    {
                        int count = choice.getValue();
                        sender.sendMessage(format("%s  - %s%-" + width + "s : %s%d", GOLD, WHITE, choice.getKey(),
                                                  hasVotes && count == winningCount ? GREEN : RED, count));
                    }
                }
                else
                {
                    for (String choice : votes.keySet())
                    {
                        sender.sendMessage(GOLD + "  - " + WHITE + choice);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(@NotNull String[] arguments, @NotNull CommandSender sender)
    {
        Set<String> names = getPlugin().getPolls().keySet();
        switch (arguments.length)
        {
            case 0:
                return new ArrayList<>(names);
            case 1:
                return StringUtil.copyPartialMatches(arguments[0], names, new ArrayList<>());
            default:
                return NO_ARGUMENTS;
        }
    }
}