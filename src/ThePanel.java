
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ThePanel extends JPanel implements MouseListener, KeyListener {


    public static void main(String[] args) {

        JFrame window = new JFrame("Fractals");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, 900, 900); //(x, y, w, h)

        ThePanel panel = new ThePanel();
        window.add(panel);

        window.setVisible(true);
        panel.setFocusable(true);
        panel.requestFocusInWindow();

    }

    //range of numbers: a -2 -> 1.5
    // b -2i -> 2i
    //x multiply by (complex range)/(width in pixels) then subtract 2


    private double minA, maxA, minB, maxB;
    private double x, y, r;


    public ThePanel() {
        minA = -1.8;
        maxA = 0.5;
        minB = -1.2;
        maxB = 1.2;


        addMouseListener(this);
        addKeyListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        BufferedImage pic = new BufferedImage(500, 500, BufferedImage.TYPE_3BYTE_BGR);
        MandlebrotDrawer j = new MandlebrotDrawer(0.3331340799999997, 0.33316351999999977, 0.05007637960047011, 0.050107099600470106);
        MandlebrotDrawer k = new MandlebrotDrawer(0.3058076430222222, 0.30588300942222213, -0.03060753892925949, -0.030682905329259488);
        MandlebrotDrawer l = new MandlebrotDrawer(0.2953516017777775, 0.2953810417777775, 0.021784920141010678, 0.021815640141010676);
//        0.3331340799999997, 0.33316351999999977, 0.05007637960047011, 0.050107099600470106)
//        -1.4011612728888894,-1.4011318328888893,2.8229189189286433E-5,5.894918918928644E-5
        pic = l.returnMandlebrotImage(pic, 100000);

        try {
            l.save(pic, "6");
        } catch (IOException e) {
            e.printStackTrace();
        }


        //        j.drawMandlebrot(g2, getWidth(), getHeight());


        double width = maxA - minA;
        double height = maxB - minB;
        g2.setColor(Color.black);

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                double a = x * (width / getWidth()) + minA;
                double b = y * (height / getHeight()) + minB;
                if (isInSet(a, b, 1000)) {
                    g2.setColor(Color.black);
                } else {
                    g2.setColor(Color.getHSBColor(0.05f + x / (float) (getWidth() * 20), 1, 0.5f));
                }
                g2.fillRect(x, y, 1, 1);
            }
        }

        System.out.println("completed");


    }


    public boolean isInSet(double a, double b, int c) {
        double tempA = a;
        double tempB = b;

        int count = 0;

        while (Math.abs(tempA) < 4.0) {
            double newTA = tempA * tempA - tempB * tempB + a;
            tempB = 2 * tempA * tempB + b;
            tempA = newTA;
            count++;

            if (count > c) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int xClick = e.getX();
        int yClick = e.getY();

        System.out.println("asd");

        double width = maxA - minA;
        double height = maxB - minB;

        double a = xClick * (width / getWidth()) + minA;
        double b = yClick * (height / getHeight()) + minB;


        if (xClick < 10 && yClick < 10) {
            minA = -1.8;
            maxA = 0.5;
            minB = -1.2;
            maxB = 1.2;


            repaint();
        } else {
            minA = a - (width / 10);
            maxA = a + (width / 10);
            minB = b - (height / 10);
            maxB = b + (height / 10);

            System.out.println(minA + "," + maxA + "," + minB + "," + maxB);

            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

        int key = keyEvent.getKeyChar();
        if (key == KeyEvent.VK_SPACE) {

            System.out.println("adf");

            double width = maxA - minA;
            double height = maxB - minB;

            double a = minA + (width / 2);
            double b = minB + (height / 2);


            minA = a - (width * 2);
            maxA = a + (width * 2);
            minB = b - (width * 2);
            maxB = b + (width * 2);
            repaint();
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}