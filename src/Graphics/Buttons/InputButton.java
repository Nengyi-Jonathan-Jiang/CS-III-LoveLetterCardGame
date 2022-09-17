package Graphics.Buttons;

import java.awt.*;

/**
 * A TextButton whose text can be edited using the {@link InputButton#append(char)}, {@link InputButton#append(String)},
 * and {@link InputButton#deleteChar()} methods. There can only be a single line of text
 */
public class InputButton extends TextButton {
    public InputButton(String str){
        super(str);
    }

    public InputButton(Font font, String str){
        super(font, str);
    }
    
    /**
     * Appends a character to the end of the text
     */
    public void append(char c){
        append(c + "");
    }
    
    /**
     * Appends a string to the end of the text
     */
    public void append(String s){
        String text = getText()[0];
        setText(text + s);
    }
    
    /**
     * Deletes a character from the end of the text
     */
    public void deleteChar(){
        String text = getText()[0];
        if(text.length() > 0)
            setText(text.substring(0, text.length() - 1));
    }
}
