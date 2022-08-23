import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    public App(){
        super();
        setTitle("L Letter Cards");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 800));
        setResizable(true);

        add(new Deck());

        setVisible(true);
    }
    public static void main(String[] args){
        App app = new App();
    }
}