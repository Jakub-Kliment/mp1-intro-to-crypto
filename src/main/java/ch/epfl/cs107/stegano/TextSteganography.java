package ch.epfl.cs107.stegano;

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
 * <b>Task 3.2: </b>Utility class to perform Text Steganography
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public class TextSteganography {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private TextSteganography(){}

    // ============================================================================================
    // =================================== EMBEDDING BIT ARRAY ====================================
    // ============================================================================================

    /**
     * Embed a bitmap message in an ARGB image
     * @param cover Cover image
     * @param message Embedded message
     * @return ARGB image with the message embedded
     */
    public static int[][] embedBitArray(int[][] cover, boolean[] message) {
        assert nullImage(cover);
        assert nullRow(cover);
        assert message != null;
        if (cover.length == 0) {
            return new int[0][0];
        }

        int [][] newImage = new int[cover.length][cover[0].length];
        int index = 0;
        for (int i = 0; i < cover.length; ++i) {
            for (int j = 0; j < cover[i].length; ++j) {
                newImage[i][j] = cover[i][j];
                if (index < message.length) {
                    newImage[i][j] = embedInLSB(newImage[i][j], message[index]);
                }
                index++;
            }
        }
        return newImage;
    }

    /**
     * Extract a bitmap from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static boolean[] revealBitArray(int[][] image) {
        assert nullImage(image);
        assert nullRow(image);
        assert shapeCompatibility(image);
        if (image.length == 0) {
            return new boolean[0];
        }

        boolean[] boolLSB = boolLSB(image);
        int index = 0;
        for (int[] column : image) {
            for (int row : column) {
                boolLSB[index] = getLSB(row);
                index++;
            }
        }
        return boolLSB;
    }



    // ============================================================================================
    // ===================================== EMBEDDING STRING =====================================
    // ============================================================================================

    /**
     * Embed a String message in an ARGB image
     * @param cover Cover image
     * @param message Embedded message
     * @return ARGB image with the message embedded
     */
    public static int[][] embedText(int[][] cover, byte[] message) {
        assert nullImage(cover) && message != null;
        assert nullRow(cover);
        assert shapeCompatibility(cover);
        if (cover.length == 0) {
            return new int[0][0];
        }

        boolean[][] boolMessage = new boolean[message.length][8];
        boolean[] newLSB = new boolean[message.length * 8];
        for (int i = 0; i < message.length; ++i) {
            boolMessage[i] = toBitArray(message[i]);
        }

        int index = 0;
        for (boolean[] booleans : boolMessage) {
            for (int j = 0; j < 8; ++j) {
                newLSB[index] = booleans[j];
                index++;
            }
        }
        return embedBitArray(cover, newLSB);
    }
    /**
     * Extract a String from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static byte[] revealText(int[][] image) {
        assert nullImage(image);
        assert nullRow(image);
        assert emptyImage(image);
        assert shapeCompatibility(image);
        if (image.length == 0) {
            return new byte[0];
        }

        boolean[] imageLSB = boolLSB(image);
        byte[] message = new byte[imageLSB.length / 8];
        for (int i = 0; i < imageLSB.length / 8; i += 8) {
            boolean[] oneByte = new boolean[8];
            System.arraycopy(imageLSB, i, oneByte, 0, 8);
            message[i] = toByte(oneByte);
        }
        return message;
    }
    public static boolean[] boolLSB(int[][] image) {
        assert nullImage(image);
        assert nullRow(image);
        boolean[] boolLSB = new boolean[image.length * image[0].length];
        int index = 0;
        for (int[] column : image) {
            for (int row : column) {
                boolLSB[index] = getLSB(row);
                index++;
            }
        }
        return boolLSB;
    }
}
