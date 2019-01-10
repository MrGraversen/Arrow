package io.graversen.arrow;

import java.util.Arrays;
import java.util.function.Function;

class Utils
{
    private Utils()
    {

    }

    public static <T> Function<T, T> combineFunctions(Function<T, T>... functions)
    {
        return Arrays.stream(functions).reduce(Function.identity(), Function::andThen);
    }
}
