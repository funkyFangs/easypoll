package constant;

/**
 * Represents optional permissions for available EasyPoll commands
 *
 * @author funkyFangs
 */
public enum EasyPollPermission
{
    CLOSE_OTHERS("easypoll.poll.close.others"),
    CREATE_MULTIPLE("easypoll.poll.create.multiple"),
    INFO_COUNTS("easypoll.poll.info.counts"),
    POLL("easypoll.poll"),
    VOTE_UNLIMITED("easypoll.poll.vote.unlimited");

    private final String permission;

    EasyPollPermission(String permission)
    {
        this.permission = permission;
    }

    @Override
    public String toString()
    {
        return permission;
    }
}