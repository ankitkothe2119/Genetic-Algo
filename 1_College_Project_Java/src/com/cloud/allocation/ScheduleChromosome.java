package com.cloud.allocation;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

// Represents a single candidate solution (Who gets which Task)
public class ScheduleChromosome {
    private int[] gene; // Index = TaskID, Value = VMID
    private double fitness = -1;
    private List<CloudTask> tasks;
    private List<VirtualMachine> vms;

    public ScheduleChromosome(List<CloudTask> tasks, List<VirtualMachine> vms) {
        this.tasks = tasks;
        this.vms = vms;
        this.gene = new int[tasks.size()];
        initializeRandomly();
    }

    // copy constructor
    public ScheduleChromosome(int[] gene, List<CloudTask> tasks, List<VirtualMachine> vms) {
        this.tasks = tasks;
        this.vms = vms;
        this.gene = gene;
    }

    private void initializeRandomly() {
        Random rand = new Random();
        for (int i = 0; i < gene.length; i++) {
            // Assign random VM to each task
            gene[i] = rand.nextInt(vms.size());
        }
    }

    public double getFitness() {
        if (fitness == -1) {
            calculateFitness();
        }
        return fitness;
    }

    // Fitness = 1.0 / Makespan (Lower makespan is better)
    private void calculateFitness() {
        double[] vmExecutionTimes = new double[vms.size()];

        // Calculate load on each VM
        for (int taskId = 0; taskId < gene.length; taskId++) {
            int vmIndex = gene[taskId];
            VirtualMachine vm = vms.get(vmIndex);
            CloudTask task = tasks.get(taskId);

            vmExecutionTimes[vmIndex] += vm.calculateExecutionTime(task.getLength());
        }

        // Makespan is the time taken by the busiest VM (Maximum finish time)
        double makespan = 0;
        for (double time : vmExecutionTimes) {
            if (time > makespan) {
                makespan = time;
            }
        }

        this.fitness = 1.0 / makespan; // Higher is better
    }

    public int[] getGenes() {
        return gene;
    }

    public double getMakespan() {
        return 1.0 / getFitness();
    }

    @Override
    public String toString() {
        return Arrays.toString(gene) + " -> Makespan: " + getMakespan();
    }
}
