package Logic;

import Logic.Actions.MenuAction;

import java.awt.*;
import java.io.IOException;

public class Style {
    public final static Color FG_COLOR = new Color(255, 215, 0);
    public final static Color BG_COLOR = new Color(215, 255, 0, 15);

    public static final Font FancyFont, TitleFont, DefaultFont = new Font("Times New Roman", Font.PLAIN, 0);
    static {
        {
            Font f;
            try {
                f = Font.createFont(Font.TRUETYPE_FONT, Style.class.getResourceAsStream("/FH.otf"));
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
                f = new Font("Times New Roman", Font.PLAIN, 0);
            }
            TitleFont = f;
        }
        {
            Font f;
            try {
                f = Font.createFont(Font.TRUETYPE_FONT, Style.class.getResourceAsStream("/SD.ttf"));
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
                f = new Font("Times New Roman", Font.PLAIN, 0);
            }
            FancyFont = f;
        }
    }

    public static Font deriveFont(Font f, int size){
        return f.deriveFont((float)size);
    }
}
