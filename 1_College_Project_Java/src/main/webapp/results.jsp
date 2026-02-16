<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>GA Results - Cloud Resource Allocation</title>
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }

                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    background: linear-gradient(135deg, #0f0c29, #302b63, #24243e);
                    min-height: 100vh;
                    color: #fff;
                    padding: 30px;
                }

                .header {
                    text-align: center;
                    margin-bottom: 30px;
                }

                .header h1 {
                    font-size: 1.8em;
                    background: linear-gradient(90deg, #00d2ff, #3a7bd5);
                    -webkit-background-clip: text;
                    -webkit-text-fill-color: transparent;
                }

                .header p {
                    color: rgba(255, 255, 255, 0.6);
                    margin-top: 5px;
                }

                .stats-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
                    gap: 16px;
                    margin-bottom: 30px;
                }

                .stat-card {
                    background: rgba(255, 255, 255, 0.08);
                    backdrop-filter: blur(10px);
                    border: 1px solid rgba(255, 255, 255, 0.12);
                    border-radius: 14px;
                    padding: 20px;
                    text-align: center;
                }

                .stat-card .value {
                    font-size: 1.6em;
                    font-weight: 700;
                    color: #00d2ff;
                }

                .stat-card .label {
                    font-size: 0.85em;
                    color: rgba(255, 255, 255, 0.6);
                    margin-top: 4px;
                }

                .section {
                    background: rgba(255, 255, 255, 0.06);
                    backdrop-filter: blur(10px);
                    border: 1px solid rgba(255, 255, 255, 0.1);
                    border-radius: 16px;
                    padding: 24px;
                    margin-bottom: 24px;
                }

                .section h2 {
                    font-size: 1.2em;
                    margin-bottom: 16px;
                    color: #00d2ff;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                }

                th {
                    background: rgba(58, 123, 213, 0.3);
                    padding: 10px 12px;
                    text-align: left;
                    font-size: 0.85em;
                    color: rgba(255, 255, 255, 0.9);
                    border-bottom: 1px solid rgba(255, 255, 255, 0.15);
                }

                td {
                    padding: 8px 12px;
                    font-size: 0.85em;
                    border-bottom: 1px solid rgba(255, 255, 255, 0.06);
                    color: rgba(255, 255, 255, 0.8);
                }

                tr:hover td {
                    background: rgba(255, 255, 255, 0.04);
                }

                .priority-high {
                    color: #ff6b6b;
                    font-weight: 600;
                }

                .priority-medium {
                    color: #ffd93d;
                    font-weight: 600;
                }

                .priority-low {
                    color: #6bff9e;
                    font-weight: 600;
                }

                .back-btn {
                    display: inline-block;
                    padding: 12px 28px;
                    border: 1px solid rgba(255, 255, 255, 0.2);
                    border-radius: 10px;
                    color: #fff;
                    text-decoration: none;
                    font-size: 0.95em;
                    transition: all 0.3s;
                }

                .back-btn:hover {
                    background: rgba(255, 255, 255, 0.1);
                    border-color: #00d2ff;
                }
            </style>
        </head>

        <body>
            <div class="header">
                <h1>Optimization Results</h1>
                <p>Genetic Algorithm Cloud Resource Allocation</p>
            </div>

            <!-- Stats Cards -->
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="value">${taskCount}</div>
                    <div class="label">Cloud Tasks</div>
                </div>
                <div class="stat-card">
                    <div class="value">${vmCount}</div>
                    <div class="label">Virtual Machines</div>
                </div>
                <div class="stat-card">
                    <div class="value">${generations}</div>
                    <div class="label">Generations</div>
                </div>
                <div class="stat-card">
                    <div class="value">${bestMakespan}s</div>
                    <div class="label">Best Makespan</div>
                </div>
            </div>

            <!-- Allocation Table -->
            <div class="section">
                <h2>Final Optimal Allocation</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Task ID</th>
                            <th>Size (MI)</th>
                            <th>Priority</th>
                            <th>VM ID</th>
                            <th>VM MIPS</th>
                            <th>Exec Time (s)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="row" items="${allocationTable}">
                            <tr>
                                <td>${row[0]}</td>
                                <td>${row[1]}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${row[2] == 'High'}">
                                            <span class="priority-high">${row[2]}</span>
                                        </c:when>
                                        <c:when test="${row[2] == 'Medium'}">
                                            <span class="priority-medium">${row[2]}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="priority-low">${row[2]}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${row[3]}</td>
                                <td>${row[4]}</td>
                                <td>${row[5]}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Generation History -->
            <div class="section">
                <h2>Evolution Progress</h2>
                <table>
                    <thead>
                        <tr>
                            <th>Generation</th>
                            <th>Best Makespan (s)</th>
                            <th>Best Fitness</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="gen" items="${generationHistory}">
                            <c:if test="${gen[0] % 10 == 0 || gen[0] == 1}">
                                <tr>
                                    <td>${String.valueOf((int)gen[0])}</td>
                                    <td>${String.format("%.4f", gen[1])}</td>
                                    <td>${String.format("%.6f", gen[2])}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <a href="ga" class="back-btn">&#8592; Run Again</a>
        </body>

        </html>