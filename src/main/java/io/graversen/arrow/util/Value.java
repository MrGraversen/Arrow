package io.graversen.arrow.util;

public class Value<T>
{
    private T value;

    public Value(T value)
    {
        this.value = value;
    }

    public static <T> Value<T> empty()
    {
        return new Value<>(null);
    }

    public T value()
    {
        return value;
    }

    public void update(T value)
    {
        this.value = value;
    }
}
