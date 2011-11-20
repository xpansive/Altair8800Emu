package com.xpansive.altair8800.opcodes;

import com.xpansive.altair8800.CPU;
import com.xpansive.altair8800.MathHelper;

public class JMP extends Opcode {

    @Override
    public int[] getByteCodes() {
        return new int[] {
                0xC3, //default
                0xCB  //alternate
        };
    }

    @Override
    public int getLength() {
        return 3;
    }

    @Override
    public int getCycles(int opcode) {
        return 10;
    }

    @Override
    public String getName(int opcode) {
        return "JMP";
    }
    
    @Override
    public void execute(CPU cpu, int lo, int hi, int opcode) {
        cpu.setProgramCounter(MathHelper.to16(lo, hi));
    }
}
