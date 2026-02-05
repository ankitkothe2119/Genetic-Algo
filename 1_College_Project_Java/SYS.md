# **Project Synopsis**

**Project Title:** Cloud Resource Allocation and Scheduling using Genetic Algorithm

---

## **1. Introduction**

Cloud computing has emerged as a dominant paradigm for delivering computing resources over the internet. It allows users to access a shared pool of configurable computing resources (e.g., networks, servers, storage, applications, and services) that can be rapidly provisioned and released with minimal management effort or service provider interaction. As the demand for cloud services grows exponentially, the efficient management of these resources becomes a critical challenge. Data centers—the backbone of cloud computing—comprise thousands of servers consuming vast amounts of energy. Inefficient resource allocation leads to underutilization of hardware, performance bottlenecks, excessive energy consumption, and violation of Service Level Agreements (SLAs).

Resource allocation in cloud computing is the process of assigning available resources to incoming user requests (tasks) in a manner that optimizes specific performance criteria. This problem is known to be NP-complete, meaning that finding an optimal solution within a reasonable time frame is computationally infeasible for large-scale systems using traditional deterministic algorithms. Heuristic and meta-heuristic approaches are therefore preferred to find near-optimal solutions efficiently.

This project focuses on implementing a **Genetic Algorithm (GA)**—a bio-inspired meta-heuristic inspired by the process of natural selection—to solve the dynamic task scheduling problem in a cloud environment. The Genetic Algorithm imitates the biological evolution process, using operators such as Selection, Crossover, and Mutation to evolve a population of potential schedules towards an optimal solution. By effectively mapping tasks to Virtual Machines (VMs), the system aims to minimize the total execution time (Makespan) and ensure a balanced load across resources, thereby enhancing the overall performance and efficiency of the cloud infrastructure.

---

## **2. Problem Statement**

In a typical Infrastructure as a Service (IaaS) cloud model, users submit a diverse set of tasks with varying computational requirements (length, size, urgency). On the other side, the cloud provider offers a pool of Virtual Machines (VMs) with different processing capabilities (MIPS - Million Instructions Per Second), bandwidth, and memory. The core problem is the **Task Scheduling Problem**: deciding which task should be executed on which VM.

Traditional scheduling algorithms like First-Come-First-Serve (FCFS) or Round Robin (RR) are simple but often result in poor resource utilization. For instance, assigning a heavy task to a slow VM while a fast VM sits idle increases the overall completion time. Conversely, purely greedy approaches like Min-Min or Max-Min may lead to severe load imbalance, leaving some resources overwhelmed while others are underutilized.

Specifically, the problems addressed by this project are:
1.  **Imbalanced Load:** Uneven distribution of tasks leads to some VMs being overloaded, increasing failure rates and latency, while others are underused, wasting energy.
2.  **High Makespan:** Inefficient scheduling results in a longer time to complete the entire batch of tasks, reducing throughput.
3.  **Scalability Issues:** As the number of tasks and VMs increases, traditional exact algorithms fail to produce a schedule in real-time.

There is a need for an intelligent, adaptive optimization technique that can navigate the massive search space of possible mappings to find a high-quality schedule that balances Makespan and Resource Utilization.

---

## **3. Aim & Objectives**

### **3.1 Aim**
The primary aim of this project is to design and develop a Java-based simulation of a Cloud Resource Allocation System that utilizes a Genetic Algorithm to optimize the scheduling of tasks onto Virtual Machines.

### **3.2 Objectives**
To achieve this aim, the following specific objectives have been defined:

1.  **To simulate a Cloud Environment:** Create a virtualized environment model in Java consisting of Datacenters, Hosts, Virtual Machines (VMs), and Cloudlets (Tasks) with configurable properties.
2.  **To implement the Genetic Algorithm:** Develop the core GA engine including:
    *   **Encoding:** Representing a schedule as a chromosome.
    *   **Fitness Function:** Formulating a mathematical function to evaluate schedules based on Makespan and Load Balance.
    *   **Genetic Operators:** Implementing Selection (Roulette Wheel/Tournament), Crossover (Single/Two-point), and Mutation techniques.
3.  **To optimize Task Scheduling:** Minimize the total Makespan (time taken to finish the last task) of the system.
4.  **To evaluate Performance:** Compare the results of the Genetic Algorithm against standard baselines (e.g., Random Allocation or Round Robin) to demonstrate improvements in efficiency.
5.  **To visualize the Optimization Process:** Provide a console-based or simple graphical representation of how the solution quality improves over generations.

---

## **4. Scope of Project**

The scope of this project is defined by the following boundaries:

*   **Domain:** Cloud Computing and Meta-heuristic Optimization.
*   **Simulation vs. Real World:** The project will be a **simulation**. We will not be deploying code on actual physical AWS/Azure servers but will simulate the behavior of a cloud broker and datacenter using Java objects. This is standard practice in academic research to avoid costs and complexity.
*   **Algorithm Focus:** The primary focus is on the **Genetic Algorithm**. While other algorithms may be implemented for baseline comparison, the optimization efforts will focus on tuning GA parameters (Population Size, Mutation Rate, etc.).
*   **Performance Metrics:** The evaluation will focus on:
    *   **Makespan:** Total execution time.
    *   **Throughput:** Tasks completed per unit time.
    *   **Degree of Imbalance:** Statistical variance in VM load.
*   **Language:** The entire system will be built using **Core Java** to demonstrate strong object-oriented design and algorithmic logic without reliance on "black-box" frameworks that hide the internal workings.

**Limitations:**
*   The system assumes independent tasks (no precedence constraints or workflows).
*   Dynamic arrival of tasks (real-time streaming) is out of scope; we assume a "batch" of tasks is available at time zero (Static Scheduling).

---

## **5. Literature Review**

A review of existing literature reveals various approaches to the cloud task scheduling problem:

1.  **Heuristic Approaches:**
    *   *FCFS (First Come First Serve):* Simple but ignores task size and VM speed.
    *   *LJF (Longest Job First):* Good for utilization but can starve short tasks.
    *   *Min-Min:* Assigns the smallest task to the fastest VM. Good for makespan but results in poor load balancing as powerful VMs get all the work.

2.  **Meta-Heuristic Approaches:**
    *   *Particle Swarm Optimization (PSO):* Converges fast but often gets stuck in local optima.
    *   *Ant Colony Optimization (ACO):* Good for dynamic environments but computationally expensive due to pheromone updates.

3.  **Genetic Algorithm (Proposed):**
    *   Research suggests GA is superior for large-scale static scheduling because of its global search capability.
    *   *Paper Reference [1]:* "Resource Scheduling in Cloud Computing based on Genetic Algorithm" (Author et al.) demonstrated that GA reduced makespan by 15% compared to Min-Min.
    *   *Paper Reference [2]:* Studies show that hybridizing GA with local search techniques can further improve convergence speed.

**Gap Analysis:**
Most existing student-level implementations rely extensively on the CloudSim toolkit, which abstracts away the core logic. This project distinguishes itself by implementing the simulation engine *from scratch* in Java, providing a deeper understanding of the underlying mechanics of both the cloud model and the optimization algorithm.

---

## **6. Project Schedule/ Timeline**

The project will be executed over a period of 4 months (1 Semester), divided into the following phases:

| Phase | Duration | Activities |
| :--- | :--- | :--- |
| **1. Requirements Analysis** | Weeks 1-2 | Literature review, understanding CloudSim architecture, defining problem scope. |
| **2. High-Level Design** | Weeks 3-4 | Designing Class Diagrams (UML), defining the Chromosome structure, designing the Fitness Function. |
| **3. Implementation - Core** | Weeks 5-7 | Coding the basic classes: `Cloudlet`, `VM`, `Datacenter`. Implementing the random scheduler. |
| **4. Implementation - GA** | Weeks 8-11 | Implementing `GeneticAlgorithm.java`: Population initialization, Crossover, Mutation logic. Tuning parameters. |
| **5. Testing & Tuning** | Weeks 12-13 | Running simulations with varying numbers of tasks (50, 100, 500). Debugging concurrency issues. Comparing results. |
| **6. Documentation** | Weeks 14-15 | Writing the Final Report, creating presentation slides, and finalizing Project Synopsis. |

---

## **7. Proposed System/ Methodology**

The proposed system follows a standard Master-Slave or Broker-based architecture typical in cloud simulations.

### **7.1 System Architecture**
1.  **User/Client:** Submits a batch of Cloudlets (Tasks) with defined lengths (Instruction count).
2.  **Datacenter Broker:** Receives the tasks and requests the list of available VMs. It passes this data to the Scheduler.
3.  **GA Scheduler (Optimizer):** The core component. It takes the list of Tasks and VMs and runs the Genetic Algorithm to produce an efficient Mapping Map (Task ID -> VM ID).
4.  **Datacenter:** Executes the tasks based on the mapping provided by the scheduler and returns the execution results.

### **7.2 Genetic Algorithm Workflow**
The algorithm proceeds in the following steps:

1.  **Initialization:** A population of $N$ random schedules is generated. Each schedule (Chromosome) is an array where index $i$ represents Task $i$ and value $v$ represents the assigned VM $v$.
2.  **Fitness Evaluation:** For each schedule, the *Makespan* is calculated.
    *   $Time(Task_i, VM_j) = Size(Task_i) / Speed(VM_j)$
    *   $CompletionTime(VM_j) = \sum Time(Task_k)$ for all tasks assigned to $VM_j$.
    *   $Makespan = \max(CompletionTime(all VMs))$.
    *   Fitness = $1 / Makespan$ (since we want to minimize time).
3.  **Selection:** We utilize **Tournament Selection**. A subset of schedules is chosen randomly, and the fittest one is selected for the mating pool.
4.  **Crossover:** We use **One-Point Crossover**. Two parent schedules exchange segments of their task-vm mappings to create two new children, combining the traits of both parents.
5.  **Mutation:** With a low probability (e.g., 0.01), a task is randomly reassigned to a different VM. This maintains genetic diversity and prevents premature convergence.
6.  **Termination:** Steps 2-5 are repeated for a fixed number of generations (e.g., 100) or until the solution doesn't improve for a set period.

---

## **8. Tools and Technologies Used**

### **8.1 Hardware Requirements**
*   **Processor:** Intel Core i3 or higher (i5 recommended for faster simulation).
*   **RAM:** 4 GB minimum (8 GB recommended).
*   **Storage:** 100 MB free space.

### **8.2 Software Requirements**
*   **Operating System:** Windows 10/11, Linux (Ubuntu), or macOS.
*   **Programming Language:** **Java (JDK 8 or higher)**. Chosen for its robustness, object-oriented features, and built-in multi-threading support.
*   **Integrated Development Environment (IDE):** **IntelliJ IDEA** or **Eclipse**.
*   **Build Tool:** Implicit (Standard Java Project) or Maven (optional).
*   **Documentation Tools:** MS Word / Markdown for reports.
*   **Version Control:** Git & GitHub for code management.

---

## **9. Expected Outcomes**

Upon successful completion of the project, we expect the following outcomes:

1.  **Functional Simulation:** A working Java application that accepts a custom number of Tasks and VMs and simulates the scheduling process.
2.  **Optimization:** The system will demonstrate a clear reduction in Makespan compared to a random allocation strategy. For example, if random allocation takes 100 seconds, the GA approach should target < 85 seconds.
3.  **Visual Metrics:** The console will display the fitness value improving over generations, proving that the algorithm is "learning" and evolving.
4.  **Load Balancing:** The final schedule will show a more even distribution of tasks across the available VMs, avoiding situations where powerful VMs are idle.
5.  **Code Artifacts:** A clean, well-commented Java codebase implementing the Genetic Algorithm from scratch, suitable for academic submission and future extension (e.g., adding energy constraints).

---
