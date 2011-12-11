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
        StringBuilder sb = new StringBuilder();
        Memory mem = cpu.getMemory();
        Opcode op = OpcodeFactory.fromByteCode(mem.readByte(memLocation));
        if (op == null)
            return;

        sb.append(padHex(memLocation, 4)); // Memory location
        sb.append("\t" + op.getName(mem.readByte(memLocation)) + "       "); // Opcode name
        switch (op.getLength()) {
        case 3:
            sb.append(padHex(mem.readByte(memLocation + 1) | (mem.readByte(memLocation + 2) << 8), 4)); // word argument
            break;
        case 2:
            sb.append(padHex(mem.readByte(memLocation + 1), 2)); // single byte argument
            // Fallthrough to append an extra tab
        default:
            sb.append("\t");
            break;
        }
        // Add in register values
        sb.append("\tA=" + cpu.getRegisterValue(Register.A));
        sb.append("\tB=" + cpu.getRegisterValue(Register.B));
        sb.append("\tC=" + cpu.getRegisterValue(Register.C));
        sb.append("\tD=" + cpu.getRegisterValue(Register.D));
        sb.append("\tE=" + cpu.getRegisterValue(Register.E));
        sb.append("\tH=" + cpu.getRegisterValue(Register.H));
        sb.append("\tL=" + cpu.getRegisterValue(Register.L));
        sb.append("\tSP=" + cpu.getRegisterValue(Register.SP));

        // Flags
        sb.append("\tF=" + padBin(cpu.getFlags(), 8));

        out.println(sb.toString());
    }

    private String padHex(int num, int pad) {
        return "0x" + String.format("%" + pad + "s", Integer.toHexString(num)).replace(' ', '0');
    }

    private String padBin(int num, int pad) {
        return "0b" + String.format("%" + pad + "s", Integer.toBinaryString(num)).replace(' ', '0');
    }
}
