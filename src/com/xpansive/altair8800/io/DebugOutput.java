package com.xpansive.altair8800.io;

import java.io.PrintStream;

import com.xpansive.altair8800.CPU;
import com.xpansive.altair8800.Memory;
import com.xpansive.altair8800.Register;
import com.xpansive.altair8800.opcodes.Opcode;
import com.xpansive.altair8800.opcodes.OpcodeFactory;

public class DebugOutput {
    private PrintStream out;

    public DebugOutput(PrintStream output) {
        this.out = output;
    }

    public void error(String message) {
        out.println("Error: " + message);
    }

    public void printDebugInfo(CPU cpu, int memLocation) {
        Memory mem = cpu.getMemory();
        Opcode op = OpcodeFactory.fromByteCode(mem.readByte(memLocation));
        if (op == null)
            return;

        String text = String.format(
                "%-8s%-10s%-10sA=%-6sB=%-6sC=%-6sD=%-6sE=%-6sF=%-6sH=%-6sL=%-6sSP=%-6sF=%-10s", 

                padHex(memLocation, 4), // Memory location
                op.getName(mem.readByte(memLocation)), // Name
                op.getLength() == 3 ? padHex(mem.readWord(memLocation + 1), 4) : // Word argument
                op.getLength() == 2 ? padHex(mem.readByte(memLocation + 1), 2) : // Byte argument
                        "", // No argument

                // Registers
                padHex(cpu.getRegisterValue(Register.A), 2),
                padHex(cpu.getRegisterValue(Register.B), 2),
                padHex(cpu.getRegisterValue(Register.C), 2),
                padHex(cpu.getRegisterValue(Register.D), 2),
                padHex(cpu.getRegisterValue(Register.E), 2),
                padHex(cpu.getRegisterValue(Register.F), 2),
                padHex(cpu.getRegisterValue(Register.H), 2),
                padHex(cpu.getRegisterValue(Register.L), 2),

                padHex(cpu.getRegisterValue(Register.SP), 2), // Stack pointer
                padBin(cpu.getFlags(), 8)); // Flags

        out.println(text);
    }

    private String padHex(int num, int pad) {
        return "0x" + String.format("%" + pad + "s", Integer.toHexString(num).toUpperCase()).replace(' ', '0');
    }

    private String padBin(int num, int pad) {
        return "0b" + String.format("%" + pad + "s", Integer.toBinaryString(num)).replace(' ', '0');
    }
}
