package command.subcommand;

import command.subcommand.vote.GetChoicePrompt;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import plugin.EasyPollPlugin;
import plugin.Poll;

import java.util.*;

import static command.PollCommand.NO_ARGUMENTS;
import static constant.EasyPollPermission.VOTE_UNLIMITED;
import static java.util.stream.Collectors.toSet;
import static org.bukkit.ChatColor.*;
import static plugin.EasyPollPlugin.PREFIX;
import static util.CommandUtil.message;
import static util.CommandUtil.messageError;

/**
 * @author funkyFangs
 */
public class VoteSubcommand extends PollSubcommand
{
    public static final String POLL = "poll";
    public static final String CHOICE = "choice";

    public VoteSubcommand(@NotNull EasyPollPlugin plugin)
    {
        super("/poll vote <name> [choice]", plugin);
    }

    @Override
    public boolean onCommand(@NotNull String[] arguments, @NotNull CommandSender sender)
    {
        int length = arguments.length;
        boolean result = length >= 1;
        if (!result)
        {
            messageError("Not enough arguments!", sender);
        }
        else
        {
            String name = arguments[0];
            Poll poll = getPlugin().getPolls().get(name);
            if (poll == null)
            {
                messageError("There is no poll named '" + RED + name + RESET + "'!", sender);
            }
            else if (sender instanceof Player && !sender.hasPermission(VOTE_UNLIMITED)
                     && !poll.getVoters().add(((Player) sender).getUniqueId()))
            {
                messageError("You have already voted for this poll!", sender);
            }
            else if (length == 1)
            {
                if (sender instanceof Player || sender instanceof ConsoleCommandSender)
                {
                    Map<Object, Object> sessionData = new HashMap<>();
                    sessionData.put(POLL, poll);

                    new ConversationFactory(getPlugin()).withPrefix(context -> PREFIX)
                                                        .withEscapeSequence("cancel")
                                                        .withLocalEcho(false)
                                                        .withFirstPrompt(new GetChoicePrompt())
                                                        .withInitialSessionData(sessionData)
                                                        .buildConversation((Conversable) sender)
                                                        .begin();
                }
                else
                {
                    messageError("You cannot use this command from here!", sender);
                }
            }
            else
            {
                String choice = String.join(" ", Arrays.copyOfRange(arguments, 1, length));
                if (poll.getVotes().computeIfPresent(choice, (key, value) -> value + 1) == null)
                {
                    messageError("There is no choice for '" + RED + choice + RESET + "' in this poll!", sender);
                }
                else
                {
                    message("Successfully voted for '" + WHITE + choice + RESET + "'!", GOLD, sender);
                }
            }
        }
        return result;
    }

    @Override
    public List<String> onTabComplete(@NotNull String[] arguments, @NotNull CommandSender sender)
    {
        int length = arguments.length;
        if (length <= 2)
        {
            Map<String, Poll> polls = getPlugin().getPolls();
            Set<String> names = sender instanceof Player && !sender.hasPermission(VOTE_UNLIMITED)
                                        ? polls.entrySet()
                                               .stream()
                                               .filter(e -> !e.getValue()
                                                              .getVoters()
                                                              .contains(((Player) sender).getUniqueId()))
                                               .map(Map.Entry::getKey)
                                               .collect(toSet())
                                        : polls.keySet();

            switch (length)
            {
                case 0:
                    return new ArrayList<>(names);
                case 1:
                    return StringUtil.copyPartialMatches(arguments[0], names, new ArrayList<>());
                case 2:
                    Poll poll = polls.get(arguments[0]);
                    if (poll != null)
                    {
                        return StringUtil.copyPartialMatches(arguments[1], poll.getVotes().keySet(), new ArrayList<>());
                    }
            }
        }
        return NO_ARGUMENTS;
    }
}