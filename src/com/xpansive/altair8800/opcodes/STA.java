package com.xpansive.altair8800.opcodes;

import com.xpansive.altair8800.CPU;
import com.xpansive.altair8800.MathHelper;
import com.xpansive.altair8800.Register;

public class STA extends Opcode {

    @Override
    public int[] getByteCodes() {
        return new int[] {
                0x32
        };
    }

    @Override
    public void execute(CPU cpu, int lo, int hi, int opcode) {
        cpu.getMemory().writeByte(MathHelper.to16(lo, hi), (byte) cpu.getRegisterValue(Register.A));
    }

    @Override
    public int getLength() {
        return 3;
    }

    @Override
    public int getCycles(int opcode) {
        return 13;
    }

    @Override
    public String getName(int opcode) {
        return "STA";
    }
}
