package com.coding.challenge.service;

import org.springframework.stereotype.Component;

@Component
public class Benchmark {
    private static final int NANO_TO_MS = 1000000;
    long benchmarkTime;
    long firstExclusiveBenchmarkTime;
    long secondExclusiveBenchmarkTime;

    public float getBenchmarkTime() {
        return benchmarkTime / NANO_TO_MS;
    }

    public float getFirstExclusiveBenchmarkTime() {
        return firstExclusiveBenchmarkTime / NANO_TO_MS;
    }

    public float getSecondExclusiveBenchmarkTime() {
        return secondExclusiveBenchmarkTime / NANO_TO_MS;
    }


    long startBenchmarkTime = 0;
    long endBenchmarkTime;
    long startFirstExclusiveBenchmarkTime;
    long endFirstExclusiveBenchmarkTime;
    long startSecondExclusiveBenchmarkTime;
    long endSecondExclusiveBenchmarkTime;

    public void runBenchmark(boolean runBenchmark) {
        if (runBenchmark == true) {
            startBenchmarkTime = System.nanoTime();
        }
        if (runBenchmark == false) {
            endBenchmarkTime = System.nanoTime();
            benchmarkTime = (endBenchmarkTime - startBenchmarkTime) - firstExclusiveBenchmarkTime - secondExclusiveBenchmarkTime;
        }
    }

    public void firstExclusiveBenchmark(boolean runFirstExclusiveBenchmark) {
        if (runFirstExclusiveBenchmark == true) {
            startFirstExclusiveBenchmarkTime = System.nanoTime();
        }
        if (runFirstExclusiveBenchmark == false) {
            endFirstExclusiveBenchmarkTime = System.nanoTime();
            firstExclusiveBenchmarkTime = firstExclusiveBenchmarkTime + (endFirstExclusiveBenchmarkTime - startFirstExclusiveBenchmarkTime);
        }
    }

    public void secondExclusiveBenchmark(boolean runSecondExclusiveBenchmark) {
        if (runSecondExclusiveBenchmark == true) {
            startSecondExclusiveBenchmarkTime = System.nanoTime();
        }
        if (runSecondExclusiveBenchmark == false) {
            endSecondExclusiveBenchmarkTime = System.nanoTime();
            secondExclusiveBenchmarkTime = secondExclusiveBenchmarkTime + (endSecondExclusiveBenchmarkTime - startSecondExclusiveBenchmarkTime);
        }
    }
}
