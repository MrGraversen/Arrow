package io.graversen.arrow;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Arrow implements AutoCloseable
{
    private final CombinationsService combinationsService;
    private final Collection<Transformer> transformers;
    private final Collection<Logger> loggers;
    private final ExecutorService combinationsExecutorService;

    private Arrow(Collection<Transformer> transformers, Collection<Logger> loggers, CombinationsService combinationsService)
    {
        this.transformers = transformers;
        this.loggers = loggers;
        this.combinationsService = combinationsService;
        this.combinationsExecutorService = Executors.newSingleThreadExecutor();
    }

    public static ArrowBuilder using(CombinationsService combinationsService)
    {
        return new ArrowBuilder(combinationsService);
    }

    public Future<String> tryFind(String target)
    {
        return combinationsExecutorService.submit(
                new ArrowJob(target, combinationsService, transformers, loggers)
        );
    }

    @Override
    public void close()
    {
        combinationsExecutorService.shutdownNow();
    }

    public static class ArrowBuilder
    {
        private CombinationsService combinationsService;
        private List<Transformer> transformers;
        private List<Logger> loggers;

        ArrowBuilder(CombinationsService combinationsService)
        {
            this.combinationsService = combinationsService;
            this.transformers = new ArrayList<>();
            this.loggers = new ArrayList<>();
        }

        public ArrowBuilder withTransformer(Transformer transformer)
        {
            return this.withTransformers(transformer);
        }

        public ArrowBuilder withTransformers(Transformer... transformers)
        {
            this.transformers.addAll(Arrays.asList(transformers));
            return this;
        }

        public ArrowBuilder withLogger(Logger logger)
        {
            return this.withLoggers(logger);
        }

        public ArrowBuilder withLoggers(Logger... loggers)
        {
            this.loggers.addAll(Arrays.asList(loggers));
            return this;
        }

        public Arrow build()
        {
            return new Arrow(
                    Collections.unmodifiableCollection(transformers),
                    Collections.unmodifiableCollection(loggers),
                    combinationsService
            );
        }
    }
}
