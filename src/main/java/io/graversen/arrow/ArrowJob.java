package io.graversen.arrow;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

class ArrowJob implements Callable<ArrowResult>
{
    private final String target;
    private final CombinationsService combinationsService;
    private final Collection<Transformer> transformers;
    private final Collection<Logger> loggers;

    private final Value<String> result;

    public ArrowJob(String target, CombinationsService combinationsService, Collection<Transformer> transformers, Collection<Logger> loggers)
    {
        this.target = target;
        this.combinationsService = combinationsService;
        this.transformers = transformers;
        this.loggers = loggers;

        this.result = Value.empty();
    }

    @Override
    public ArrowResult call()
    {
        final long start = System.currentTimeMillis();

        combinationsService.computeCombinations()
                .peek(remember())
                .map(applyTransformers())
                .peek(log())
                .filter(checkCombination())
                .findFirst();

        return new ArrowResult(result.value(), combinationsService.getCombinationsGenerated(), start, System.currentTimeMillis());
    }

    private Consumer<String> remember()
    {
        return result::update;
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
