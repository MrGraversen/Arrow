package io.graversen.arrow;

import io.graversen.trunk.math.MathUtils;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

class ArrowJob implements Callable<ArrowResult>
{
    private final String target;
    private final CombinationsService combinationsService;
    private final Collection<Transformer> transformers;
    private final Collection<Logger> loggers;

    private final AtomicLong counter;
    private final Value<String> result;

    public ArrowJob(String target, CombinationsService combinationsService, Collection<Transformer> transformers, Collection<Logger> loggers)
    {
        this.target = target;
        this.combinationsService = combinationsService;
        this.transformers = transformers;
        this.loggers = loggers;

        this.counter = new AtomicLong(0);
        this.result = Value.empty();
    }

    @Override
    public ArrowResult call()
    {
        final long start = System.currentTimeMillis();

        combinationsService.computeCombinations()
                .peek(remember())
                .peek(count())
                .map(applyTransformers())
                .peek(log())
                .filter(checkCombination())
                .findFirst();

        return new ArrowResult(result.value(), counter.get(), start, System.currentTimeMillis());
    }

    private Consumer<String> remember()
    {
        return result::update;
    }

    private Consumer<String> count()
    {
        return combination -> counter.incrementAndGet();
    }

    private Consumer<String> log()
    {
        return StreamUtils.combine(loggers.toArray(Logger[]::new));
    }

    private Function<String, String> applyTransformers()
    {
        return StreamUtils.combine(transformers.toArray(Transformer[]::new));
    }

    private Predicate<String> checkCombination()
    {
        return target::equals;
    }
}
