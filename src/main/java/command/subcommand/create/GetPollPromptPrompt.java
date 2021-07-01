package command.subcommand.create;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static command.subcommand.CreateSubcommand.PROMPT;
import static org.bukkit.ChatColor.GOLD;

public class GetPollPromptPrompt extends StringPrompt
{
    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext context)
    {
        return GOLD + "What is the poll prompt?";
    }

    @Nullable
    @Override
    public Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input)
    {
        context.setSessionData(PROMPT, input);
        return new GetPollChoicesPrompt(context);
    }
}