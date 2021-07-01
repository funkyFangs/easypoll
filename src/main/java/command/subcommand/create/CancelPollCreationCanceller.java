package command.subcommand.create;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ExactMatchConversationCanceller;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.ChatColor.GOLD;

/**
 * Checks to see if a user enters the cancel token, and notifies them that they can
 * speak again if so.
 *
 * @author funkyFangs
 */
public class CancelPollCreationCanceller extends ExactMatchConversationCanceller
{
    private static final String CANCEL = "cancel";

    /**
     * Builds an ExactMatchConversationCanceller.
     */
    public CancelPollCreationCanceller()
    {
        super(CANCEL);
    }

    @Override
    public boolean cancelBasedOnInput(@NotNull ConversationContext context, @NotNull String input)
    {
        boolean result = super.cancelBasedOnInput(context, input);
        if (result)
        {
            context.getForWhom().sendRawMessage(GOLD + "Poll creation has been cancelled! You can talk again!");
        }
        return result;
    }
}