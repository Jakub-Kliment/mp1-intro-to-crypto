package ch.epfl.cs107.crypto;

import ch.epfl.cs107.Helper;

import java.util.Random;

import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 2: </b>Utility class to encrypt a given plain text.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Encrypt {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Encrypt(){}

    // ============================================================================================
    // ================================== CAESAR'S ENCRYPTION =====================================
    // ============================================================================================

    /**
     * Method to encode a byte array message using a single character key
     * the key is simply added to each byte of the original message
     *
     * @param plainText The byte array representing the string to encode
     * @param key the byte corresponding to the char we use to shift
     * @return an encoded byte array
     */
    public static byte[] caesar(byte[] plainText, byte key) {
        assert plainText != null : "The next is empty";
        byte[] cypherText = new byte[plainText.length];
        for (int i = 0 ; i< cypherText.length ; i++){
            cypherText[i] = (byte) (plainText[i]+key);
        }
        return cypherText;
        //return Helper.fail("NOT IMPLEMENTED");
    }

    // ============================================================================================
    // =============================== VIGENERE'S ENCRYPTION ======================================
    // ============================================================================================

    /**
     * Method to encode a byte array using a byte array keyword
     * The keyword is repeated along the message to encode
     * The bytes of the keyword are added to those of the message to encode
     * @param plainText the byte array representing the message to encode
     * @param keyword the byte array representing the key used to perform the shift
     * @return an encoded byte array
     */
    public static byte[] vigenere(byte[] plainText, byte[] keyword) {
        assert keyword != null : "The keyword is empty";
        assert plainText != null : "The text is empty";
        byte[] cyphertext = new byte[plainText.length];
        for (int i = 0 ; i< cyphertext.length ; i++){
            cyphertext[i] = (byte) (plainText[i]+keyword[i]);
        }
        return cyphertext;
        //return Helper.fail("NOT IMPLEMENTED");
    }

    // ============================================================================================
    // =================================== CBC'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method applying a basic chain block counter of XOR without encryption method.
     * @param plainText the byte array representing the string to encode
     * @param iv the pad of size BLOCKSIZE we use to start the chain encoding
     * @return an encoded byte array
     */
    public static byte[] cbc(byte[] plainText, byte[] iv) {
        return Helper.fail("NOT IMPLEMENTED");
    }

    // ============================================================================================
    // =================================== XOR'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method to encode a byte array using a XOR with a single byte long key
     * @param plainText the byte array representing the string to encode
     * @param key the byte we will use to XOR
     * @return an encoded byte array
     */
    public static byte[] xor(byte[] plainText, byte key) {
        assert plainText != null : "The text is empty";
        byte[] cypher = new byte[plainText.length];
        for (int i = 0 ; i<plainText.length ; i++){
            cypher[i] = (byte) (plainText[i]^key);
        }
        return (cypher);
        //return Helper.fail("NOT IMPLEMENTED");
    }

    // ============================================================================================
    // =================================== ONETIME'S PAD ENCRYPTION ===============================
    // ============================================================================================

    /**
     * Method to encode a byte array using a one-time pad of the same length.
     *  The method XOR them together.
     * @param plainText the byte array representing the string to encode
     * @param pad the one-time pad
     * @return an encoded byte array
     */
    public static byte[] oneTimePad(byte[] plainText, byte[] pad) {
        assert plainText != null : "The text is empty";
        assert pad.length == plainText.length : "The text and the mask need to be the same size";
        byte[] cypher = new byte[plainText.length];
        for (int i = 0 ; i<plainText.length ; i++){
            cypher[i] = (byte) (plainText[i]^pad[i]);
        }
        return (cypher);
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Method to encode a byte array using a one-time pad
     * @param plainText Plain text to encode
     * @param pad Array containing the used pad after the execution
     * @param result Array containing the result after the execution
     */
    public static void oneTimePad(byte[] plainText, byte[] pad, byte[] result) {
        Random r = new Random();
        assert plainText != null : "The text is empty";
        assert plainText.length == result.length;
        assert pad.length == result.length;
        for (int i = 0 ; i<plainText.length ; i++){
            pad[i] = (byte) r.nextInt(256);
            result[i] = (byte) (plainText[i]^pad[i]);
        }
        //return Helper.fail("NOT IMPLEMENTED");
    }

}