package com.xpansive.altair8800;

import java.util.Scanner;

import com.xpansive.altair8800.opcodes.Opcode;
import com.xpansive.altair8800.opcodes.OpcodeFactory;

public class Altair8800 {

    /**
     * @param args
     */
    public static void main(String[] args) {   
        CPU proc = new CPU();
        int memory[] = new int[] { 
        		0x3A, 0x08, 0x00, 	//LDA 0x0008
                0xC6, 0x05,         //ADI 0x05
                0xC3, 0x03, 0x00, 	//JMP 0x0000
                0x00
        };
        proc.getMemory().writeBytes(0, memory);
        Scanner s = new Scanner(System.in);
        for (;;) {
            boolean valid;
            do {
                valid = true;
                System.out.print("-");
                String in = s.nextLine();
                
                switch (in.length() > 0 ? in.charAt(0) : ' ') {
                case 'g':
                    try {
                        int times = Integer.parseInt(in.substring(1).trim());
                        for (int i = 0; i < times; i++) {
                            int pc = proc.getProgramCounter();
                            proc.run(1);
                            System.out.println(opcodeToString(proc, pc));
                        }
                    } catch(NumberFormatException ex) {
                        valid = false;
                        break;
                    }
                    break;
                    
                case 'q':
                    return;

                default:
                    valid = false;
                    break;
                }
                
                if (!valid) 
                    System.out.println(
                            "Available commands:\n" + 
                            "g num\t\tRuns until the specified number of opcodes has been reached.\n" +
                            "q\t\tQuits the emulator");
            } while (!valid);
        }
    }

    private static String opcodeToString(CPU cpu, int memLocation) {
        StringBuilder sb = new StringBuilder();
        Memory mem = cpu.getMemory();
        Opcode op = OpcodeFactory.fromByteCode(mem.readByte(memLocation));
        sb.append(padHex(memLocation, 4)); // Memory location
        sb.append("\t" + op.getName(mem.readByte(memLocation)) + "       "); // Opcode name
        switch (op.getLength()) {
        case 3:
            sb.append(padHex(mem.readByte(memLocation + 1) | (mem.readByte(memLocation + 1) << 8), 4)); //word argument
            break;
        case 2:
            sb.append(padHex(mem.readByte(memLocation + 1), 2)); //single byte argument
            //Fallthrough to append an extra tab
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

        //Flags
        sb.append("\tF=" + padBin(cpu.getFlags(), 8));

        return sb.toString();
    }

    private static String padHex(int num, int pad) {
        return "0x" + String.format("%" + pad + "s", Integer.toHexString(num)).replace(' ', '0');
    }

    private static String padBin(int num, int pad) {
        return "0b" + String.format("%" + pad + "s", Integer.toBinaryString(num)).replace(' ', '0');
    }
}
