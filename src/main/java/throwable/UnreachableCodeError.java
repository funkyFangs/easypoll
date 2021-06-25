package throwable;

public class UnreachableCodeError extends Error
{
    public UnreachableCodeError(String location)
    {
        super("Tried to execute unreachable code: " + location);
    }
}