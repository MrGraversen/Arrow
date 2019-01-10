package io.graversen.arrow;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Arrow
{
    private final CombinationsService combinationsService;
    private final ExecutorService combinationsExecutorService;

    public Arrow(CombinationsService combinationsService)
    {
        this.combinationsService = combinationsService;
        this.combinationsExecutorService = Executors.newSingleThreadExecutor();
    }

    public Future<String> tryFind(String target)
    {
        return tryFind(target, Collections.emptyList());
    }

    public Future<String> tryFind(String target, Transformer transformer)
    {
        return tryFind(target, Collections.singletonList(transformer));
    }

    public Future<String> tryFind(String target, Collection<Transformer> transformers)
    {
        return combinationsExecutorService.submit(new ArrowJob(target, combinationsService, transformers));
    }
}
