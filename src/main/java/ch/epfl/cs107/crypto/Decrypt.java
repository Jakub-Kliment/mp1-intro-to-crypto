package ch.epfl.cs107.crypto;

import ch.epfl.cs107.Helper;
import ch.epfl.cs107.utils.Bit;
import static ch.epfl.cs107.utils.Text.*;
import static ch.epfl.cs107.utils.Image.*;
import static ch.epfl.cs107.utils.Bit.*;
import static ch.epfl.cs107.stegano.ImageSteganography.*;
import static ch.epfl.cs107.stegano.TextSteganography.*;
import static ch.epfl.cs107.crypto.Encrypt.*;
import static ch.epfl.cs107.crypto.Decrypt.*;
import static ch.epfl.cs107.Main.*;

/**
 * <b>Task 2: </b>Utility class to decrypt a given cipher text.
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Decrypt {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Decrypt(){}

    // ============================================================================================
    // ================================== CAESAR'S ENCRYPTION =====================================
    // ============================================================================================

    /**
     * Method to decode a byte array message using a single character key
     * <p>
     * @param cipher Cipher message to decode
     * @param key Key to decode with
     * @return decoded message
     */
    public static byte[] caesar(byte[] cipher, byte key) {
        assert nullArray(cipher);
        byte[] plainText = new byte[cipher.length];
        for(int i = 0 ; i < plainText.length; i++) {
            plainText[i] = (byte)(cipher[i] - key);
        }
        return plainText;
    }

    // ============================================================================================
    // =============================== VIGENERE'S ENCRYPTION ======================================
    // ============================================================================================

    /**
     * Method to encode a byte array using a byte array keyword
     * @param cipher Cipher message to decode
     * @param keyword Key to decode with
     * @return decoded message
     */
    public static byte[] vigenere(byte[] cipher, byte[] keyword) {
        assert nullArray(cipher) && nullArray(keyword);
        assert emptyArrayByte(keyword);

        byte[] plainText = new byte[cipher.length];
        for (int i = 0 ; i < plainText.length; i++){
            plainText[i] = (byte)(cipher[i] - keyword[i % keyword.length]);
        }
        return plainText;
    }

    // ============================================================================================
    // =================================== CBC'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method to decode cbc-encrypted ciphers
     * @param cipher message to decode
     * @param iv the pad of size BLOCKSIZE we use to start the chain encoding
     * @return decoded message
     */
    public static byte[] cbc(byte[] cipher, byte[] iv) {
        assert nullArray(cipher) && nullArray(iv);
        assert iv.length != 0;

        if (cipher.length == 0) {
            return new byte[0];
        }
        byte[] key = new byte[cipher.length];
        System.arraycopy(iv, 0, key, 0, iv.length);
        System.arraycopy(cipher, 0, key, iv.length, key.length - iv.length);

        byte[] plainText = new byte[cipher.length];
        for (int i = 0 ; i < cipher.length; i++) {
            plainText[i] = (byte)(cipher[i] ^ key[i]);
        }
        return plainText;
    }

    // ============================================================================================
    // =================================== XOR'S ENCRYPTION =======================================
    // ============================================================================================

    /**
     * Method to decode xor-encrypted ciphers
     * @param cipher text to decode
     * @param key the byte we will use to XOR
     * @return decoded message
     */
    public static byte[] xor(byte[] cipher, byte key) {
        return Encrypt.xor(cipher, key);
    }

    // ============================================================================================
    // =================================== ONETIME'S PAD ENCRYPTION ===============================
    // ============================================================================================

    /**
     * Method to decode otp-encrypted ciphers
     * @param cipher text to decode
     * @param pad the one-time pad to use
     * @return decoded message
     */
    public static byte[] oneTimePad(byte[] cipher, byte[] pad) {
        return Encrypt.oneTimePad(cipher, pad);
    }

}
