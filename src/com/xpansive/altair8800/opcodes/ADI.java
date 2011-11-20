package com.xpansive.altair8800.opcodes;

import java.util.EnumSet;

import com.xpansive.altair8800.CPU;
import com.xpansive.altair8800.Flags;
import com.xpansive.altair8800.Register;

public class ADI extends Opcode {

    @Override
    public int[] getByteCodes() {
        return new int[] {
                0xC6
        };
    }

    @Override
    public void execute(CPU cpu, int arg0, int arg1, int opcode) {
        int a = cpu.getRegisterValue(Register.A);
        
        int sum = a + arg0;
        
        sum = setFlags(sum, cpu, EnumSet.allOf(Flags.class));
        
        cpu.setRegisterValue(Register.A, sum);
    }

    @Override
    public int getLength() {
        return 2;
    }

    @Override
    public int getCycles(int opcode) {
        return 7;
    }

    @Override
    public String getName(int opcode) {
        return "ADI";
    }

}
