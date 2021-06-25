package command.subcommand;

import command.subcommand.create.CreatePollPrompt;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.EasyPollPlugin;
import plugin.Poll;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static command.PollCommand.NO_ARGUMENTS;
import static constant.EasyPollPermission.CREATE_MULTIPLE;
import static plugin.EasyPollPlugin.PREFIX;
import static util.CommandUtil.messageError;

/**
 * @author funkyFangs
 */
public class CreateSubcommand extends PollSubcommand
{
    private static final String CANCEL = "cancel";
    public static final String NAME = "name";

    public CreateSubcommand(@NotNull EasyPollPlugin plugin)
    {
        super("/poll create <name>", plugin);
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
            EasyPollPlugin plugin = getPlugin();
            Map<String, Poll> polls = plugin.getPolls();
            String name = arguments[0];
            if (polls.containsKey(name))
            {
                messageError("A poll with this name already exists!", sender);
            }
            else if (sender instanceof Player player && !sender.hasPermission(CREATE_MULTIPLE)
                     && polls.values().stream().anyMatch(poll -> player.getUniqueId().equals(poll.getCreator())))
            {
                messageError("You can only have 1 poll at a time!", sender);
            }
            else if (sender instanceof Conversable conversable)
            {
                Map<Object, Object> sessionData = new HashMap<>();
                sessionData.put(NAME, name);

                new ConversationFactory(getPlugin()).withPrefix(context -> PREFIX)
                                                    .withEscapeSequence(CANCEL)
                                                    .withLocalEcho(false)
                                                    .withFirstPrompt(new CreatePollPrompt())
                                                    .withInitialSessionData(sessionData)
                                                    .buildConversation(conversable)
                                                    .begin();
            }
            else
            {
                messageError("You cannot use this command from here!", sender);
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