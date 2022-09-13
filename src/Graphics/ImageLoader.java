package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Utility class to load images from files
 */
public final class ImageLoader {
    private final static Map<String, BufferedImage> images = new TreeMap<>();
    
    private ImageLoader(){}
    
    /**
     * Retrieves an image that was already loaded into the class
     * @param s The name of the image
     * @return The image (can be null if it does not exist)
     */
    public static BufferedImage get(String s){
        return images.get(s);
    }
    
    /**
     * Loads a file into the class
     * @param filename The location of the image file
     * @param name The name of the image (can be different from the file name, this is the string used to retrieve
     *             images using the {@link ImageLoader#get} method
     */
    public static void add(String filename, String name){
        if(images.containsKey(name)) return;

        images.put(name, load(filename));
    }
    
    /**
     * Reads an image file and returns the image directly without loading it into the class
     * @param filename The location of the image file
     * @return The image (can be null if it does not exist)
     */
    public static BufferedImage load(String filename){
        try (InputStream inputStream = ImageLoader.class.getResourceAsStream("/" + filename)) {
            if(inputStream != null) {
                return ImageIO.read(inputStream);
            }
            else{
                throw new IOException("Null input stream");
            }
        }
        catch(Exception e){
            System.out.println("Could not load file \"" + filename + "\"");
            e.printStackTrace();
        }
        return null;
    }
}