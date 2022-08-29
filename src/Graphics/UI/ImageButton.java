package Graphics.UI;

import Graphics.GameCanvas;
import Graphics.ImageLoader;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class ImageButton extends Button {
    BufferedImage image;
    public ImageButton(String fileName){
        image = ImageLoader.load(fileName);
    }
    @Override
    public void draw(@NotNull GameCanvas canvas) {

    }
}
