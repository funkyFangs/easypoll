package command.subcommand.create;

import command.PollCloseRunnable;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import plugin.EasyPollPlugin;
import plugin.Poll;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import static command.subcommand.CreateSubcommand.NAME;
import static org.bukkit.ChatColor.GOLD;
import static util.CommandUtil.getUUID;
import static util.DurationUtil.parse;

/**
 * @author funkyFangs
 */
public class CreatePollPrompt implements Prompt
{
    private static final int TICKS_PER_SECOND = 20;
    private static final Duration MAX_DURATION = Duration.ofHours(12);
    private static final int MIN_NUM_CHOICES = 2;
    private static final int MAX_NUM_CHOICES = 8;

    @NotNull
    private String promptText;

    private String prompt;
    private Duration duration;
    private final Set<String> choices = new LinkedHashSet<>();

    private int numChoices;

    public CreatePollPrompt()
    {
        promptText = GOLD + "Enter a valid prompt (enter 'cancel' to cancel):";
    }

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        return promptText;
    }

    @Override
    public boolean blocksForInput(@NotNull ConversationContext context)
    {
        return true;
    }

    /**
     * Sequentially gets the prompt, duration, and choices for a {@link Poll}, updating the prompt as necessary.
     * When finished, creates the poll and transitions to a {@link NotifyPollCreatedPrompt}.
     * @param context the current context of the {@link Prompt}
     * @param input the next input messaged by the {@link Conversable}
     * @return this if not all inputs have been accepted, or a new {@link NotifyPollCreatedPrompt} otherwise
     */
    @NotNull
    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, String input)
    {
        int size = choices.size();
        if (prompt == null)
        {
            prompt = input;
            promptText = GOLD + "Enter a valid duration (e.g. 1 hour 10 minutes):";
        }
        else if (duration == null)
        {
            Duration duration = parse(input);
            if (duration != null && !duration.isZero() && duration.compareTo(MAX_DURATION) <= 0)
            {
                this.duration = duration;
                promptText = GOLD + "Enter number of choices:";
            }
        }
        else if (numChoices == 0)
        {
            try
            {
                int numChoices = Integer.parseInt(input);
                if (numChoices >= MIN_NUM_CHOICES && numChoices <= MAX_NUM_CHOICES)
                {
                    this.numChoices = numChoices;
                    promptText = GOLD + "Enter choice #" + (size + 1) + ':';
                }
            }
            catch (NumberFormatException ignored)
            {
            }
        }
        else if (size < numChoices)
        {
            if (choices.add(input))
            {
                // Choices successfully added new element, so size increases by 1
                if (++size == numChoices)
                {
                    finalizeResults(context);
                    return new NotifyPollCreatedPrompt();
                }
                promptText = GOLD + "Enter choice #" + (size + 1) + ":";
            }
        }
        return this;
    }

    /**
     * Creates the {@link Poll} and {@link PollCloseRunnable} from the prompt, duration, and choices of this
     * {@link Prompt}.
     * @param context the current context of the {@link Prompt}
     */
    private void finalizeResults(ConversationContext context)
    {
        if (context.getSessionData(NAME) instanceof String name && context.getPlugin() instanceof EasyPollPlugin plugin)
        {
            UUID creatorId = getUUID((CommandSender) context.getForWhom());

            PollCloseRunnable closeRunnable = new PollCloseRunnable(plugin, name);
            Poll poll = new Poll(creatorId, prompt, duration, choices, closeRunnable);

            plugin.getPolls().put(name, poll);

            closeRunnable.runTaskLater(plugin, duration.getSeconds() * TICKS_PER_SECOND);
        }
    }
}