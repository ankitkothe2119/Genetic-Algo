package com.cloud.allocation;

public class CloudTask {
    private int id;
    private int length; // Length in Million Instructions (MI)
    private int priority; // 1=Low, 2=Medium, 3=High

    public CloudTask(int id, int length, int priority) {
        this.id = id;
        this.length = length;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public int getPriority() {
        return priority;
    }

    public String getPriorityLabel() {
        switch (priority) {
            case 3:
                return "High";
            case 2:
                return "Medium";
            case 1:
                return "Low";
            default:
                return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "Task" + id + "(Len:" + length + ", Pri:" + getPriorityLabel() + ")";
    }
}
