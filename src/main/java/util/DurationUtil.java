package util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import throwable.UnreachableCodeError;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author funkyFangs
 */
public class DurationUtil
{
    /**
     * Parses an input {@link String} into a {@link Duration}.
     * @param input the {@link String} to parse
     * @return the input as a {@link Duration}, or null if the input is invalid
     */
    @Nullable
    public static Duration parse(String input)
    {
        if (input == null || input.isEmpty())
        {
            return null;
        }
        String[] tokens = input.split(" +");
        int length = tokens.length;
        return switch (length)
                       {
                           case 2 -> parse(tokens[0], tokens[1]);
                           case 4 -> parse(tokens[0], tokens[1], tokens[2], tokens[3]);
                           case 6 -> parse(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                           default -> null;
                       };
    }

    /**
     * Parses an input {@link String} of the format "X hour(s) Y minute(s) Z second(s)" where X, Y, and Z are positive
     * integers.
     * @param amountToken1 the amount of the first token
     * @param unitToken1 the unit of the first token
     * @param amountToken2 the amount of the second token
     * @param unitToken2 the unit of the second token
     * @param amountToken3 the amount of the third token
     * @param unitToken3 the unit of the third token
     * @return the input {@link String} as a {@link Duration}, or null if it is improperly formatted
     */
    @Nullable
    private static Duration parse(String amountToken1, String unitToken1, String amountToken2, String unitToken2,
                                  String amountToken3, String unitToken3)
    {
        long amount1;
        long amount2;
        long amount3;
        try
        {
            amount1 = Long.parseLong(amountToken1);
            amount2 = Long.parseLong(amountToken2);
            amount3 = Long.parseLong(amountToken3);
        }
        catch (NumberFormatException ignored)
        {
            return null;
        }

        Duration duration = null;
        if (validHour(amount1, unitToken1) && validMinute(amount2, unitToken2) && validSecond(amount3, unitToken3))
        {
            duration = Duration.ofHours(amount1).plusMinutes(amount2).plusSeconds(amount3);
        }
        return duration;
    }

    @Nullable
    private static Duration parse(String amountToken1, String unitToken1, String amountToken2, String unitToken2)
    {
        long amount1;
        long amount2;
        try
        {
            amount1 = Long.parseLong(amountToken1);
            amount2 = Long.parseLong(amountToken2);
        }
        catch (NumberFormatException ignored)
        {
            return null;
        }

        Duration duration = null;
        if (validHour(amount1, unitToken1))
        {
            Duration hours = Duration.ofHours(amount1);
            if (validMinute(amount2, unitToken2))
            {
                duration = hours.plusMinutes(amount2);
            }
            else if (validSecond(amount2, unitToken2))
            {
                duration = hours.plusSeconds(amount2);
            }
        }
        else if (validMinute(amount1, unitToken1) && validSecond(amount2, unitToken2))
        {
            duration = Duration.ofMinutes(amount1).plusSeconds(amount2);
        }
        return duration;
    }

    /**
     * Parses a {@link Duration} from an amount and unit as a {@link String}.
     * @param amountToken the amount of the duration as a {@link String}
     * @param unitToken the unit of the duration as a {@link String}
     * @return a {@link Duration}, or null if the tokens were invalid
     */
    @Nullable
    private static Duration parse(@NotNull String amountToken, @NotNull String unitToken)
    {
        long amount;
        try
        {
            amount = Long.parseLong(amountToken);
        }
        catch (NumberFormatException ignored)
        {
            return null;
        }

        Duration duration = null;
        if (validHour(amount, unitToken))
        {
            duration = Duration.ofHours(amount);
        }
        else if (validMinute(amount, unitToken))
        {
            duration = Duration.ofMinutes(amount);
        }
        else if (validSecond(amount, unitToken))
        {
            duration = Duration.ofSeconds(amount);
        }
        return duration;
    }

    /**
     * Determines if the given input represents an amount of hours.
     * @param amount the amount of the input
     * @param unit the unit of the input
     * @return true if the input is an amount of hours, or false otherwise
     */
    private static boolean validHour(long amount, String unit)
    {
        return unit.equalsIgnoreCase(amount == 1 ? "hour" : "hours");
    }

    private static boolean validMinute(long amount, String unit)
    {
        return unit.equalsIgnoreCase(amount == 1 ? "minute" : "minutes");
    }

    private static boolean validSecond(long amount, String unit)
    {
        return unit.equalsIgnoreCase(amount == 1 ? "second" : "seconds");
    }

    @NotNull
    public static String toString(@NotNull Duration duration)
    {
        // Life could be so wonderful, but instead we're stuck with JDK 8
        long durationInSeconds = duration.getSeconds();
        long hours = durationInSeconds / 3600;
        long minutes = (durationInSeconds % 3600) / 60;
        long seconds = durationInSeconds % 60;

        List<String> times = new ArrayList<>(3);

        if (hours > 0)
        {
            times.add(hours + (hours == 1 ? " hour" : " hours"));
        }
        if (minutes > 0)
        {
            times.add(minutes + (minutes == 1 ? " minute" : " minutes"));
        }
        if (seconds > 0 || hours == 0 && minutes == 0)
        {
            times.add(seconds + (seconds == 1 ? " second" : " seconds"));
        }

        StringBuilder builder = new StringBuilder();
        return switch (times.size())
        {
            case 1 -> builder.append(times.get(0)).toString();
            case 2 -> builder.append(times.get(0)).append(" and ").append(times.get(1)).toString();
            case 3 -> builder.append(times.get(0)).append(", ").append(times.get(1)).append(", and ").append(times.get(2)).toString();
            default -> throw new UnreachableCodeError("DurationUtil.toString:194");
        };
    }
}