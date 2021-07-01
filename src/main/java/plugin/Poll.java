package plugin;

import command.PollCloseRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * @author funkyFangs
 */
public class Poll
{
    private final Map<String, Integer> votes = new LinkedHashMap<>();
    private final Set<UUID> voters = new HashSet<>();

    private final UUID creator;
    private final String prompt;
    private final Instant startTime;
    private final Duration duration;
    private final PollCloseRunnable closeRunnable;

    public Poll(@Nullable UUID creator, @NotNull String prompt, @NotNull Duration duration,
                @NotNull Iterable<String> choices, PollCloseRunnable closeRunnable)
    {
        this.creator = creator;
        this.prompt = prompt;
        this.duration = duration;
        for (String choice : choices)
        {
            votes.put(choice, 0);
        }
        this.closeRunnable = closeRunnable;
        startTime = Instant.now();

        closeRunnable.schedule(duration);
    }

    @NotNull
    public Map<String, Integer> getVotes()
    {
        return votes;
    }

    @NotNull
    public Set<UUID> getVoters()
    {
        return voters;
    }

    @Nullable
    public UUID getCreator()
    {
        return creator;
    }

    @NotNull
    public String getPrompt()
    {
        return prompt;
    }

    @NotNull
    public Instant getStartTime()
    {
        return startTime;
    }

    @NotNull
    public Duration getDuration()
    {
        return duration;
    }

    @NotNull
    public PollCloseRunnable getCloseRunnable()
    {
        return closeRunnable;
    }
}