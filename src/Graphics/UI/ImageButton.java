package Graphics.UI;

import Graphics.GameCanvas;
import Graphics.ImageLoader;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public class ImageButton extends Button {
    BufferedImage image;
    public ImageButton(String imgName){
        image = ImageLoader.get(imgName);
    }
    @Override
    public void draw(@NotNull GameCanvas canvas) {
        canvas.graphics.drawImage(image, x, y, width, height, null);
    }
}
