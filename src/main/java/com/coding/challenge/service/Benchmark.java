package com.coding.challenge.service;

import org.springframework.stereotype.Component;

/**
 * Eine Klasse für eine Benchmark-Funktionalität.
 * Die Rückgabe von getBenchmarkTime(), getFirstExclusiveBenchmarkTime(), getSecondExclusiveBenchmarkTime() erfolgt
 * als float und in Millisekunden.
 *
 * @author Dhalia
 */
@Component
public class Benchmark {
    private static final int NANO_TO_MS = 1000000;
    float benchmarkTime;
    float firstExclusiveBenchmarkTime;
    float secondExclusiveBenchmarkTime;

    public float getBenchmarkTime() {
        return benchmarkTime / NANO_TO_MS;
    }

    public float getFirstExclusiveBenchmarkTime() {
        return firstExclusiveBenchmarkTime / NANO_TO_MS;
    }

    public float getSecondExclusiveBenchmarkTime() {
        return secondExclusiveBenchmarkTime / NANO_TO_MS;
    }

    long startBenchmarkTime;
    long endBenchmarkTime;
    long startFirstExclusiveBenchmarkTime;
    long startSecondExclusiveBenchmarkTime;

    /**
     * Startet und stoppt den Benchmark.
     *
     * @param runBenchmark Boolean true = Start, false = Stopp
     */
    public void runBenchmark(boolean runBenchmark) {
        if (runBenchmark == true) {
            startBenchmarkTime = System.nanoTime();
        }
        if (runBenchmark == false) {
            endBenchmarkTime = System.nanoTime();
            benchmarkTime = (endBenchmarkTime - startBenchmarkTime) - firstExclusiveBenchmarkTime - secondExclusiveBenchmarkTime;
        }
    }

    /**
     * Startet und stoppt den ersten exklusiven zwischen Benchmark.
     *
     * @param runFirstExclusiveBenchmark Boolean true = Start, false = Stopp
     */
    public void firstExclusiveBenchmark(boolean runFirstExclusiveBenchmark) {
        if (runFirstExclusiveBenchmark == true) {
            startFirstExclusiveBenchmarkTime = System.nanoTime();
        }
        if (runFirstExclusiveBenchmark == false) {
            firstExclusiveBenchmarkTime = firstExclusiveBenchmarkTime + (System.nanoTime() - startFirstExclusiveBenchmarkTime);
        }
    }

    /**
     * Startet und stoppt den zweiten exklusiven zwischen Benchmark.
     *
     * @param runSecondExclusiveBenchmark Boolean true = Start, false =Stopp
     */
    public void secondExclusiveBenchmark(boolean runSecondExclusiveBenchmark) {
        if (runSecondExclusiveBenchmark == true) {
            startSecondExclusiveBenchmarkTime = System.nanoTime();
        }
        if (runSecondExclusiveBenchmark == false) {
            secondExclusiveBenchmarkTime = secondExclusiveBenchmarkTime + (System.nanoTime()- startSecondExclusiveBenchmarkTime);
        }
    }
    public void reset(){
        this.benchmarkTime=0;
        this.firstExclusiveBenchmarkTime = 0;
        this.secondExclusiveBenchmarkTime = 0;
        this.startBenchmarkTime = 0;
        this.endBenchmarkTime = 0;
        this.startFirstExclusiveBenchmarkTime = 0;
        this.startSecondExclusiveBenchmarkTime = 0;
    }
}
