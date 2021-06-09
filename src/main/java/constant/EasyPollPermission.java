package constant;

/**
 * Represents the available permissions for the /poll command.
 * Note: these constants are {@link String}s instead of an enum because they are only used for {@link String} values.
 * @author funkyFangs
 */
public class EasyPollPermission
{
    public static final String CLOSE_OTHERS = "easypoll.poll.close.others";
    public static final String CREATE_MULTIPLE = "easypoll.poll.create.multiple";
    public static final String INFO_COUNTS = "easypoll.poll.info.counts";
    public static final String POLL = "easypoll.poll";
    public static final String VOTE_UNLIMITED = "easypoll.poll.vote.unlimited";

    private EasyPollPermission()
    {
        // Hides default constructor
    }
}