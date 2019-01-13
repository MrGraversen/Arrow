package io.graversen.arrow.util;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public class StreamUtils
{
    private StreamUtils()
    {

    }

    public static <T> Function<T, T> combine(Function<T, T>... functions)
    {
        return Arrays.stream(functions).reduce(Function.identity(), Function::andThen);
    }

    public static <T> Consumer<T> combine(Consumer<T>... consumers)
    {
        return Arrays.stream(consumers).reduce(consumerIdentity(), Consumer::andThen);
    }

    private static <T> Consumer<T> consumerIdentity()
    {
        return c -> {};
    }
}
