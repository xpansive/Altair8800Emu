package com.xpansive.altair8800;

public class MathHelper {
    public static int to16(int lo, int hi) {
        return hi << 8 | lo;
    }
    
    public static int loByte(int val) {
        return val & 0xFF;
    }
    
    public static int hiByte(int val) {
        return val >> 8;
    }
    
    public static boolean bit(int val, int bit) {
        return (1 << bit & val) > 0 ? true : false;
    }
    
    public static int bitRange16(int val, int start, int end) {
        return val >> start & ~(0xFFFF << end - start);
    }
    
    public static int setBit16(int val, int bit, boolean bool) {
        return bool ? 
                1 << bit | val :
                (0xFFFF ^ 1 << bit) & val;
    }
    
    public static int setHiByte(int val, int hi) {
    	return to16(loByte(val), hi);
    }
    
    public static int setLoByte(int val, int lo) {
    	return to16(lo, hiByte(val));
    }
    
    public static boolean bitParity(int val) {
        val ^= val >> 1;
        val ^= val >> 2;
        val = (val & 0x11111111) * 0x11111111;
        int parity = (val >> 28) & 1;
        return parity == 0 ? true : false;
    }
}
