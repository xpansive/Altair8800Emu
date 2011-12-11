package com.xpansive.altair8800;

public class Memory {
    private int[] memory;
    private int size;

    public Memory(int size) {
        memory = new int[size];
        this.size = size;
    }

    public int readByte(int loc) {
        if (loc <= size)
            return memory[loc];
        return 0;
    }

    public void writeByte(int loc, int val) {
        if (loc <= size)
            memory[loc] = val;
    }

    public int readWord(int loc) {
        if (loc + 1 <= size)
            return memory[loc] | memory[loc + 1] << 8;
        return 0;
    }

    public void writeWord(int loc, int val) {
        if (loc + 1 <= size) {
            memory[loc] = (byte) (val & 0xFF);
            memory[loc + 1] = (byte) (val >> 8);
        }
    }

    public int[] readBytes(int loc, int num) {
        if (loc + num <= size) {
            int[] temp = new int[num];
            System.arraycopy(memory, loc, temp, 0, num);
            return temp;
        }
        return new int[0];
    }

    public void writeBytes(int loc, int[] values) {
        if (loc + values.length <= size)
            System.arraycopy(values, 0, memory, loc, values.length);
    }
}
