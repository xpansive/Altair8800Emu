package com.xpansive.altair8800;

public enum Flags {
    SIGN (7),
    ZERO (6),
    AUXILIARY_CARRY (4),
    PARITY (2),
    CARRY (0);
    
    private int bit;
    
    private Flags(int bit) {
        this.bit = bit;
    }
    
    /**
     * Returns the bit of the F (flags) register that corresponds with this flag.
     * 
     * @return The bit of the F (flags) register that corresponds with this flag.<br> 
     * 0 <= bit <= 7.
     */
    public int getBit() {
        return bit;
    }
    
    /**
     * Returns a Flags instance based on the specified bit of the flags register.<br>
     * (Not sure if this will be used)
     * 
     * @param bit The bit that will be used to create a Flags instance.<br>
     * 0 <= bit <= 7.
     * @return The Flags instance that has been created from the specified bit of the flags register.
     */
    public static Flags getFromBit(int bit) {
        Flags f = Flags.ZERO; //Random, but it sounds like the right thing to use
        f.bit = bit;
        return f;
    }
}
