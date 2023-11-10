package ch.epfl.cs107.crypto;

import ch.epfl.cs107.Helper;
import ch.epfl.cs107.utils.Bit;

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
        assert emptyArrayByte(plainText);
        if (plainText.length == 0) {
            return new byte[0];
        }
        byte[] cypherText = new byte[plainText.length];
        for (int i = 0 ; i < cypherText.length; i++) {
            cypherText[i] = (byte)(plainText[i] + key);
        }
        return cypherText;
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
        assert nullArray(plainText) && nullArray(keyword);
        assert emptyArrayByte(keyword);
        if (plainText.length == 0) {
            return new byte[0];
        }

        byte[] ciphertext = new byte[plainText.length];
        for (int i = 0 ; i < ciphertext.length; i++){
            ciphertext[i] = (byte)(plainText[i] + keyword[i % keyword.length]);
        }
        return ciphertext;
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
        assert nullArray(plainText) && nullArray(iv);
        assert emptyArrayByte(iv);
        if (plainText.length == 0) {
            return new byte[0];
        }

        byte[] key = new byte[iv.length];
        System.arraycopy(iv, 0, key, 0, iv.length);

        byte[] cipher = new byte[plainText.length];
        for (int i = 0 ; i < plainText.length; i++) {
            cipher[i] = (byte)(plainText[i] ^ key[i % key.length]);
            key[i % key.length] = cipher[i];
        }
        return cipher;
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
        assert nullArray(plainText);
        if (plainText.length == 0) {
            return new byte[0];
        }

        byte[] cypher = new byte[plainText.length];
        for (int i = 0 ; i < plainText.length; i++) {
            cypher[i] = (byte)(plainText[i] ^ key);
        }
        return (cypher);
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
        assert nullArray(plainText) && nullArray(pad);
        assert pad.length == plainText.length;
        if (plainText.length == 0) {
            return new byte[0];
        }

        byte[] cypher = new byte[plainText.length];
        for (int i = 0 ; i < plainText.length; i++){
            cypher[i] = (byte)(plainText[i] ^ pad[i]);
        }
        return (cypher);
    }

    /**
     * Method to encode a byte array using a one-time pad
     * @param plainText Plain text to encode
     * @param pad Array containing the used pad after the execution
     * @param result Array containing the result after the execution
     */
    public static void oneTimePad(byte[] plainText, byte[] pad, byte[] result) {
        Random rand = new Random();
        assert (plainText != null)&&(pad != null);
        assert plainText.length == pad.length;

        for (int i = 0 ; i < plainText.length ; i++){
            pad[i] = (byte)rand.nextInt(256);
            result[i] = (byte)(plainText[i] ^ pad[i]);
        }
    }

}