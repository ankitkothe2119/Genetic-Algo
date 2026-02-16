# JIRA Board Structure - Cloud GA Scheduler Project

Use this document to create your JIRA board on [Atlassian Free Cloud](https://www.atlassian.com/software/jira/free).

**Project Name:** Cloud-GA-Scheduler
**Project Key:** CGS
**Methodology:** Scrum (2-week sprints)

---

## Epic 1: Core GA Engine (CGS-1)
**Description:** Implement the Genetic Algorithm optimization engine for cloud task scheduling.

| Story ID | Title | Story Points | Status |
|----------|-------|-------------|--------|
| CGS-2 | Create CloudTask model with priority support | 2 | Done |
| CGS-3 | Create VirtualMachine model with MIPS calculation | 2 | Done |
| CGS-4 | Implement ScheduleChromosome with fitness function | 5 | Done |
| CGS-5 | Implement GeneticAlgorithm (Selection, Crossover, Mutation) | 8 | Done |
| CGS-6 | Create Main class with console output | 3 | Done |
| CGS-7 | Add multi-threaded fitness evaluation | 5 | Done |

---

## Epic 2: Maven Build System (CGS-8)
**Description:** Convert project to Maven build system for dependency management and automated builds.

| Story ID | Title | Story Points | Status |
|----------|-------|-------------|--------|
| CGS-9  | Create pom.xml with CloudSim dependency | 3 | Done |
| CGS-10 | Restructure to Maven standard layout | 2 | Done |
| CGS-11 | Add Servlet/JSP dependencies | 2 | Done |
| CGS-12 | Configure WAR packaging plugin | 2 | Done |

---

## Epic 3: Web UI - Servlet/JSP (CGS-13)
**Description:** Build a web interface using Servlets and JSP for browser-based GA execution.

| Story ID | Title | Story Points | Status |
|----------|-------|-------------|--------|
| CGS-14 | Create GAServlet controller (MVC pattern) | 5 | Done |
| CGS-15 | Create index.jsp input form | 3 | Done |
| CGS-16 | Create results.jsp with allocation table | 5 | Done |
| CGS-17 | Configure web.xml servlet mapping | 2 | Done |
| CGS-18 | Add JSTL for dynamic table rendering | 3 | Done |

---

## Epic 4: CI/CD Pipeline - Jenkins (CGS-19)
**Description:** Set up automated build pipeline using Jenkins.

| Story ID | Title | Story Points | Status |
|----------|-------|-------------|--------|
| CGS-20 | Create Jenkinsfile with build stages | 3 | Done |
| CGS-21 | Configure GitHub webhook trigger | 2 | To Do |
| CGS-22 | Add artifact archiving step | 2 | Done |

---

## Epic 5: Future - Priority Scheduling (CGS-23)
**Description:** Enhance GA with priority-aware scheduling for QoS optimization.

| Story ID | Title | Story Points | Status |
|----------|-------|-------------|--------|
| CGS-24 | Design weighted fitness function for priority | 5 | To Do |
| CGS-25 | Implement priority penalty in fitness calculation | 5 | To Do |
| CGS-26 | Add priority filter in results UI | 3 | To Do |
| CGS-27 | Performance comparison: Priority vs Non-Priority | 5 | To Do |

---

## Sprint Plan

### Sprint 1 (Completed)
- Epic 1: Core GA Engine (All stories)
- **Velocity:** 25 points

### Sprint 2 (Completed)
- Epic 2: Maven Build System
- Epic 3: Web UI
- **Velocity:** 22 points

### Sprint 3 (Current)
- Epic 4: CI/CD Pipeline
- Epic 5: Priority Scheduling (In Progress)
- **Velocity Target:** 20 points
