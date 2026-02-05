const express = require('express');
const app = express();
const port = 3000;

app.use(express.json());

// Mock database (in-memory)
let users = [{ id: 1, name: 'DemoUser', credits: 100 }];

app.get('/', (req, res) => {
    res.send('SaaS API Gateway Running! connects transparently to Python AI Engine.');
});

// Proxy endpoint: Forward requests to Python AI Engine
app.post('/api/optimize', async (req, res) => {
    // In production, this would use 'axios' to call the Python service
    // const response = await axios.post('http://localhost:8000/allocate', req.body);

    console.log("Received optimization request:", req.body);

    // Simulate response for now
    res.json({
        message: "Request forwarded to AI Engine",
        job_id: "JOB-12345",
        status: "processing"
    });
});

app.listen(port, () => {
    console.log(`API Gateway listening at http://localhost:${port}`);
});
