package command;

import org.bukkit.Server;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import plugin.EasyPollPlugin;

/**
 * @author funkyFangs
 */
@ExtendWith(MockitoExtension.class)
public class ClosePollRunnableTest
{
    @Mock
    private Server server;

    @Mock
    private EasyPollPlugin plugin;
}