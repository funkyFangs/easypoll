package command.subcommand;

import command.PollCloseRunnable;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import plugin.EasyPollPlugin;
import plugin.Poll;

import java.util.*;

import static command.PollCommand.NO_ARGUMENTS;
import static constant.EasyPollPermission.CLOSE_OTHERS;
import static java.util.stream.Collectors.toSet;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.RESET;
import static util.CommandUtil.getUUID;
import static util.CommandUtil.messageError;

/**
 * @author funkyFangs
 */
public class CloseSubcommand extends PollSubcommand
{
    public CloseSubcommand(@NotNull EasyPollPlugin plugin)
    {
        super("/poll close <name>", plugin);
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
            else if (sender instanceof Player player && !sender.hasPermission(CLOSE_OTHERS)
                     && !Objects.equals(player.getUniqueId(), poll.getCreator()))
            {
                messageError("You do not have permission to close someone else's poll!", sender);
            }
            else
            {
                PollCloseRunnable closeRunnable = poll.getCloseRunnable();
                closeRunnable.run();
                closeRunnable.cancel();
            }
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(@NotNull String[] arguments, @NotNull CommandSender sender)
    {
        int length = arguments.length;
        if (length > 1)
        {
            return NO_ARGUMENTS;
        }
        else
        {
            Map<String, Poll> polls = getPlugin().getPolls();
            UUID senderId = getUUID(sender);
            Set<String> names = sender instanceof Player && !sender.hasPermission(CLOSE_OTHERS)
                                        ? polls.entrySet()
                                               .stream()
                                               .filter(e -> Objects.equals(senderId, e.getValue().getCreator()))
                                               .map(Map.Entry::getKey)
                                               .collect(toSet())
                                        : polls.keySet();

            return length == 0
                           ? new ArrayList<>(names)
                           : StringUtil.copyPartialMatches(arguments[0], names, new ArrayList<>());
        }
    }
}