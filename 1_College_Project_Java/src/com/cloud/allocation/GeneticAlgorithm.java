package com.cloud.allocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GeneticAlgorithm {
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;
    private List<CloudTask> tasks;
    private List<VirtualMachine> vms;
    private ExecutorService executor;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
            List<CloudTask> tasks, List<VirtualMachine> vms) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.tasks = tasks;
        this.vms = vms;
        // Use all available CPU cores
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public ScheduleChromosome[] initPopulation() {
        ScheduleChromosome[] population = new ScheduleChromosome[populationSize];
        for (int i = 0; i < populationSize; i++) {
            population[i] = new ScheduleChromosome(tasks, vms);
        }
        return population;
    }

    public double getGroupFitness(ScheduleChromosome[] population) {
        double totalFitness = 0;
        for (ScheduleChromosome c : population) {
            totalFitness += c.getFitness();
        }
        return totalFitness;
    }

    // Explicitly calculate fitness in parallel
    public void evaluatePopulation(ScheduleChromosome[] population) {
        List<Callable<Void>> callables = new ArrayList<>();
        for (ScheduleChromosome chromosome : population) {
            callables.add(() -> {
                chromosome.getFitness(); // Triggers lazy calculation
                return null;
            });
        }

        try {
            executor.invokeAll(callables);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        executor.shutdown();
    }

    // Main Evolution Logic
    public ScheduleChromosome[] evolve(ScheduleChromosome[] population) {
        evaluatePopulation(population); // Calculate fitness in parallel before sorting
        return crossoverPopulation(population);
    }

    private ScheduleChromosome[] crossoverPopulation(ScheduleChromosome[] population) {
        ScheduleChromosome[] newPopulation = new ScheduleChromosome[populationSize];

        // 1. Sort population by fitness (Desc) for Elitism
        Arrays.sort(population, Comparator.comparingDouble(ScheduleChromosome::getFitness).reversed());

        // 2. Keep Elites
        for (int i = 0; i < elitismCount; i++) {
            newPopulation[i] = population[i];
        }

        // 3. Crossover for the rest
        for (int i = elitismCount; i < populationSize; i++) {
            // Select Parents
            ScheduleChromosome parent1 = selectParent(population);
            ScheduleChromosome parent2 = selectParent(population);

            // Crossover?
            ScheduleChromosome offspring;
            if (Math.random() < crossoverRate) {
                // One point crossover
                int[] gene1 = parent1.getGenes();
                int[] gene2 = parent2.getGenes();
                int[] newGene = new int[gene1.length];

                int pivot = new Random().nextInt(gene1.length);
                for (int k = 0; k < gene1.length; k++) {
                    if (k < pivot)
                        newGene[k] = gene1[k];
                    else
                        newGene[k] = gene2[k];
                }
                offspring = new ScheduleChromosome(newGene, tasks, vms);
            } else {
                offspring = parent1; // No crossover, just copy
            }

            // Mutation
            newPopulation[i] = mutate(offspring);
        }

        return newPopulation;
    }

    private ScheduleChromosome selectParent(ScheduleChromosome[] population) {
        // Tournament Selection
        int tournamentSize = 5;
        ScheduleChromosome best = null;
        for (int i = 0; i < tournamentSize; i++) {
            int randIdx = new Random().nextInt(population.length);
            ScheduleChromosome candidate = population[randIdx];
            if (best == null || candidate.getFitness() > best.getFitness()) {
                best = candidate;
            }
        }
        return best;
    }

    private ScheduleChromosome mutate(ScheduleChromosome chromosome) {
        int[] genes = chromosome.getGenes().clone();
        for (int i = 0; i < genes.length; i++) {
            if (Math.random() < mutationRate) {
                // Assign to a random VM
                genes[i] = new Random().nextInt(vms.size());
            }
        }
        return new ScheduleChromosome(genes, tasks, vms);
    }
}
