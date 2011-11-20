package com.xpansive.altair8800.opcodes;

import java.util.EnumSet;

import com.xpansive.altair8800.CPU;
import com.xpansive.altair8800.MathHelper;
import com.xpansive.altair8800.Register;

public class LDA extends Opcode {

    @Override
    public int[] getByteCodes() {
        return new int[] {
                0x3A
        };
    }

    @Override
    public void execute(CPU cpu, int arg0, int arg1, int opcode) {
        cpu.setRegisterValue(Register.A, cpu.getMemory().readByte(MathHelper.to16(arg0, arg1)));
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
    public EnumSet<Register> getAffectedRegisters(int opcode) {
        return EnumSet.of(Register.A);
    }

    @Override
    public String getName(int opcode) {
        return "LDA";
    }
}
