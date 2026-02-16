package com.cloud.allocation.web;

import com.cloud.allocation.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * GAServlet - Controller Servlet for Genetic Algorithm Cloud Scheduler.
 * Handles user input (task count, VM count, generations) and runs GA
 * optimization.
 * Follows MVC pattern: Servlet = Controller, JSP = View, GA Engine = Model.
 */
public class GAServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Show the input form
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Read parameters from the form
        int taskCount = Integer.parseInt(request.getParameter("taskCount"));
        int vmCount = Integer.parseInt(request.getParameter("vmCount"));
        int generations = Integer.parseInt(request.getParameter("generations"));
        int popSize = Integer.parseInt(request.getParameter("popSize"));

        // 2. Generate dummy data
        List<CloudTask> tasks = createDummyTasks(taskCount);
        List<VirtualMachine> vms = createDummyVMs(vmCount);

        // 3. GA Configuration
        double mutationRate = 0.05;
        double crossoverRate = 0.9;
        int elitism = 2;

        GeneticAlgorithm ga = new GeneticAlgorithm(popSize, mutationRate, crossoverRate, elitism, tasks, vms);

        // 4. Init Population & Evolve
        ScheduleChromosome[] population = ga.initPopulation();

        List<double[]> generationHistory = new ArrayList<>();

        for (int gen = 1; gen <= generations; gen++) {
            population = ga.evolve(population);
            ScheduleChromosome best = population[0];
            generationHistory.add(new double[] { gen, best.getMakespan(), best.getFitness() });
        }

        ga.shutdown();

        // 5. Get Final Best Result
        ScheduleChromosome finalBest = population[0];
        int[] genes = finalBest.getGenes();

        // 6. Build allocation results
        List<String[]> allocationTable = new ArrayList<>();
        for (int i = 0; i < genes.length; i++) {
            CloudTask task = tasks.get(i);
            VirtualMachine vm = vms.get(genes[i]);
            double execTime = (double) task.getLength() / vm.getMips();

            allocationTable.add(new String[] {
                    String.valueOf(task.getId()),
                    String.valueOf(task.getLength()),
                    task.getPriorityLabel(),
                    String.valueOf(vm.getId()),
                    String.valueOf(vm.getMips()),
                    String.format("%.4f", execTime)
            });
        }

        // 7. Set attributes for JSP
        request.setAttribute("tasks", tasks);
        request.setAttribute("vms", vms);
        request.setAttribute("bestMakespan", String.format("%.4f", finalBest.getMakespan()));
        request.setAttribute("bestFitness", String.format("%.6f", finalBest.getFitness()));
        request.setAttribute("allocationTable", allocationTable);
        request.setAttribute("generationHistory", generationHistory);
        request.setAttribute("taskCount", taskCount);
        request.setAttribute("vmCount", vmCount);
        request.setAttribute("generations", generations);
        request.setAttribute("popSize", popSize);

        // 8. Forward to results page
        request.getRequestDispatcher("/results.jsp").forward(request, response);
    }

    // --- Data Generators ---
    private List<CloudTask> createDummyTasks(int count) {
        List<CloudTask> list = new ArrayList<>();
        Random rand = new Random(42); // Fixed seed for reproducibility
        for (int i = 0; i < count; i++) {
            int length = 500 + rand.nextInt(4500);
            int priority = 1 + rand.nextInt(3);
            list.add(new CloudTask(i, length, priority));
        }
        return list;
    }

    private List<VirtualMachine> createDummyVMs(int count) {
        List<VirtualMachine> list = new ArrayList<>();
        Random rand = new Random(42);
        for (int i = 0; i < count; i++) {
            list.add(new VirtualMachine(i, 500 + rand.nextInt(1500)));
        }
        return list;
    }
}
