package command;

import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plugin.EasyPollPlugin;

import static org.bukkit.ChatColor.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static plugin.EasyPollPlugin.PREFIX;

/**
 * @author funkyFangs
 */
@ExtendWith(MockitoExtension.class)
public class PollCommandTest
{
    @Mock
    private EasyPollPlugin plugin;

    @Mock
    private Player player;

    @Mock
    private ConsoleCommandSender console;

    @Mock
    private Command command;

    @Test
    void testOnCommandPlayerNotEnoughArguments()
    {
        PollCommand pollCommand = new PollCommand(plugin);

        boolean result = pollCommand.onCommand(player, command, "", new String[0]);

        assertThat(result, is(false));
        verifyNoInteractions(player);
    }

    @Test
    void testOnCommandPlayerInvalidCommand()
    {
        PollCommand pollCommand = new PollCommand(plugin);
        String[] arguments = {"DNE"};

        boolean result = pollCommand.onCommand(player, command, "", arguments);

        assertThat(result, is(false));
        verify(player).sendMessage(PREFIX + DARK_RED + RED + "DNE" + RESET + DARK_RED
                                   + " is not a valid command name!");
    }
}