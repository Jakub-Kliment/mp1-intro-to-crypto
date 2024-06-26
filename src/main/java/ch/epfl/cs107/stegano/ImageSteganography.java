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
 * <b>Task 3.1: </b>Utility class to perform Image Steganography
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ImageSteganography {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private ImageSteganography(){}

    // ============================================================================================
    // ================================== EMBEDDING METHODS =======================================
    // ============================================================================================

    /**
     * Embed an ARGB image on another ARGB image (the cover)
     * @param cover Cover image
     * @param argbImage Embedded image
     * @param threshold threshold to use for binary conversion
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedARGB(int[][] cover, int[][] argbImage, int threshold) {
        assert nullImage(cover) && nullImage(argbImage);
        assert validThreshold(threshold);
        assert compareSize(cover, argbImage);
        if (cover.length == 0) {
            return new int[0][0];
        }
        return embedBW(cover, toBinary(toGray(argbImage), threshold));
    }

    /**
     * Embed a Gray scaled image on another ARGB image (the cover)
     * @param cover Cover image
     * @param grayImage Embedded image
     * @param threshold threshold to use for binary conversion
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedGray(int[][] cover, int[][] grayImage, int threshold){
        assert nullImage(cover) && nullImage(grayImage);
        assert nullRow(cover) && nullRow(grayImage);
        assert validThreshold(threshold);
        assert compareSize(cover, grayImage);
        if (cover.length == 0) {
            return new int[0][0];
        }
        return embedBW(cover, toBinary(grayImage, threshold));
    }

    /**
     * Embed a binary image on another ARGB image (the cover)
     * @param cover Cover image
     * @param load Embedded image
     * @return ARGB image with the image embedded on the cover
     */
    public static int[][] embedBW(int[][] cover, boolean[][] load){
        assert nullImage(cover) && nullImage(load);
        assert nullRow(cover) && nullRow(load);
        assert shapeCompatibility(cover) && shapeCompatibility(load);
        assert emptyImage(cover) && emptyImage(load);
        assert compareSize(cover, load);
        assert cover[0].length >= load[0].length;

        int[][] newImage = new int[cover.length][cover[0].length];
        int index = 0;
        for (int i = 0; i < load.length; ++i) {
            for (int j = 0; j < load[i].length; ++j) {
                newImage[i][j] = embedInLSB(cover[i][j], load[i][j]);
            }
            index = i;
        }
        for (int i = index + 1; i < cover.length; ++i) {
            System.arraycopy(cover[i], 0, newImage[i], 0, cover[i].length);
        }
        return newImage;
    }

    // ============================================================================================
    // =================================== REVEALING METHODS ======================================
    // ============================================================================================

    /**
     * Reveal a binary image from a given image
     * @param image Image to reveal from
     * @return binary representation of the hidden image
     */
    public static boolean[][] revealBW(int[][] image) {
        assert nullImage(image);
        assert nullRow(image);
        assert shapeCompatibility(image);
        assert emptyImage(image);

        boolean[][] imageLSB = new boolean[image.length][image[0].length];
        for (int i = 0; i < image.length; ++i) {
            for (int j = 0; j < image[0].length; ++j) {
                imageLSB[i][j] = getLSB(image[i][j]);
            }
        }
        return imageLSB;
    }

}
