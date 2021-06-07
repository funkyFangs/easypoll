package plugin;

import command.PollCommand;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.RESET;

/**
 * @author funkyFangs
 */
public class EasyPollPlugin extends JavaPlugin
{
    public static final String PREFIX = GOLD + "[EasyPoll] " + RESET;

    private final Map<String, Poll> polls = new HashMap<>();

    public Map<String, Poll> getPolls()
    {
        return polls;
    }

    @Override
    public void onEnable()
    {
        Logger logger = getLogger();
        logger.info("EasyPoll enabled!");

        PluginCommand pluginCommand = getCommand("poll");
        if (pluginCommand == null)
        {
            logger.severe("Failed to load /poll command!");
        }
        else
        {
            PollCommand pollCommand = new PollCommand(this);
            pluginCommand.setExecutor(pollCommand);
            pluginCommand.setTabCompleter(pollCommand);
        }
    }

    @Override
    public void onDisable()
    {
        getLogger().info("EasyPoll disabled");

        getServer().getScheduler().cancelTasks(this);

        polls.clear();
    }
}