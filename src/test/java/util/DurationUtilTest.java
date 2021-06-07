package util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Validates the correctness of the parsing and formatting methods of the {@link DurationUtil} class.
 * @author funkyFangs
 */
public class DurationUtilTest
{
    @Test
    void testParseEmptyInput()
    {
        assertThat(DurationUtil.parse(""), is(nullValue()));
    }

    @Test
    void testParseInvalidInput()
    {
        assertThat(DurationUtil.parse("Test"), is(nullValue()));
    }

    @Test
    void testParseSingleInputs()
    {
        assertThat(DurationUtil.parse("1 hour"), is(Duration.ofHours(1)));
        assertThat(DurationUtil.parse("2 hours"), is(Duration.ofHours(2)));

        assertThat(DurationUtil.parse("1 minute"), is(Duration.ofMinutes(1)));
        assertThat(DurationUtil.parse("2 minutes"), is(Duration.ofMinutes(2)));

        assertThat(DurationUtil.parse("1 second"), is(Duration.ofSeconds(1)));
        assertThat(DurationUtil.parse("2 seconds"), is(Duration.ofSeconds(2)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 hours",
                            "2 hour",
                            "1 minutes",
                            "2 minute",
                            "1 seconds",
                            "2 second"})
    void testParseInvalidSingleInputs(String input)
    {
        assertThat(DurationUtil.parse(input), is(nullValue()));
    }

    @Test
    void testParseDoubleInputs()
    {
        assertThat(DurationUtil.parse("1 hour 1 minute"), is(Duration.ofHours(1).plusMinutes(1)));
        assertThat(DurationUtil.parse("1 hour 2 minutes"), is(Duration.ofHours(1).plusMinutes(2)));
        assertThat(DurationUtil.parse("2 hours 1 minute"), is(Duration.ofHours(2).plusMinutes(1)));
        assertThat(DurationUtil.parse("2 hours 2 minutes"), is(Duration.ofHours(2).plusMinutes(2)));

        assertThat(DurationUtil.parse("1 hour 1 second"), is(Duration.ofHours(1).plusSeconds(1)));
        assertThat(DurationUtil.parse("1 hour 2 seconds"), is(Duration.ofHours(1).plusSeconds(2)));
        assertThat(DurationUtil.parse("2 hours 1 second"), is(Duration.ofHours(2).plusSeconds(1)));
        assertThat(DurationUtil.parse("2 hours 2 seconds"), is(Duration.ofHours(2).plusSeconds(2)));

        assertThat(DurationUtil.parse("1 minute 1 second"), is(Duration.ofMinutes(1).plusSeconds(1)));
        assertThat(DurationUtil.parse("1 minute 2 seconds"), is(Duration.ofMinutes(1).plusSeconds(2)));
        assertThat(DurationUtil.parse("2 minutes 1 second"), is(Duration.ofMinutes(2).plusSeconds(1)));
        assertThat(DurationUtil.parse("2 minutes 2 seconds"), is(Duration.ofMinutes(2).plusSeconds(2)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 hour 1 minutes",
                            "1 hour 2 minute",
                            "1 hours 1 minutes",
                            "1 hours 1 minute",
                            "1 hours 2 minutes",
                            "1 hours 2 minute",
                            "2 hours 1 minutes",
                            "2 hours 2 minute",
                            "2 hour 1 minute",
                            "2 hour 1 minutes",
                            "2 hour 2 minutes",
                            "2 hour 2 minute"})
    void testParseInvalidHourMinuteInputs(String input)
    {
        assertThat(DurationUtil.parse(input), is(nullValue()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 hour 1 seconds",
                            "1 hour 2 second",
                            "1 hours 1 seconds",
                            "1 hours 1 second",
                            "1 hours 2 seconds",
                            "1 hours 2 second",
                            "2 hours 1 seconds",
                            "2 hours 2 second",
                            "2 hour 1 second",
                            "2 hour 1 seconds",
                            "2 hour 2 seconds",
                            "2 hour 2 second"})
    void testParseInvalidHourSecondInputs(String input)
    {
        assertThat(DurationUtil.parse(input), is(nullValue()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 minute 1 seconds",
                            "1 minute 2 second",
                            "1 minutes 1 seconds",
                            "1 minutes 1 second",
                            "1 minutes 2 seconds",
                            "1 minutes 2 second",
                            "2 minutes 1 seconds",
                            "2 minutes 2 second",
                            "2 minute 1 second",
                            "2 minute 1 seconds",
                            "2 minute 2 seconds",
                            "2 minute 2 second"})
    void testParseInvalidMinuteSecondInputs(String input)
    {
        assertThat(DurationUtil.parse(input), is(nullValue()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 minute 1 hour",
                            "1 second 1 hour",
                            "1 second 1 minute"})
    void testParseInvalidDoubleInputs(String input)
    {
        assertThat(DurationUtil.parse(input), is(nullValue()));
    }

    @Test
    void testToStringSingleDuration()
    {
        assertThat(DurationUtil.toString(Duration.ofHours(1)), is("1 hour"));
        assertThat(DurationUtil.toString(Duration.ofHours(2)), is("2 hours"));

        assertThat(DurationUtil.toString(Duration.ofMinutes(1)), is("1 minute"));
        assertThat(DurationUtil.toString(Duration.ofMinutes(2)), is("2 minutes"));

        assertThat(DurationUtil.toString(Duration.ofSeconds(1)), is("1 second"));
        assertThat(DurationUtil.toString(Duration.ofSeconds(2)), is("2 seconds"));
    }

    @Test
    void testToStringDoubleDuration()
    {
        assertThat(DurationUtil.toString(Duration.ofHours(1).plusMinutes(1)), is("1 hour and 1 minute"));
        assertThat(DurationUtil.toString(Duration.ofHours(1).plusMinutes(2)), is("1 hour and 2 minutes"));
        assertThat(DurationUtil.toString(Duration.ofHours(2).plusMinutes(1)), is("2 hours and 1 minute"));
        assertThat(DurationUtil.toString(Duration.ofHours(2).plusMinutes(2)), is("2 hours and 2 minutes"));

        assertThat(DurationUtil.toString(Duration.ofHours(1).plusSeconds(1)), is("1 hour and 1 second"));
        assertThat(DurationUtil.toString(Duration.ofHours(1).plusSeconds(2)), is("1 hour and 2 seconds"));
        assertThat(DurationUtil.toString(Duration.ofHours(2).plusSeconds(1)), is("2 hours and 1 second"));
        assertThat(DurationUtil.toString(Duration.ofHours(2).plusSeconds(2)), is("2 hours and 2 seconds"));

        assertThat(DurationUtil.toString(Duration.ofMinutes(1).plusSeconds(1)), is("1 minute and 1 second"));
        assertThat(DurationUtil.toString(Duration.ofMinutes(1).plusSeconds(2)), is("1 minute and 2 seconds"));
        assertThat(DurationUtil.toString(Duration.ofMinutes(2).plusSeconds(1)), is("2 minutes and 1 second"));
        assertThat(DurationUtil.toString(Duration.ofMinutes(2).plusSeconds(2)), is("2 minutes and 2 seconds"));
    }

    @Test
    void testToStringTripleDuration()
    {
        assertThat(DurationUtil.toString(Duration.ofHours(1).plusMinutes(1).plusSeconds(1)),
                   is("1 hour, 1 minute, and 1 second"));
        assertThat(DurationUtil.toString(Duration.ofHours(1).plusMinutes(1).plusSeconds(2)),
                   is("1 hour, 1 minute, and 2 seconds"));
        assertThat(DurationUtil.toString(Duration.ofHours(1).plusMinutes(2).plusSeconds(1)),
                   is("1 hour, 2 minutes, and 1 second"));
        assertThat(DurationUtil.toString(Duration.ofHours(1).plusMinutes(2).plusSeconds(2)),
                   is("1 hour, 2 minutes, and 2 seconds"));
        assertThat(DurationUtil.toString(Duration.ofHours(2).plusMinutes(1).plusSeconds(1)),
                   is("2 hours, 1 minute, and 1 second"));
        assertThat(DurationUtil.toString(Duration.ofHours(2).plusMinutes(1).plusSeconds(2)),
                   is("2 hours, 1 minute, and 2 seconds"));
        assertThat(DurationUtil.toString(Duration.ofHours(2).plusMinutes(2).plusSeconds(1)),
                   is("2 hours, 2 minutes, and 1 second"));
        assertThat(DurationUtil.toString(Duration.ofHours(2).plusMinutes(2).plusSeconds(2)),
                   is("2 hours, 2 minutes, and 2 seconds"));
    }
}