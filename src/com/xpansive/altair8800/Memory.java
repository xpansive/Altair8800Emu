package com.xpansive.altair8800;

public class Memory {
    private int[] memory;
    
    public Memory(int size) {
        memory = new int[size];
    }
    
    public int readByte(int loc) {
        return memory[loc];
    }
    
    public void writeByte(int loc, int val) {
        memory[loc] = val;
    }
    
    public int readWord(int loc) {
        return memory[loc] | memory[loc + 1] << 8;
    }
    
    public void writeWord(int loc, int val) {
        memory[loc] = val & 0xFF;
        memory[loc + 1] = val >> 8;
    }
    
    public int[] readBytes(int loc, int num) {
        int[] temp = new int[num];
        System.arraycopy(memory, loc, temp, 0, num);
        return temp;
    }
    
    public void writeBytes(int loc, int[] values) {
        System.arraycopy(values, 0, memory, loc, values.length);
    }
}
