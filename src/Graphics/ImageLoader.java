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

        BufferedImage img = null;
        try (InputStream inputStream = ImageLoader.class.getResourceAsStream(filename)) {
            if(inputStream != null) {
                img = ImageIO.read(inputStream);
                images.put(name, img);
            }
        }
        catch(Exception e){
            System.out.println("Could not load file \"" + filename + "\"");
            e.printStackTrace();
        }
    }
}