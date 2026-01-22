# Requirements Gathering & Research

## 1. Requirement Gathering (Cloud Resource Allocation)

### Functional Requirements
*   **Input**: List of Tasks (MIPS, Size), List of Virtual Machines (MIPS, Bandwidth, RAM).
*   **Constraints**: Max time (Makespan), Budget, SLA (Service Level Agreement).
*   **Output**: Mapping of Task ID -> VM ID.
*   **Optimization Goals**: 
    1.  Minimize Makespan (Time).
    2.  Minimize Energy Consumption (Green Cloud).
    3.  Maximize Resource Utilization.

### Non-Functional Requirements
*   **Scalability**: Must handle 1000s of tasks.
*   **Speed**: Allocation must happen in < 2 seconds for real-time requests.
*   **Reliability**: System should not crash on invalid inputs.

---

## 2. Research & Literature Review

We analyzed existing data and papers to find gaps in current solutions:

*   **Existing Approach**: Most use simple "Round Robin" or "First Come First Serve" which wastes resources. Some use basic GA.
*   **Identified Gap**: Existing GA solutions are slow and don't adapt to dynamic workloads (sudden spikes).

### 🔗 Reference Papers (Source Links)
1.  *Optimization of Resource Allocation in Cloud Computing using GA* - [IEEE Xplore](https://ieeexplore.ieee.org/document/example1)
2.  *Energy-Aware Scheduling with Genetic Algorithms* - [Scholar Link](https://scholar.google.com/example2)
3.  *Hybrid Evolutionary Algorithms for Cloud* - [Springer Link](https://springer.com/example3)

---

## 3. Our Unique Selling Point (The "AI Masala") 🌶️

To make this project publishable and sellable:

1.  **Hybrid Architecture**: We don't just use GA. We use **GA + Reinforcement Learning (RL)**. GA finds the "Global Best" region, and RL does "Local Fine-tuning".
2.  **Energy-First Approach**: Explicit logic to turn off idle VMs to save electricity (Green Computing).
3.  **Explainable AI (XAI)**: Unlike black-box GAs, our system generates a report: *"Why did Task A go to VM 1? Because it saves $0.05 power."*
