package frames;

import field.Field;
import marble.Marble;
import utils.TchoucTchouc;
import window.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.NumberFormat;

public class Game {

    private static final int width = 200;
    private static final int height = 30;
    private static String title;
    private static boolean canExport = false;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static void main(String _title) {
        title = _title;
        Window w = new Window(_title);
        setupComponents(w.panel);
        setupMenu(w);
        w.start();
    }

    private static void setupComponents(JPanel panel){

        int mid = (int) (screenSize.width/2-(width*1.5));

        JLabel labelBox = new JLabel("Nombre de boîtes");
        labelBox.setBounds(mid, 50, width, height);

        Field fieldBox = new Field(NumberFormat.getIntegerInstance());
        fieldBox.setBounds(mid+150,50, width, height);

        JLabel labelMarbles = new JLabel("Nombre de billes");
        labelMarbles.setBounds(mid, 100, width, height);

        Field fieldMarbles = new Field(NumberFormat.getIntegerInstance());
        fieldMarbles.setBounds(mid+150,100, width, height);

        JLabel labelStrat = new JLabel("Stratégie de placement");
        labelStrat.setBounds(mid, 150, width, height);

        String[] couleurs = {"Chaînage", "Double choix", "Adressage ouvert linéaire", "Adressage ouvert quadratique"};
        JComboBox<String> comboBox = new JComboBox<>(couleurs);
        comboBox.setBounds(mid+150,150,width,height);

        JButton validateBox = new JButton("Valider la saisie");
        validateBox.setBounds(mid+75, 200, width, height);

        validateBox.addActionListener(e -> {
            int k = 0, n = 0;
            try{
                k = Integer.parseInt(fieldBox.getText());
            } catch (NumberFormatException z){
                fieldBox.setText("Veuillez fournir un nombre entier");
            }
            try{
                n = Integer.parseInt(fieldMarbles.getText());
            } catch (NumberFormatException z){
                fieldMarbles.setText("Veuillez fournir un nombre entier");
            }

            if(n != 0 && k != 0){
                if (n < k / 2 || n > k) {
                    fieldMarbles.setText("Veuillez saisir un nombre entre "+k/2+" et "+k);
                } else {
                    String strat = (String) comboBox.getSelectedItem();
                    panel.removeAll();
                    panel.setLayout(new GridLayout(1, k));
                    JPanel[] boxes = new JPanel[k];
                    for (int i = 0; i < k; i++) {
                        boxes[i] = new JPanel();
                        boxes[i].setPreferredSize(new Dimension(screenSize.width / k, screenSize.height));
                        boxes[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        panel.add(boxes[i]);
                    }

                    TchoucTchouc.hub(strat, boxes, n);

                    panel.revalidate();
                    panel.repaint();

                    canExport = true;
                }
            }
        });

        panel.add(labelBox);
        panel.add(fieldBox);
        panel.add(labelMarbles);
        panel.add(fieldMarbles);
        panel.add(validateBox);
        panel.add(labelStrat);
        panel.add(comboBox);
    }

    private static void setupMenu(Window w){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem reboot, stop, export;
        reboot = new JMenuItem("Recommencer");
        reboot.addActionListener(e -> {w.z();reboot();});
        stop = new JMenuItem("Arrêter");
        stop.addActionListener(e -> {w.z();});
        export = new JMenuItem("Exporter au format svg");
        export.addActionListener(e -> {
            try {
                export(w);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        menu.add(reboot);
        menu.add(stop);
        menu.add(export);
        menuBar.add(menu);
        w.setJMenuBar(menuBar);
    }

    private static void reboot(){
        main(title);
    }

    private static void export(Window w) throws IOException {
        if(canExport){
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.svg"));
            double nbCol = w.panel.getComponents().length+1;
            double width = screenSize.width/nbCol;
            double base = width / 2;
            StringBuilder str = new StringBuilder("<svg width=\"" + (screenSize.width-width) + "\" height=\""+screenSize.height+"\" xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\">");
            int j = 0;
            for(Component c : w.panel.getComponents()){
                if(c instanceof JPanel){
                    for(int i = 0; i < ((JPanel) c).getComponents().length; i++){
                        if(((JPanel) c).getComponents()[i] instanceof Marble)
                            str.append(((Marble) ((JPanel) c).getComponents()[i]).toSVG(base + width * j,base + width * i,base));
                    }
                    j++;
                    str.append("<line x1=\"").append(width * j).append("\" y1=\"0\" x2=\"").append(width * j).append("\" y2=\"").append(screenSize.height).append("\" stroke=\"black\" />");
                }
            }
            writer.write(str+"</svg>");
            writer.close();
            JOptionPane.showMessageDialog(w, "Le rendu graphique a bien été exporté !");
        } else {
            JOptionPane.showMessageDialog(w, "Vous ne pouvez pas encore exporter le rendu graphique dans un fichier SVG");
        }
    }
}
