package io.graversen.arrow;

import java.util.Objects;

public class ArrowResult
{
    private final String result;
    private final long attempts;
    private final long start;
    private final long end;

    public ArrowResult(String result, long attempts, long start, long end)
    {
        this.result = result;
        this.attempts = attempts;
        this.start = start;
        this.end = end;
    }

    public String getResult()
    {
        return result;
    }

    public long getAttempts()
    {
        return attempts;
    }

    private long getDuration()
    {
        return end - start;
    }

    private boolean success()
    {
        return Objects.nonNull(result);
    }

    @Override
    public String toString()
    {
        return String.format("ArrowResult = [result=%s, attempts=%d, duration %dms]", getResult(), getAttempts(), getDuration());
    }
}
