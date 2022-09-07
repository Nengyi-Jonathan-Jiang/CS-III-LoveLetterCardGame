package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public final class ImageLoader {
    private final static Map<String, BufferedImage> images = new TreeMap<>();
    public static BufferedImage get(String s){
        return images.get(s);
    }
    public static void add(String filename, String name){
        if(images.containsKey(name)) return;

        try (InputStream inputStream = ImageLoader.class.getResourceAsStream(filename)) {
            if(inputStream != null) {
                images.put(name, ImageIO.read(inputStream));
            }
        }
        catch(Exception e){
            System.out.println("Could not load file \"" + filename + "\"");
            e.printStackTrace();
        }
    }
    public static BufferedImage load(String filename){
        try (InputStream inputStream = ImageLoader.class.getResourceAsStream(filename)) {
            if(inputStream != null) {
                return ImageIO.read(inputStream);
            }
        }
        catch(Exception e){
            System.out.println("Could not load file \"" + filename + "\"");
            e.printStackTrace();
        }
        return null;
    }
}