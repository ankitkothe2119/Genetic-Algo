package com.cloud.allocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("  Cloud Resource Allocation (Java GA)");
        System.out.println("==========================================");

        // 1. Setup Data
        int taskCount = 20;
        int vmCount = 5;
        List<CloudTask> tasks = createDummyTasks(taskCount);
        List<VirtualMachine> vms = createDummyVMs(vmCount);

        System.out.println("Created " + taskCount + " tasks and " + vmCount + " VMs.");

        // 2. GA Configuration
        int popSize = 100;
        double mutationRate = 0.05;
        double crossoverRate = 0.9;
        int elitism = 2;
        int maxGenerations = 100;

        GeneticAlgorithm ga = new GeneticAlgorithm(popSize, mutationRate, crossoverRate, elitism, tasks, vms);

        // 3. Init Population
        ScheduleChromosome[] population = ga.initPopulation();

        // 4. Evolution Loop
        for (int gen = 1; gen <= maxGenerations; gen++) {
            population = ga.evolve(population);

            // Get best of this generation
            ScheduleChromosome best = population[0]; // Is sorted by evolve method (elitism)

            if (gen % 10 == 0 || gen == 1) {
                System.out.printf("Generation %d: Best Makespan = %.4f seconds (Fitness: %.4f)%n",
                        gen, best.getMakespan(), best.getFitness());
            }
        }

        // 5. Final Result
        ScheduleChromosome finalBest = population[0];

        // Display Task List
        System.out.println("\n========== TASK LIST ==========");
        System.out.println("-----------------------------------------------------");
        System.out.printf("| %-10s | %-15s | %-15s |%n", "Task ID", "Size (MI)", "Priority");
        System.out.println("-----------------------------------------------------");
        for (CloudTask task : tasks) {
            System.out.printf("| %-10d | %-15d | %-15s |%n", task.getId(), task.getLength(), task.getPriorityLabel());
        }
        System.out.println("-----------------------------------------------------");

        // Display VM List
        System.out.println("\n========== VM LIST ==========");
        System.out.println("---------------------------------------");
        System.out.printf("| %-10s | %-15s |%n", "VM ID", "MIPS");
        System.out.println("---------------------------------------");
        for (VirtualMachine vm : vms) {
            System.out.printf("| %-10d | %-15d |%n", vm.getId(), vm.getMips());
        }
        System.out.println("---------------------------------------");

        // Display Final Allocation
        System.out.println("\n========== FINAL OPTIMAL ALLOCATION ==========");
        System.out.println("Minimum Time to Finish All Tasks: " + finalBest.getMakespan() + " seconds");
        System.out.println("Allocation Table:");
        System.out
                .println("------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-12s | %-10s | %-8s | %-10s | %-15s |%n", "Task ID", "Size (MI)", "Priority",
                "VM ID", "VM MIPS",
                "Exec Time (s)");
        System.out
                .println("------------------------------------------------------------------------------------------");

        int[] genes = finalBest.getGenes();
        for (int i = 0; i < genes.length; i++) {
            CloudTask task = tasks.get(i);
            VirtualMachine vm = vms.get(genes[i]);
            double execTime = (double) task.getLength() / vm.getMips();

            System.out.printf("| %-10d | %-12d | %-10s | %-8d | %-10d | %-15.4f |%n",
                    task.getId(), task.getLength(), task.getPriorityLabel(), vm.getId(), vm.getMips(), execTime);
        }
        System.out
                .println("------------------------------------------------------------------------------------------");

        ga.shutdown();
    }

    // --- Data Generators ---
    static List<CloudTask> createDummyTasks(int count) {
        List<CloudTask> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int length = 500 + rand.nextInt(4500); // Random Length 500-5000 MI
            int priority = 1 + rand.nextInt(3); // Random Priority 1-3 (Low, Medium, High)
            list.add(new CloudTask(i, length, priority));
        }
        return list;
    }

    static List<VirtualMachine> createDummyVMs(int count) {
        List<VirtualMachine> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            list.add(new VirtualMachine(i, 500 + rand.nextInt(1500))); // Random MIPS 500-2000
        }
        return list;
    }
}
