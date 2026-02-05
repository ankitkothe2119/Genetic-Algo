package com.cloud.allocation;

public class VirtualMachine {
    private int id;
    private int mips; // Million Instructions Per Second

    public VirtualMachine(int id, int mips) {
        this.id = id;
        this.mips = mips;
    }

    public int getId() {
        return id;
    }

    public int getMips() {
        return mips;
    }

    // Calculate time to process a specific task length
    public double calculateExecutionTime(int taskLength) {
        return (double) taskLength / mips;
    }

    @Override
    public String toString() {
        return "VM" + id + "(MIPS:" + mips + ")";
    }
}
