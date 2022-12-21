package marble;

import javax.swing.*;
import java.awt.*;

public class Marble extends JPanel {

    private boolean toChange;

    public Marble() {
        setPreferredSize(new Dimension(10, 10));
    }

    public Marble(boolean _toChange) {
        toChange = _toChange;
        setPreferredSize(new Dimension(10, 10));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        if(toChange)
            g.setColor(Color.GREEN);
        g.fillOval(0, 0, getWidth(), getHeight());
    }

    public String toSVG(double x, double y, double z){
        return "<circle cx=\""+x+"\" cy=\""+y+"\" r=\""+z+"\" fill=\""+(toChange ? "green" : "blue")+"\" />\n";
    }
}
