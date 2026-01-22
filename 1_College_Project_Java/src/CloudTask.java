package com.cloud.allocation;

public class CloudTask {
    private int id;
    private int length; // Length in Million Instructions (MI)

    public CloudTask(int id, int length) {
        this.id = id;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Task" + id + "(Len:" + length + ")";
    }
}
