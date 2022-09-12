package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ImageLoader {
    private final static Map<String, BufferedImage> images = new TreeMap<>();
    public static BufferedImage get(String s){
        return images.get(s);
    }
    public static void add(String filename, String name){
        if(images.containsKey(name)) return;

        images.put(name, load(filename));
    }
    public static BufferedImage load(String filename){
        try (InputStream inputStream = ImageLoader.class.getResourceAsStream("../" + filename)) {
            if(inputStream != null) {
                return ImageIO.read(inputStream);
            }
            else{
                System.out.println(Stream.of(new File("/").listFiles())
                    .filter(file -> !file.isDirectory())
                    .map(File::getName).collect(Collectors.toList()));
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