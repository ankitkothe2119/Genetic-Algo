# How to Setup & Run Everything Locally 🖥️

This guide will help you run both the **College Java Project** and the **Commercial SaaS Product** on your local machine.

---

## 🏗️ Part 1: Running the Java Project (College)

**Goal**: Run the core algorithm in a terminal and see the allocation output.

### Prerequisites (Check these first)
1.  **Java Development Kit (JDK)**: Make sure you have Java installed.
    *   Check command: `java -version`
    *   If not installed, download from [Oracle](https://www.oracle.com/java/technologies/downloads/).

### Steps
1.  **Open Terminal / CMD**.
2.  Navigate to the Java source folder:
    ```bash
    cd "c:\Users\Nice\Downloads\Genetic-Algo-main\1_College_Project_Java\src"
    ```
3.  **Compile the Code**:
    *   Since the code has a package `com.cloud.allocation`, we need to go one step back to compile properly, or just compile all files:
    
    *Recommended way (from `src` directory):*
    ```bash
    javac -d . *.java
    ```
    *(This creates a `com/cloud/allocation` folder with .class files)*

4.  **Run the Main Class**:
    ```bash
    java com.cloud.allocation.Main
    ```
    
    **✅ Success:** You should see generations printing (Gen 1... Gen 100) and the final allocation map.

---

## 🚀 Part 2: Running the SaaS Product (Commercial)

**Goal**: Start the Python AI Engine (Backend) and the HTML Dashboard (Frontend).

### Phase A: Backend (Python AI Engine)

1.  **Install Python**:
    *   Check command: `python --version`
2.  **Navigate to Backend Folder**:
    ```bash
    cd "c:\Users\Nice\Downloads\Genetic-Algo-main\2_Commercial_SaaS_PyJS\backend_ai_engine"
    ```
3.  **Create a Virtual Environment (Recommended)**:
    ```bash
    python -m venv venv
    
    # Activate (Windows):
    .\venv\Scripts\activate
    ```
4.  **Install Dependencies**:
    ```bash
    pip install fastapi uvicorn numpy pydantic
    ```
5.  **Run the Server**:
    ```bash
    uvicorn main:app --reload
    ```
    *   It will say: `Uvicorn running on http://127.0.0.1:8000`
    *   Keep this terminal **OPEN**.

### Phase B: Frontend (Node.js API + Dashboard)

1.  **Install Node.js**:
    *   Check command: `node -v`
2.  **Open a NEW Terminal**.
3.  **Navigate to API Layer**:
    ```bash
    cd "c:\Users\Nice\Downloads\Genetic-Algo-main\2_Commercial_SaaS_PyJS\api_layer"
    ```
4.  **Install & Run**:
    ```bash
    npm install express
    node server.js
    ```
    *   It will run on Port 3000.

5.  **View the Dashboard**:
    *   Go to `c:\Users\Nice\Downloads\Genetic-Algo-main\2_Commercial_SaaS_PyJS\frontend_dashboard`
    *   Double click `index.html` (It will open in Chrome/Edge).
    *   Click the **"Run Optimization 🚀"** button to see the magic!

---

## 🛠️ Troubleshooting

*   **Java Error: 'javac' not recognized?**
    *   You need to add Java `bin` path to your Windows Environment Variables.
*   **Python Error: 'pip' not found?**
    *   Make sure you checked "Add Python to PATH" during installation.
