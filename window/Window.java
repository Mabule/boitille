package window;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class Window extends JFrame{

    public JPanel panel = new JPanel(null);

    public Window(String title){
        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width-200, screenSize.height-200);
        setLocationRelativeTo(null);
    }

    public void start(){
        setVisible(true);
        setContentPane(panel);
    }

    public void z() {
        new Thread(this::dispose);
    }
}
