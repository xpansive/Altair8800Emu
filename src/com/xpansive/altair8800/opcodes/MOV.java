package com.xpansive.altair8800.opcodes;

import java.util.EnumSet;

import com.xpansive.altair8800.CPU;
import com.xpansive.altair8800.MathHelper;
import com.xpansive.altair8800.Register;

public class MOV extends Opcode {
    
    private Register[] registerValues = new Register[] {
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
    public int[] getByteCodes() {
        return new int[] {
                0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49, 0x4A, 0x4B, 0x4C, 0x4D, 0x4E, 0x4F,
                0x50, 0x51, 0x52, 0x53, 0x55, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5A, 0x5B, 0x5C, 0x5D, 0x5E, 0x5F,
                0x60, 0x61, 0x62, 0x63, 0x66, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
                0x70, 0x71, 0x72, 0x73, 0x77, 0x75,       0x77, 0x78, 0x79, 0x7A, 0x7B, 0x7C, 0x7D, 0x7E, 0x7F
        };
    }

    @Override
    public void execute(CPU cpu, int arg0, int arg1, int opcode) {
    	Register srcReg = getSrcReg(opcode);
        Register destReg = getDestReg(opcode);
        
        if (srcReg == Register.HL) {
        	int memPos = cpu.getRegisterValue(srcReg);
        	int srcVal = cpu.getMemory().readByte(memPos);
        	cpu.setRegisterValue(destReg, srcVal);
        }
        else if (destReg == Register.HL) {
        	int memPos = cpu.getRegisterValue(destReg);
        	cpu.getMemory().writeByte(memPos, cpu.getRegisterValue(srcReg));
        }
        else {
        	cpu.setRegisterValue(destReg, cpu.getRegisterValue(srcReg));
        }
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public int getCycles(int opcode) {
        Register srcReg = getSrcReg(opcode);
        Register destReg = getDestReg(opcode);
        
        return srcReg == Register.HL || destReg == Register.HL ? 7 : 5;
    }

    @Override
    public String getName(int opcode) {
    	Register srcReg = getSrcReg(opcode);
        Register destReg = getDestReg(opcode);
        
        return "MOV " + destReg.name() + "," + srcReg.name();
    }
    
    @Override
    public EnumSet<Register> getAffectedRegisters(int opcode) {
        return EnumSet.of(getDestReg(opcode));
    }
    
    private Register getSrcReg(int opcode) {
    	int src = MathHelper.bitRange16(opcode, 0, 3);
        
        return registerValues[src];
    }
    
    private Register getDestReg(int opcode) {
    	int dest = MathHelper.bitRange16(opcode, 3, 6);
        
        return registerValues[dest];
    }
}
