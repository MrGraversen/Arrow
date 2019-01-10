package io.graversen.arrow;

import java.io.PrintStream;

public class Loggers
{
    private Loggers()
    {

    }

    public static Logger standardOutLogger()
    {
        return printStreamLogger(System.out);
    }

    public static Logger printStreamLogger(PrintStream printStream)
    {
        return printStream::println;
    }
}
