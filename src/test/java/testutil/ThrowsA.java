package testutil;

import org.hamcrest.CustomMatcher;

public class ThrowsA extends CustomMatcher<Runnable>
{
    private final Class<? extends Throwable> throwableClass;
    private String message;

    private ThrowsA(Class<? extends Throwable> throwableClass)
    {
        super("");
        this.throwableClass = throwableClass;
    }

    public static <T extends Throwable> ThrowsA throwsA(Class<T> exceptionClass)
    {
        return new ThrowsA(exceptionClass);
    }

    public ThrowsA withMessage(String message)
    {
        this.message = message;
        return this;
    }

    @Override
    public boolean matches(Object actual)
    {
        return actual instanceof Runnable && matches((Runnable) actual);
    }

    public boolean matches(Runnable runnable)
    {
        try
        {
            runnable.run();
        }
        catch (Throwable throwable)
        {
            return throwableClass.isAssignableFrom(throwable.getClass())
                   && message == null || message.equals(throwable.getMessage());
        }
        return false;
    }
}
