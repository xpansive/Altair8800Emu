package com.xpansive.altair8800;

import java.util.HashMap;

import com.xpansive.altair8800.opcodes.Opcode;
import com.xpansive.altair8800.opcodes.OpcodeFactory;

public class CPU {

    private int flags;
    private HashMap<Register, Integer> registers = new HashMap<Register, Integer>();
    private int programCounter;
    private Memory memory;

    public CPU() {
        // Set all registers to 0
        for (Register register : Register.values()) {
            registers.put(register, 0);
        }

        memory = new Memory(256);
    }

    public int getFlags() {
        return flags;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public int getRegisterValue(Register register) {
        if (register.getWidth() == 8 || register == Register.SP)
            return registers.get(register);

        // 16bit
        int loByte = registers.get(register.getLowByte());
        int hiByte = registers.get(register.getHighByte());
        return MathHelper.to16(loByte, hiByte);
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public void setProgramCounter(int value) {
        programCounter = value;
    }

    public void setRegisterValue(Register register, int value) {
        // Special case for the stack pointer
        if (register.getWidth() == 8 || register == Register.SP) {
            registers.put(register, value);
        } else { // 16bit
            Register loByte = register.getLowByte();
            Register hiByte = register.getHighByte();
            registers.put(loByte, MathHelper.loByte(value));
            registers.put(hiByte, MathHelper.hiByte(value));
        }
    }

    public void wait(int cycles) {
        // TODO: Timing
    }

    public Memory getMemory() {
        return memory;
    }

    public void run(int numOpcodes) {
        for (int i = 0; i < numOpcodes; i++) {
            Opcode opcode = OpcodeFactory.fromByteCode(memory.readByte(programCounter));
            if (opcode == null) {
                System.out.println("Error: unreconignized opcode " + memory.readByte(programCounter));
            }
            int opcodeLoc = programCounter;
            programCounter += opcode.getLength();

            // Use the next two bytes as operands for the opcode.
            // Even if it doesn't use them, this simplifies the code quite a
            // bit.
            opcode.execute(this, memory.readByte(opcodeLoc + 1), memory.readByte(opcodeLoc + 2), memory.readByte(opcodeLoc));
            wait(opcode.getCycles(memory.readByte(opcodeLoc)));
        }
    }
}
