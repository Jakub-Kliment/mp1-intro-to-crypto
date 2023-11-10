package ch.epfl.cs107.utils;

import ch.epfl.cs107.Helper;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 1.1: </b>Utility class to manipulate bits
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Bit {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Bit(){}

    // ============================================================================================
    // ==================================== BIT MANIPULATION ======================================
    // ============================================================================================

    /**
     * Embed a bit in a given integer bits
     * <p>
     * @param value value to embed in
     * @param m <code>true</code> to embed 1, <code>false</code> to embed 0
     * @param pos position of the bit to change
     * @return embedded value
     */
    public static int embedInXthBit(int value, boolean m, int pos) {
        assert (pos>=0)&&(pos<32) : "Bit position need to be under 32";
        double compare = 0.0;
        if (m) {
            if (pos == 31) {
                return value | Integer.MIN_VALUE;
            } else {
                compare = Math.pow(2, pos);
                return value | (int) compare;
            }
        } else {
            if (pos == 31) {
                return value & Integer.MAX_VALUE;
            } else {
                compare = ~((int) Math.pow(2, pos));
                return value & (int) compare;
            }
        }
    }

    /**
     * Embed a bit in the "least significant bit" (LSB)
     * <p>
     * @param value value to embed in
     * @param m <code>true</code> to embed 1, <code>false</code> to embed 0
     * @return embedded value
     */
    public static int embedInLSB(int value, boolean m){
        if (m&&!getLSB(value)||(!m&&getLSB(value))){
            if (m){
                value+=1;
            } else{
                value-=1;
            }
        }
        return value;
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Extract a bit in a given position from a given value
     * <p>
     * @param value value to extract from
     * @param pos position of the bit to extract
     * @return <code>true</code> if the bit is '1' and <code>false</code> otherwise
     */
    public static boolean getXthBit(int value, int pos) {
        assert (pos >= 0) && (pos < 32) : "Bit position need to be under 32";
        int solution = 0;
        if (pos == 31) {
            solution = value & Integer.MIN_VALUE;
        } else {
            double compare = Math.pow(2, pos);
            solution = value & (int) compare;
        }
        return solution != 0;
    }

    /**
     * Extract the 'least significant bit' from a given value
     * <p>
     * @param value value to extract from
     * @return <code>true</code> if the bit is '1' and <code>false</code> otherwise
     */
    public static boolean getLSB(int value) {
        return value%2!=0;
        //return Helper.fail("NOT IMPLEMENTED");
    }

    // ============================================================================================
    // ==================================== BYTE MANIPULATION =====================================
    // ============================================================================================

    /**
     * Convert a byte value to the bit array representation.
     * <p>
     * Suppose we have the following input <b><code>0b00_11_01_10</code></b>. We can expect this function
     * to return the following array :
     * <b>[<code>false</code>,
     *     <code>false</code>,
     *     <code>true</code>,
     *     <code>true</code>,
     *     <code>false</code>,
     *     <code>true</code>,
     *     <code>true</code>,
     *     <code>false</code>]</b>
     * @param value byte representation of the value
     * @return bit array representation of the value
     */
    public static boolean[] toBitArray(byte value){
        boolean[] bitArray = new boolean[8];
        for(int i = 0 ; i<8 ; i++){
            bitArray[7-i]=getXthBit(value,i);
        }
        return bitArray;
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Convert a boolean array to a byte
     * <p>
     * Suppose we have the following input :
     * <b>[<code>false</code>,
     *     <code>false</code>,
     *     <code>true</code>,
     *     <code>true</code>,
     *     <code>false</code>,
     *     <code>true</code>,
     *     <code>true</code>,
     *     <code>false</code>]</b>
     * We can expect this function to return the following byte : <b><code>0b00_11_01_10</code></b>.
     * @param bitArray bit array representation to convert
     * @return the byte representation of the bit array
     */
    public static byte toByte(boolean[] bitArray) {
        assert bitArray != null : "Tableau vide.";
        assert bitArray.length == 8 : "Tableau doit contenir 8 boolean.";
        byte value = 0;
        int length = bitArray.length;
        for (int i = 0; i < length; ++i) {
            if (bitArray[length - i - 1]) {
                byte tmp = (byte)Math.pow(2,i);
                value += tmp;
            }
        }
        return value;
    }

    public static boolean emptyArrayByte(byte[] array) {
        for (byte by : array){
            if (by!=0){
                return true;
            }
        }
        return false;
    }
}
