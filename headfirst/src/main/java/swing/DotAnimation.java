package swing;

import javax.swing.*;
import java.awt.*;

public class DotAnimation {

    private int x = 70;
    private int y = 70;

    public static void main(String[] args) {
        DotAnimation animation = new DotAnimation();
        animation.go();
    }

    private void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MyDrawPanel drawPanel = new MyDrawPanel();

        frame.getContentPane().add(drawPanel);
        frame.setSize(300,300);
        frame.setVisible(true);

        for (int i =0; i < 200; i++) {
            x++;
            y++;

            drawPanel.repaint();

            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class MyDrawPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            g.setColor(Color.white);
            g.fillRect(0,0,this.getWidth(), this.getHeight());
            g.setColor(Color.green);
            g.fillOval(x,y,40,40);
        }
    }

}