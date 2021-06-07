package command.subcommand;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import plugin.EasyPollPlugin;

import java.util.List;

/**
 * @author funkyFangs
 */
public abstract class PollSubcommand
{
    private final EasyPollPlugin plugin;
    private final String usage;

    public PollSubcommand(String usage, EasyPollPlugin plugin)
    {
        this.plugin = plugin;
        this.usage = usage;
    }

    public String getUsage()
    {
        return usage;
    }

    protected EasyPollPlugin getPlugin()
    {
        return plugin;
    }

    public abstract boolean onCommand(@NotNull String[] arguments, @NotNull CommandSender sender);

    public abstract List<String> onTabComplete(@NotNull String[] arguments, @NotNull CommandSender sender);
}