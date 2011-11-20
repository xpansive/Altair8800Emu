package com.xpansive.altair8800.opcodes;

import com.xpansive.altair8800.CPU;

public class NOP extends Opcode {

    @Override
    public int[] getByteCodes() {
        return new int[] {
                0x00, //default
                
                //alternative (not recommended)
                0x10,
                0x20,
                0x30,
                0x08,
                0x18,
                0x28,
                0x38
        };
    }
    
    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public int getCycles(int opcode) {
        return 4;
    }
    
    @Override
    public String getName(int opcode) {
        return "NOP";
    }
    
    @Override
    public void execute(CPU cpu, int arg0, int arg1, int opcode) {
    }
}
