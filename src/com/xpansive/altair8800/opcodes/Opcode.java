package com.xpansive.altair8800.opcodes;

import java.util.EnumSet;

import com.xpansive.altair8800.*;

public abstract class Opcode {
    /**
     * Gets the byte code(s) which will represent the opcode.
     * 
     * @return An array of the possible representations.<br>
     *         Each value will be within 0 <= value <= 255.
     */
    public abstract int[] getByteCodes();

    /**
     * Executes the opcode on the given CPU instance.
     * 
     * @param cpu
     *            The CPU to execute the opcode on.
     * @param arg0
     *            The first argument to the opcode.
     * @param arg1
     *            The second argument to the opcode.
     * @throws Exception
     *             if not implemented.
     */
    public abstract void execute(CPU cpu, int arg0, int arg1, int opcode);

    /**
     * Gets a list of all the processor flags which are affected by this opcode.
     * 
     * @return The list of flags.
     */
    public EnumSet<Flags> getAffectedFlags(int opcode) {
        return EnumSet.noneOf(Flags.class);
    }

    /**
     * Gets the length, in bytes, of this opcode.
     * 
     * @return The length, in bytes where length > 0.
     */
    public abstract int getLength();

    /**
     * Gets the number of CPU cycles required to process this opcode.
     * 
     * @return The number of CPU cycles where cycles > 0.
     */
    public abstract int getCycles(int opcode);

    /**
     * Gets a list of all the registers which are affected by this opcode.
     * 
     * @return The list of registers.
     */
    public EnumSet<Register> getAffectedRegisters(int opcode) {
        return EnumSet.noneOf(Register.class);
    }

    /**
     * Gets the name of this opcode.
     * 
     * @return The name.
     */
    public abstract String getName(int opcode);
    
    public static int setFlags(int val, CPU cpu, EnumSet<Flags> flagsToSet) {
        int flags = cpu.getFlags();
        
        if (flagsToSet.contains(Flags.CARRY))
            if (val > 255) {
                val %= 256;
                flags = MathHelper.setBit16(flags, Flags.CARRY.getBit(), true);
            }
        if (flagsToSet.contains(Flags.ZERO))
            flags = MathHelper.setBit16(flags, Flags.ZERO.getBit(), val == 0);
        if (flagsToSet.contains(Flags.SIGN))
        flags = MathHelper.setBit16(flags, Flags.SIGN.getBit(), MathHelper.bit(val, 7));
        if (flagsToSet.contains(Flags.PARITY))
        flags = MathHelper.setBit16(flags, Flags.PARITY.getBit(), MathHelper.bitParity(val));
        
        //TODO: Auxiliary carry
        
        cpu.setFlags(flags);
        return val;
    }
}
