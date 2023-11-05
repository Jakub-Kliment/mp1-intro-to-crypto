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
        assert cover != null : "Image null";
        if (cover.length == 0) { return new int[0][0]; }
        int [][] newImage = new int[cover.length][cover[0].length];
        int index = 0;
        for (int i = 0; i < cover.length; ++i) {
            assert cover[i] != null : "Image containing null";
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
        assert image != null : "Image null";
        if (image.length == 0) { return new boolean[0]; }
        boolean[] boolLSB = new boolean[image.length * image[0].length];
        int index = 0;
        for (int[] column : image) {
            assert column != null : "Image containing null";
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
        return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Extract a String from an image
     * @param image Image to extract from
     * @return extracted message
     */
    public static byte[] revealText(int[][] image) {
        return Helper.fail("NOT IMPLEMENTED");
    }

}
