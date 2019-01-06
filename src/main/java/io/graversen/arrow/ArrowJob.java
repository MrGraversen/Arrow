package io.graversen.arrow;

import io.graversen.trunk.hardware.HardwareUtils;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ArrowJob implements Callable<String>
{
    private final String target;
    private final CombinationsService combinationsService;
    private final Collection<Transformer> transformers;

    private final Queue<String> combinationQueue;
    private final ExecutorService transformersExecutorService;

    public ArrowJob(String target, CombinationsService combinationsService, Collection<Transformer> transformers)
    {
        this.target = target;
        this.combinationsService = combinationsService;
        this.transformers = transformers;

        final int processorCount = HardwareUtils.getHardware().getLogicalProcessorCount();
        this.combinationQueue = new ConcurrentLinkedQueue<>();
        this.transformersExecutorService = Executors.newFixedThreadPool(processorCount);
    }

    @Override
    public String call() throws Exception
    {
        return "";
    }
}
