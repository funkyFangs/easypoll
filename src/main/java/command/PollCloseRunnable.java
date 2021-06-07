package command;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import plugin.EasyPollPlugin;
import plugin.Poll;

import java.util.Map;
import java.util.UUID;

import static org.bukkit.ChatColor.*;
import static util.CommandUtil.message;

/**
 * @author funkyFangs
 */
public class PollCloseRunnable extends BukkitRunnable
{
    private final EasyPollPlugin plugin;
    private final String pollName;

    public PollCloseRunnable(@NotNull EasyPollPlugin plugin, @NotNull String pollName)
    {
        this.plugin = plugin;
        this.pollName = pollName;
    }

    @Override
    public void run()
    {
        Map<String, Poll> polls = plugin.getPolls();
        Poll poll = polls.get(pollName);
        if (poll == null)
        {
            plugin.getLogger().severe("Tried to close non-existent poll - '" + pollName + "'");
        }
        else
        {
            Server server = plugin.getServer();
            messageFinalResults(poll, server.getConsoleSender());

            UUID creatorId = poll.getCreator();
            if (creatorId != null)
            {
                Player player = server.getPlayer(creatorId);
                if (player != null)
                {
                    messageFinalResults(poll, player);
                }
            }

            for (UUID playerId : poll.getVoters())
            {
                if (playerId != creatorId)
                {
                    Player player = server.getPlayer(playerId);
                    if (player != null)
                    {
                        messageFinalResults(poll, player);
                    }
                }
            }

            polls.remove(pollName);
        }
    }

    private void messageFinalResults(@NotNull Poll poll, @NotNull CommandSender sender)
    {
        message("Final results for '" + WHITE + pollName + RESET + "':", GOLD, sender);
        message("Prompt: " + WHITE + poll.getPrompt(), GOLD, sender);

        Map<String, Integer> votes = poll.getVotes();
        int winningCount = votes.values().stream().mapToInt(Integer::intValue).max().orElse(0);
        boolean hasVotes = winningCount != 0;
        int width = votes.keySet().stream().mapToInt(String::length).max().orElse(0);

        for (Map.Entry<String, Integer> choice : votes.entrySet())
        {
            int count = choice.getValue();
            sender.sendMessage(String.format("%s  - %-" + width + "s : %s%d", WHITE, choice.getKey(),
                                             hasVotes && count == winningCount ? GREEN : RED, count));
        }
    }
}