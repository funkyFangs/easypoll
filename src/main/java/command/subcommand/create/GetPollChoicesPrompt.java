package command.subcommand.create;

import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashSet;
import java.util.Set;

import static command.subcommand.CreateSubcommand.CHOICES;
import static org.bukkit.ChatColor.GOLD;
import static org.bukkit.ChatColor.RED;

/**
 * @author funkyFangs
 */
public class GetPollChoicesPrompt implements Prompt
{
    private static final String DONE = "done";
    private static final int MAX_CHOICES = 8;
    private static final int MIN_CHOICES = 2;

    private final Set<String> choices = new LinkedHashSet<>();

    public GetPollChoicesPrompt(@NotNull ConversationContext context)
    {
        context.setSessionData(CHOICES, choices);
    }

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        String basePrompt = GOLD + "What is prompt #" + choices.size();
        if (choices.size() >= MIN_CHOICES)
        {
            basePrompt += " (or '" + DONE + "' to end)";
        }
        return basePrompt + "?";
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context)
    {
        return true;
    }

    @Nullable
    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input)
    {
        Prompt nextPrompt = this;
        int size = choices.size();
        Conversable forWhom = context.getForWhom();
        if (DONE.equals(input))
        {
            if (size >= MIN_CHOICES)
            {
                nextPrompt = new CreatePollPrompt();
            }
            else
            {
                forWhom.sendRawMessage(RED + "This poll does not have enough choices!");
            }
        }
        else if (choices.contains(input))
        {
            forWhom.sendRawMessage(RED + "You have already added this choice to the poll!");
        }
        else if (size == MAX_CHOICES)
        {
            nextPrompt = new CreatePollPrompt();
        }
        else
        {
            choices.add(input);
        }
        return nextPrompt;
    }
}