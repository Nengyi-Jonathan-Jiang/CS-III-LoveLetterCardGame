package Graphics.Buttons;

import Graphics.GameCanvas;
import Graphics.ImageLoader;

import java.awt.image.BufferedImage;

/**
 * A Button that displays an image
 */
public class ImageButton extends Button {
    BufferedImage image;
    public ImageButton(String imgName){
        image = ImageLoader.get(imgName);
    }
    
    @Override
    public void draw(GameCanvas canvas) {
        canvas.graphics.drawImage(image, x, y, width, height, null);
    }
}
