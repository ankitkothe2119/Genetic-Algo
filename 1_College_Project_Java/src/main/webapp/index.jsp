<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cloud GA Scheduler - Genetic Algorithm Resource Allocation</title>
    <meta name="description" content="Cloud Resource Allocation using Genetic Algorithm. Optimize task scheduling across Virtual Machines.">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: #fff;
        }
        .container {
            background: rgba(255,255,255,0.08);
            backdrop-filter: blur(20px);
            border: 1px solid rgba(255,255,255,0.15);
            border-radius: 20px;
            padding: 40px;
            max-width: 500px;
            width: 90%;
            box-shadow: 0 8px 32px rgba(0,0,0,0.3);
        }
        h1 {
            text-align: center;
            margin-bottom: 8px;
            font-size: 1.6em;
            background: linear-gradient(90deg, #00d2ff, #3a7bd5);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        .subtitle {
            text-align: center;
            color: rgba(255,255,255,0.6);
            font-size: 0.9em;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-size: 0.95em;
            color: rgba(255,255,255,0.8);
        }
        input[type="number"] {
            width: 100%;
            padding: 12px 16px;
            border: 1px solid rgba(255,255,255,0.2);
            border-radius: 10px;
            background: rgba(255,255,255,0.06);
            color: #fff;
            font-size: 1em;
            outline: none;
            transition: border 0.3s;
        }
        input[type="number"]:focus {
            border-color: #3a7bd5;
        }
        .btn {
            width: 100%;
            padding: 14px;
            border: none;
            border-radius: 12px;
            background: linear-gradient(135deg, #3a7bd5, #00d2ff);
            color: #fff;
            font-size: 1.1em;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s, box-shadow 0.2s;
            margin-top: 10px;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(58,123,213,0.4);
        }
        .info {
            margin-top: 20px;
            padding: 12px;
            background: rgba(0,210,255,0.1);
            border-radius: 10px;
            font-size: 0.82em;
            color: rgba(255,255,255,0.7);
            line-height: 1.5;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Cloud GA Scheduler</h1>
        <p class="subtitle">Genetic Algorithm based Resource Allocation</p>

        <form action="ga" method="POST" id="gaForm">
            <div class="form-group">
                <label for="taskCount">Number of Cloud Tasks</label>
                <input type="number" id="taskCount" name="taskCount" value="20" min="2" max="500" required>
            </div>
            <div class="form-group">
                <label for="vmCount">Number of Virtual Machines</label>
                <input type="number" id="vmCount" name="vmCount" value="5" min="2" max="50" required>
            </div>
            <div class="form-group">
                <label for="generations">Number of Generations</label>
                <input type="number" id="generations" name="generations" value="100" min="10" max="1000" required>
            </div>
            <div class="form-group">
                <label for="popSize">Population Size</label>
                <input type="number" id="popSize" name="popSize" value="50" min="10" max="500" required>
            </div>
            <button type="submit" class="btn">Run Genetic Algorithm</button>
        </form>

        <div class="info">
            <strong>How it works:</strong> The Genetic Algorithm creates random schedules,
            then uses Selection, Crossover & Mutation to evolve towards the optimal
            Task-to-VM allocation that minimizes total execution time (Makespan).
        </div>
    </div>
</body>
</html>
