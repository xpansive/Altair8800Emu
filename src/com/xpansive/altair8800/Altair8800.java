package com.xpansive.altair8800;

import java.io.IOException;
import java.util.Scanner;

import com.xpansive.altair8800.io.DebugOutput;
import com.xpansive.altair8800.io.FileReader;

public class Altair8800 {
    public static void main(String[] args) {
        CPU proc = new CPU();
        DebugOutput debug = new DebugOutput(System.out);

        if (args.length < 1) {
            debug.error("No file specified!");
            return;
        }
        if (!loadProgram(proc, args[0])) {
            return;
        }

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
                            debug.printDebugInfo(proc, pc);
                        }
                    } catch (NumberFormatException ex) {
                        valid = false;
                    }
                    break;

                case 'q':
                    return;

                default:
                    valid = false;
                }

                if (!valid) {
                    System.out.println("Available commands:\n" + "g num\t\tRuns until the specified number of opcodes has been reached.\n" + "q\t\tQuits the emulator");
                }
            } while (!valid);
        }
    }

    private static boolean loadProgram(CPU cpu, String filename) {
        FileReader reader = new FileReader();
        DebugOutput debug = new DebugOutput(System.out);
        int[] data;

        try {
            data = reader.read(filename);
        } catch (IOException e) {
            debug.error("Error loading file: " + e.getMessage());
            return false;
        }
        cpu.getMemory().writeBytes(0, data);
        return true;
    }
}
