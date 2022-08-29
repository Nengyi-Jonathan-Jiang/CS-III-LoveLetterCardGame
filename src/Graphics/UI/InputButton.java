package Graphics.UI;

public class InputButton extends TextButton {
    public InputButton(String str){
        super(str);
    }

    public void append(char c){
        append(c + "");
    }
    public void append(String s){
        String text = getText()[0];
        setText(text + s);
    }
    public void deleteChar(){
        String text = getText()[0];
        if(text.length() > 0)
            setText(text.substring(0, text.length() - 1));
    }
}
