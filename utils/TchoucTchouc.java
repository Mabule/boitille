package utils;

import marble.Marble;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class TchoucTchouc {

    public static void random(JPanel[] boxes, int n){
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            int box = r.nextInt(boxes.length);
            boxes[box].add(new Marble());
        }

        markMax(boxes);
    }

    public static void doubleChoice(JPanel[] boxes, int n){
        Random r = new Random();

        for (int i = 0; i < n; i++) {
            int boxO = r.nextInt(boxes.length);
            int boxT = r.nextInt(boxes.length);
            if(boxes[boxO].getComponents().length == boxes[boxT].getComponents().length)
                boxes[boxO].add(new Marble());
            else{
                if(boxes[boxO].getComponents().length < boxes[boxT].getComponents().length)
                    boxes[boxO].add(new Marble());
                else
                    boxes[boxT].add(new Marble());
            }
        }

        markMax(boxes);
    }

    public static void openLinearQuadra(JPanel[] boxes, int n, boolean quadra){
        Random r = new Random();

        int index = 0;
        int max_examinated = 0;
        for (int i = 0; i < n; i++) {
            long nbStart = r.nextInt(boxes.length);
            int count = 0;
            long j = 0;
            long quadra_count = 3;
            int current_index;
            do{
                current_index = (int) (nbStart+j)%boxes.length;
                if(quadra && j >= 1) {
                    j = (j + quadra_count)%boxes.length;
                    quadra_count += 2;
                } else
                    j++;
                count++;
            }while(boxes[current_index].getComponents().length > 0);
            boxes[current_index].add(count == 1 ? new Marble(true) : new Marble());
            if(count > max_examinated) {
                index = current_index;
                max_examinated = count;
            }
        }

        JLabel lbl = new JLabel(String.valueOf(max_examinated));
        lbl.setPreferredSize(new Dimension(15, 10));
        boxes[index].add(lbl);
    }

    private static void markMax(JPanel[] boxes){
        int max = 0;
        int index = 0;
        for(int i = 0; i < boxes.length; i++){
            if(boxes[i].getComponents().length > max){
                index = i;
                max = boxes[i].getComponents().length;
            }
        }
        JLabel lbl = new JLabel(String.valueOf(max));
        lbl.setPreferredSize(new Dimension(10, 10));
        boxes[index].add(lbl);
    }

    public static void hub(String choice, JPanel[] boxes, int n){
        switch(Objects.requireNonNull(choice)){
            case "Chaînage" -> random(boxes, n);
            case "Double choix" -> doubleChoice(boxes, n);
            case "Adressage ouvert linéaire" -> openLinearQuadra(boxes, n, false);
            case "Adressage ouvert quadratique" -> openLinearQuadra(boxes, n, true);
        }
    }

}
