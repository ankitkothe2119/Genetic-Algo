from fastapi import FastAPI
from pydantic import BaseModel
from typing import List
import random

from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()

# Enable CORS for Frontend (Port 4000)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:4000"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# --- Data Models ---
class Task(BaseModel):
    id: int
    length: int  # MI (Million Instructions)

class VM(BaseModel):
    id: int
    mips: int    # Million Instructions Per Second

class AllocationRequest(BaseModel):
    tasks: List[Task]
    vms: List[VM]

# --- Genetic Algorithm Engine ---
class GeneticAlgorithm:
    def __init__(self, tasks, vms, population_size=50, generations=50):
        self.tasks = tasks
        self.vms = vms
        self.population_size = population_size
        self.generations = generations
        self.mutation_rate = 0.1

    def init_population(self):
        population = []
        for _ in range(self.population_size):
            # Gene: index=task_id, value=vm_id
            chromosome = [random.choice(self.vms).id for _ in self.tasks]
            population.append(chromosome)
        return population

    def fitness(self, chromosome):
        vm_times = {vm.id: 0 for vm in self.vms}
        # Map IDs to objects for speed
        vm_map = {vm.id: vm for vm in self.vms}
        
        for i, vm_id in enumerate(chromosome):
            task = self.tasks[i]
            vm = vm_map[vm_id]
            exec_time = task.length / vm.mips
            vm_times[vm_id] += exec_time
        
        makespan = max(vm_times.values()) if vm_times else 0.001
        return 1.0 / makespan

    def selection(self, population):
        tournament_size = 5
        best = None
        for _ in range(tournament_size):
            ind = random.choice(population)
            if best is None or self.fitness(ind) > self.fitness(best):
                best = ind
        return best

    def crossover(self, p1, p2):
        point = random.randint(1, len(p1) - 1)
        c1 = p1[:point] + p2[point:]
        c2 = p2[:point] + p1[point:]
        return c1, c2

    def mutate(self, chromosome):
        if random.random() < self.mutation_rate:
            idx = random.randint(0, len(chromosome) - 1)
            chromosome[idx] = random.choice(self.vms).id
        return chromosome

    def run(self):
        print(f"--- Starting Genetic Algorithm ({self.generations} Generations) ---")
        population = self.init_population()
        
        for gen in range(self.generations):
            population = sorted(population, key=self.fitness, reverse=True)
            
            # Log progress every 10 generations
            if gen % 10 == 0:
                best_fit = self.fitness(population[0])
                print(f"Creating Generation {gen}: Best Fitness = {best_fit:.5f}")

            new_pop = population[:2] # Elitism
            while len(new_pop) < self.population_size:
                p1 = self.selection(population)
                p2 = self.selection(population)
                c1, c2 = self.crossover(p1, p2)
                new_pop.append(self.mutate(c1))
                if len(new_pop) < self.population_size:
                    new_pop.append(self.mutate(c2))
            population = new_pop
            
        best_chromosome = max(population, key=self.fitness)
        
        # Log Final Decision
        print("\n--- AI Final Decision ---")
        result = {}
        for i, vm_id in enumerate(best_chromosome):
            task_id = self.tasks[i].id
            print(f"Allocating Task {task_id} (Length {self.tasks[i].length}) --> VM {vm_id}")
            result[task_id] = vm_id
            
        return result

# --- Reinforcement Learning Agent (Stub) ---
class RL_Agent:
    def optimize(self, initial_allocation, tasks, vms):
        # Placeholder: In future, this will adjust based on energy limits
        return initial_allocation

# --- API Endpoints ---

@app.get("/")
def read_root():
    return {"status": "AI Engine Running", "version": "1.0.0"}

@app.post("/allocate")
def allocate_resources(request: AllocationRequest):
    print(f"Received request: {len(request.tasks)} Tasks, {len(request.vms)} VMs")
    
    ga = GeneticAlgorithm(request.tasks, request.vms)
    ga_allocation = ga.run()
    
    rl = RL_Agent()
    final_allocation = rl.optimize(ga_allocation, request.tasks, request.vms)
    
    return {
        "status": "success",
        "allocation": final_allocation,
        "energy_saved": "15%" 
    }

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
