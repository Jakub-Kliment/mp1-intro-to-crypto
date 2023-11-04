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
 * <b>Task 1.3: </b>Utility class to manipulate ARGB images
 *
 * @author Hamza REMMAL (hamza.remmal@epfl.ch)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Image {

    // DO NOT CHANGE THIS, MORE ON THAT ON WEEK 7
    private Image(){}

    // ============================================================================================
    // ==================================== PIXEL MANIPULATION ====================================
    // ============================================================================================

    /**
     * Build a given pixel value from its respective components
     *
     * @param alpha alpha component of the pixel
     * @param red red component of the pixel
     * @param green green component of the pixel
     * @param blue blue component of the pixel
     * @return packed value of the pixel
     */
    public static int argb(byte alpha, byte red, byte green, byte blue){
        int color = 0;
        color += Byte.toUnsignedInt(alpha);
        color = color << 8;
        color += Byte.toUnsignedInt(red);
        color = color << 8;
        color += Byte.toUnsignedInt(green);
        color = color << 8;
        color += Byte.toUnsignedInt(blue);
        return color;
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Extract the alpha component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the alpha component of the pixel
     */
    public static byte alpha(int pixel){
        return (byte) (pixel >> 24);
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Extract the red component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the red component of the pixel
     */
    public static byte red(int pixel){
        return (byte) ((pixel >> 16)&(0b11111111));
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Extract the green component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the green component of the pixel
     */
    public static byte green(int pixel){
        return (byte) ((pixel >> 8)&(0b11111111));
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Extract the blue component of a given pixel
     *
     * @param pixel packed value of the pixel
     * @return the blue component of the pixel
     */
    public static byte blue(int pixel){
        return (byte) ((pixel)&(0b11111111));
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Compute the gray scale of the given pixel
     *
     * @param pixel packed value of the pixel
     * @return gray scaling of the given pixel
     */
    public static int gray(int pixel){
        int redUnsigned = Byte.toUnsignedInt(red(pixel));
        int greenUnsigned = Byte.toUnsignedInt(green(pixel));
        int blueUnsigned = Byte.toUnsignedInt(blue(pixel));
        return ((redUnsigned+greenUnsigned+blueUnsigned)/3);
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Compute the binary representation of a given pixel.
     *
     * @param gray gray scale value of the given pixel
     * @param threshold when to consider a pixel white
     * @return binary representation of a pixel
     */
    public static boolean binary(int gray, int threshold){
        assert gray <= 255 && gray >= 0 : "Gray value not valid";
        return gray >= threshold;
        //return Helper.fail("NOT IMPLEMENTED");
    }

    // ============================================================================================
    // =================================== IMAGE MANIPULATION =====================================
    // ============================================================================================

    /**
     * Build the gray scale version of an ARGB image
     *
     * @param image image in ARGB format
     * @return the gray scale version of the image
     */
    public static int[][] toGray(int[][] image){
        assert image != null : "Image null.";
        if (image.length == 0) {
            return new int[0][0];
        }
        int[][] gray = new int[image.length][image[0].length];
        for (int i = 0; i < image.length; ++i) {
            assert image[i] != null : "Image representation contains null.";
            for (int j = 0; j < image[i].length; ++j) {
                gray[i][j] = gray(image[i][j]);
            }
        }
        return gray;
    }

    /**
     * Build the binary representation of an image from the gray scale version
     *
     * @param image Image in gray scale representation
     * @param threshold Threshold to consider
     * @return binary representation of the image
     */
    public static boolean[][] toBinary(int[][] image, int threshold){
        assert image != null : "Image null";
        if (image.length == 0) {
            return new boolean[0][0];
        }
        boolean[][] grayToBinary = new boolean[image.length][image[0].length];
        for (int i = 0; i < image.length; ++i) {
            assert image[i] != null : "Image representation contains null.";
            for (int j = 0; j < image[i].length; ++j) {
                grayToBinary[i][j] = binary(image[i][j], threshold);
            }
        }
        return grayToBinary;
    }

    /**
     * Build an ARGB image from the gray-scaled image
     * @implNote The result of this method will a gray image, not the original image
     * @param image grayscale image representation
     * @return <b>gray ARGB</b> representation
     */
    public static int[][] fromGray(int[][] image){
        assert image != null : "Image null";
        if (image.length == 0) {
            return new int[0][0];
        }
        int[][] imageARGB = new int[image.length][image[0].length];
        for(int i = 0 ; i < image.length ; i++){
            assert image[i] != null : "Image representation contains null.";
            for(int j = 0 ; j<image[i].length ; j++){
                imageARGB[i][j] = argb((byte) 255,(byte) image[i][j],(byte) image[i][j],(byte) image[i][j]);
            }
        }
        return imageARGB;
        //return Helper.fail("NOT IMPLEMENTED");
    }

    /**
     * Build an ARGB image from the binary image
     * @implNote The result of this method will a black and white image, not the original image
     * @param image binary image representation
     * @return <b>black and white ARGB</b> representation
     */
    public static int[][] fromBinary(boolean[][] image){
        assert image != null : "Image null";
        if (image.length == 0) {
            return new int[0][0];
        }
        int[][] imageGrey = new int[image.length][image[0].length];
        for (int i = 0 ; i < image.length ; i++){
            assert image[i] != null : "Image representation contains null.";
            for (int j = 0 ; j < image[i].length ; j++){
                if (image[i][j]){
                    imageGrey[i][j]=0xFFFFFFFF;
                } else {
                    imageGrey[i][j]=0xFF000000;
                }
            }
        }
        return imageGrey;
        //return Helper.fail("NOT IMPLEMENTED");
    }

}
