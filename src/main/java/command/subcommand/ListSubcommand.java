package command.subcommand;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import plugin.EasyPollPlugin;

import java.util.List;
import java.util.Set;

import static command.PollCommand.NO_ARGUMENTS;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.WHITE;
import static util.CommandUtil.message;
import static util.CommandUtil.messageError;

/**
 * @author funkyFangs
 */
public class ListSubcommand extends PollSubcommand
{
    public ListSubcommand(@NotNull EasyPollPlugin plugin)
    {
        super("/poll list", plugin);
    }

    @Override
    public boolean onCommand(@NotNull String[] arguments, @NotNull CommandSender sender)
    {
        int length = arguments.length;
        boolean result = length == 0;
        if (!result)
        {
            messageError("Too many arguments!", sender);
        }
        else
        {
            Set<String> pollNames = getPlugin().getPolls().keySet();
            if (pollNames.isEmpty())
            {
                message("No polls!", GOLD, sender);
            }
            else
            {
                message("Current polls:", GOLD, sender);
                for (String name : pollNames)
                {
                    sender.sendMessage(GOLD + " - " + WHITE + name);
                }
            }
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(@NotNull String[] arguments, @NotNull CommandSender sender)
    {
        return NO_ARGUMENTS;
    }
}