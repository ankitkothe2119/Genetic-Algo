# Project Workflow & Architecture

## 🗓️ Development Timeline (Gantt Chart)

```mermaid
gantt
    title Genetic Algorithm Resource Allocation Project
    dateFormat  YYYY-MM-DD
    section Planning
    Requirement Gathering       :active, a1, 2026-01-17, 2d
    Research & Architecture     :a2, after a1, 3d
    
    section College (Java)
    Core GA Implementation      :2026-01-20, 5d
    Multi-threading Setup       :5d
    Testing & Reporting         :3d
    
    section Commercial (SaaS)
    Python AI Engine (GA+RL)    :2026-01-25, 7d
    API Layer (Node/FastAPI)    :5d
    Frontend Dashboard (JS)     :5d
    Integration & Docker        :3d
```

---

## 🔄 Data Flow Diagram (DFD) - Level 0

```text
   +-------------+        Request Resource       +-------------------+
   |  Cloud      | -------------------------->   |  SaaS API Gateway |
   |  Client     |                               |  (Node.js/Auth)   |
   +-------------+                               +-------------------+
          ^                                               |
          | Allocation Response                           | Forward Request
          | (JSON Plan)                                   v
          |                                      +-------------------+
          +------------------------------------- |  AI Engine (Py)   |
                                                 |  (GA + RL Model)  |
                                                 +-------------------+
```

---

## 🔄 Data Flow Diagram (DFD) - Level 1 (Inside AI Engine)

1.  **Input Parsing**: Receive JSON (VMs, Tasks, Constraints).
2.  **Population Init**: Create random allocation maps.
3.  **GA Loop**:
    *   **Fitness Calc**: Calculate Cost, Time, Energy.
    *   **Selection**: Pick best parents.
    *   **Crossover/Mutation**: Mix and modify.
4.  **Optimization**: Commercial version runs RL agent to fine-tune.
5.  **Output**: Best allocation map returned to API.
