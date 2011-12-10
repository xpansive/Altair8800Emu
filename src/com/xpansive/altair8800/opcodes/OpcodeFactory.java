package com.xpansive.altair8800.opcodes;

import java.util.HashMap;

public class OpcodeFactory {
    
    private static HashMap<Integer, Opcode> opcodeMap = new HashMap<Integer, Opcode>();
    private static Opcode[] opcodes = new Opcode[] {
        new NOP(), new JMP(), new LDA(), new STA(), new MOV(), new ADD(), new ADI()
    };
    
    static {
        //Populate the opcode map
        for(Opcode opcode : opcodes) {
            for(int byteCode : opcode.getByteCodes()) {
                opcodeMap.put(byteCode, opcode);
            }
        }
    }
    
    public static Opcode fromByteCode(int opcode) {
        return opcodeMap.get(opcode);
    }
}
