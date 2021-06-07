package command;

import command.subcommand.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import plugin.EasyPollPlugin;

import java.util.*;

import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.RESET;
import static util.CommandUtil.*;

/**
 * @author funkyFangs
 */
public class PollCommand implements CommandExecutor, TabCompleter
{
    private static final String CLOSE = "close";
    private static final String CREATE = "create";
    private static final String INFO = "info";
    private static final String LIST = "list";
    private static final String VOTE = "vote";

    private static final List<String> SUBCOMMANDS = Arrays.asList(CLOSE, CREATE, INFO, LIST, VOTE);
    public static final List<String> NO_ARGUMENTS = Collections.emptyList();

    private final Map<String, PollSubcommand> registry;

    public PollCommand(@NotNull EasyPollPlugin plugin)
    {
        Map<String, PollSubcommand> temp = new HashMap<>();

        temp.put(CLOSE, new CloseSubcommand(plugin));
        temp.put(CREATE, new CreateSubcommand(plugin));
        temp.put(INFO, new InfoSubcommand(plugin));
        temp.put(LIST, new ListSubcommand(plugin));
        temp.put(VOTE, new VoteSubcommand(plugin));

        registry = Collections.unmodifiableMap(temp);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
                             @NotNull String[] args)
    {
        boolean result = args.length != 0;
        if (result)
        {
            String subcommandName = args[0];
            PollSubcommand subcommand = registry.get(subcommandName);
            if (subcommand == null)
            {
                messageError(RED + subcommandName + RESET + " is not a valid command name!", sender);
                result = false;
            }
            else if (!subcommand.onCommand(removeElement(args, 0), sender))
            {
                message("Usage: " + subcommand.getUsage(), sender);
            }
        }
        return result;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias,
                                      @NotNull String[] args)
    {
        switch (args.length)
        {
            case 0:
                return SUBCOMMANDS;
            case 1:
                return StringUtil.copyPartialMatches(args[0], SUBCOMMANDS, new ArrayList<>());
            default:
                PollSubcommand subcommand = registry.get(args[0]);
                return subcommand == null
                               ? NO_ARGUMENTS
                               : subcommand.onTabComplete(removeElement(args, 0), sender);
        }
    }
}