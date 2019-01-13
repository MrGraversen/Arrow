package io.graversen.arrow.benchmarks;

import io.graversen.arrow.Arrow;
import io.graversen.arrow.CombinationsService;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ArrowBenchmarks
{
    @Param({"1000", "10000", "100000"})
    private int limit;

    private CombinationsService combinationsService;

    @Setup
    public void setup()
    {
        this.combinationsService = CombinationsService.usingAlphaNumericEnglishAlphabet();
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testComputeNext(Blackhole blackhole)
    {
        final String next = combinationsService.computeNext();
        blackhole.consume(next);
    }

    public static void main(String[] args) throws RunnerException
    {
        final Options opts = new OptionsBuilder()
                .include(ArrowBenchmarks.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(5)
                .jvmArgs("-Xms4g", "-Xmx4g")
                .shouldDoGC(true)
                .forks(1)
                .build();

        new Runner(opts).run();
    }
}
