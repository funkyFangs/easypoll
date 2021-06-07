package util;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.bukkit.ChatColor.BLUE;
import static org.bukkit.ChatColor.WHITE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static plugin.EasyPollPlugin.PREFIX;
import static testutil.ThrowsA.throwsA;

/**
 * @author funkyFangs
 */
@ExtendWith(MockitoExtension.class)
public class CommandUtilTest
{
    @Mock
    private ConsoleCommandSender console;

    @Mock
    private Player player;

    @Test
    void testMessageConsole()
    {
        CommandUtil.message("This is a test", console);
        verify(console).sendMessage(PREFIX + WHITE + "This is a test");
    }

    @Test
    void testMessagePlayer()
    {
        CommandUtil.message("This is a test", player);
        verify(player).sendMessage(PREFIX + WHITE + "This is a test");
    }

    @Test
    void testMessageDefaultColorConsole()
    {
        CommandUtil.message("This is a test", BLUE, console);
        verify(console).sendMessage(PREFIX + BLUE + "This is a test");
    }

    @Test
    void testMessageDefaultColorPlayer()
    {
        CommandUtil.message("This is a test", BLUE, player);
        verify(player).sendMessage(PREFIX + BLUE + "This is a test");
    }

    @Test
    void testRemoveElement()
    {
        String[] array = {"A", "B", "C"};
        assertThat(CommandUtil.removeElement(array, 1), is(arrayContaining("A", "C")));
    }

    @Test
    void testRemoveElementEnd()
    {
        String[] array = {"A", "B", "C"};
        assertThat(CommandUtil.removeElement(array, 2), is(arrayContaining("A", "B")));
    }

    @Test
    void testRemoveElementBeginning()
    {
        String[] array = {"A", "B", "C"};
        assertThat(CommandUtil.removeElement(array, 0), is(arrayContaining("B", "C")));
    }

    @Test
    void testRemoveElementOutOfBounds()
    {
        String[] array = {"A", "B", "C"};
        assertThat(() -> CommandUtil.removeElement(array, 3), throwsA(IndexOutOfBoundsException.class));
    }

    @Test
    void testGetUUIDConsole()
    {
        assertThat(CommandUtil.getUUID(console), is(nullValue()));
    }

    @Test
    void testGetUUIDPlayer()
    {
        UUID uuid = UUID.fromString("00000000-00-00-00-000000000000");

        when(player.getUniqueId()).thenReturn(uuid);

        assertThat(CommandUtil.getUUID(player), is(uuid));
        verify(player).getUniqueId();
    }
}