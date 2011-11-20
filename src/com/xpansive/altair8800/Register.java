package com.xpansive.altair8800;

public enum Register {
    //8bit
    A (8),
    F (8),
    B (8),
    C (8), 
    D (8),
    E (8),
    H (8),
    L (8),
    
    
    //16bit
    PSW(16, Register.A, Register.F),
    BC (16, Register.B, Register.C),
    DE (16, Register.D, Register.E),
    HL (16, Register.H, Register.L),
    SP (16);
    
    private int width;
    private Register lo;
    private Register hi;
    
    private Register(int width) {
    	this.width = width;
    }
    
    private Register(int width, Register lo, Register hi) {
    	this.width = width;
    	this.lo = lo;
    	this.hi = hi;
    }
    
    /**
     * Gets the width, in bits, of this register
     * @return The width of the register
     */
    public int getWidth() {
    	return width;
    }
    
    public Register getLowByte() {
    	return lo;
    }
    
    public Register getHighByte() {
    	return hi;
    }
}
