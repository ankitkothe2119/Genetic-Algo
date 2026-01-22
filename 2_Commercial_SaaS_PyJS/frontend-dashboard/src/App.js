import { useState } from 'react';
import './App.css';

function App() {
  const [activeTab, setActiveTab] = useState('dashboard');
  const [stats, setStats] = useState({
    activeVMs: 5,
    totalTasks: 20,
    energyEfficiency: '0%'
  });
  const [allocationData, setAllocationData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [requestData, setRequestData] = useState(null);

  // Generate Dummy Data for Testing
  const generateTestData = () => {
    const tasks = Array.from({ length: 20 }, (_, i) => ({
      id: i,
      length: Math.floor(Math.random() * 4500) + 500 // 500-5000 MI
    }));
    const vms = Array.from({ length: 5 }, (_, i) => ({
      id: i,
      mips: Math.floor(Math.random() * 1500) + 500 // 500-2000 MIPS
    }));

    setRequestData({ tasks, vms });
    setStats({
      activeVMs: vms.length,
      totalTasks: tasks.length,
      energyEfficiency: 'Calculated after run...'
    });
    setAllocationData(null); // Reset previous run
    alert("Test Data Generated! Click 'Run Optimization' to process.");
  };

  const runOptimization = async () => {
    if (!requestData) {
      // Auto-generate if not exists
      generateTestData();
      return;
    }

    setLoading(true);
    try {
      const response = await fetch('http://localhost:8000/allocate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestData)
      });

      const data = await response.json();
      setAllocationData(data.allocation);
      setStats(prev => ({ ...prev, energyEfficiency: data.energy_saved }));
    } catch (error) {
      console.error("Optimization failed:", error);
      alert("Failed to connect to AI Engine. Is backend running?");
    }
    setLoading(false);
  };

  return (
    <div className="app-container">
      {/* Sidebar */}
      <aside className="sidebar glass-panel">
        <div className="logo">
          <h3>🧬 CloudGen <span style={{ color: 'var(--primary)' }}>AI</span></h3>
        </div>
        <nav>
          <button
            className={`nav-item ${activeTab === 'dashboard' ? 'active' : ''}`}
            onClick={() => setActiveTab('dashboard')}
          >
            Dashboard
          </button>
          <button className="nav-item" onClick={generateTestData}>
            Generate Test Data 🎲
          </button>
        </nav>
      </aside>

      {/* Main Content */}
      <main className="main-content">
        <header className="header glass-panel">
          <h2>Resource Optimizer</h2>
          <div className="user-profile">
            <span>Admin Status: </span>
            <span style={{ color: '#00ff88' }}>Online ●</span>
          </div>
        </header>

        <div className="content-grid">
          {/* Stats Cards */}
          <div className="stat-card glass-panel">
            <h4>Active VMs</h4>
            <div className="value">{stats.activeVMs}</div>
            <div className="trend">Server Health: Optima</div>
          </div>
          <div className="stat-card glass-panel">
            <h4>Pending Tasks</h4>
            <div className="value">{stats.totalTasks}</div>
            <div className="trend">Queue Load: Moderate</div>
          </div>
          <div className="stat-card glass-panel">
            <h4>Energy Saved</h4>
            <div className="value" style={{ color: 'var(--primary)' }}>{stats.energyEfficiency}</div>
            <div className="trend">vs. Round Robin</div>
          </div>

          {/* Allocation Visualization Area */}
          <div className="allocation-map glass-panel" style={{ gridColumn: '1 / -1' }}>
            <div className="map-header">
              <h3>Live Resource Map</h3>
              <button className="btn-primary" onClick={runOptimization} disabled={loading}>
                {loading ? 'Optimizing...' : 'Run Optimization ✨'}
              </button>
            </div>

            <div className="vm-grid" style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(150px, 1fr))', gap: '15px' }}>
              {!allocationData ? (
                <div className="placeholder-grid" style={{ gridColumn: '1/-1' }}>
                  <p style={{ textAlign: 'center', padding: '50px' }}>
                    {requestData ? "Data Loaded. Ready to Optimize." : "Click 'Generate Test Data' to start."}
                  </p>
                </div>
              ) : (
                requestData.vms.map(vm => {
                  // Find tasks assigned to this VM
                  const assignedTasks = requestData.tasks.filter(t => allocationData[t.id.toString()] === vm.id);
                  const load = assignedTasks.reduce((acc, t) => acc + t.length, 0);
                  const capacity = vm.mips * 10; // Dummy capacity logic
                  const isOverloaded = load > capacity;

                  return (
                    <div key={vm.id} className="glass-panel" style={{ borderColor: isOverloaded ? '#ff0055' : '#00ff88', minHeight: '150px' }}>
                      <h5>VM #{vm.id} <small>({vm.mips} MIPS)</small></h5>
                      <div style={{ marginTop: '10px', fontSize: '12px', color: '#aaa' }}>
                        Tasks: {assignedTasks.length} <br />
                        Load: {load} MI
                      </div>
                      <div style={{ marginTop: '10px', display: 'flex', flexWrap: 'wrap', gap: '2px' }}>
                        {assignedTasks.map(t => (
                          <div key={t.id} style={{ width: '10px', height: '10px', background: 'var(--primary)', borderRadius: '2px' }} title={`Task ${t.id}: ${t.length} MI`}></div>
                        ))}
                      </div>
                    </div>
                  );
                })
              )}
            </div>
          </div>

          {/* Detailed Allocation Log */}
          {allocationData && (
            <div className="logs-section glass-panel" style={{ gridColumn: '1 / -1', marginTop: '20px' }}>
              <h3>AI Decision Logs 📝</h3>
              <div style={{ maxHeight: '200px', overflowY: 'auto', marginTop: '10px' }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', color: '#ccc' }}>
                  <thead>
                    <tr style={{ borderBottom: '1px solid #444', textAlign: 'left' }}>
                      <th style={{ padding: '8px' }}>Task ID</th>
                      <th style={{ padding: '8px' }}>Size (MI)</th>
                      <th style={{ padding: '8px' }}>Assigned To</th>
                      <th style={{ padding: '8px' }}>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {requestData.tasks.map(t => (
                      <tr key={t.id} style={{ borderBottom: '1px solid #222' }}>
                        <td style={{ padding: '8px' }}>Task #{t.id}</td>
                        <td style={{ padding: '8px' }}>{t.length}</td>
                        <td style={{ padding: '8px', color: 'var(--primary)' }}>VM #{allocationData[t.id]}</td>
                        <td style={{ padding: '8px' }}>✅ Optimized</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}

export default App;
