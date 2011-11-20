package com.xpansive.altair8800.opcodes;

import java.util.EnumSet;

import com.xpansive.altair8800.CPU;
import com.xpansive.altair8800.Flags;
import com.xpansive.altair8800.MathHelper;
import com.xpansive.altair8800.Register;

public class ADD extends Opcode {
	
    private static Register[] registerValues = new Register[] {
            Register.B,
            Register.C,
            Register.D,
            Register.E,
            Register.H,
            Register.L,
            Register.HL,
            Register.A
        };

	@Override
	public void execute(CPU cpu, int arg0, int arg1, int opcode) {
	    Register reg = registerValues[MathHelper.bitRange16(opcode, 0, 3)];
	    
	    int a = cpu.getRegisterValue(Register.A);
	    int num = cpu.getRegisterValue(reg);
	    
	    if (reg == Register.HL) {
	        num = cpu.getMemory().readByte(num);
	    }
	    int sum = a + num;
	    
	    sum = setFlags(sum, cpu, EnumSet.allOf(Flags.class));
        cpu.setRegisterValue(Register.A, sum);
	}

	@Override
	public int[] getByteCodes() {
		return new int[] {
				0x80, 0x81, 0x82, 0x83, 0x84, 0x85, 0x86, 0x87
		};
	}
	
	@Override
	public EnumSet<Flags> getAffectedFlags(int opcode) {
        return EnumSet.allOf(Flags.class);
	}

	@Override
	public int getCycles(int opcode) {
		Register reg = registerValues[MathHelper.bitRange16(opcode, 0, 3)];
		return reg == Register.HL ? 7 : 4; //Reg - reg takes 4 cycles; reg - mem 7
	}

	@Override
	public int getLength() {
		return 1;
	}

	@Override
	public String getName(int opcode) {
		return "ADD " + registerValues[MathHelper.bitRange16(opcode, 0, 3)];
	}

}
