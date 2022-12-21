package field;

import javax.swing.*;
import java.text.NumberFormat;

public class Field extends JFormattedTextField {

    public Field(NumberFormat format) {
        super(format);
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }
}
