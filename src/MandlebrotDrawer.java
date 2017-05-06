import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * Created by simon_clark on 2/5/16.
 */
public class MandlebrotDrawer {

    private double minA, maxA, minB, maxB;
    private double x, y, r;

    public MandlebrotDrawer(double inputMinA, double inputMaxA, double inputMinB, double inputMaxB) {
        minA = inputMinA;
        maxA = inputMaxA;
        minB = inputMinB;
        maxB = inputMaxB;
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

    public void drawMandlebrot(Graphics2D g2, int winW, int winH) {
        double minA = x - r;
        double minB = y - r;
        g2.setColor(Color.black);

        for (int x = 0; x < winW; x++) {
            for (int y = 0; y < winH; y++) {
                double a = x * (r / winW) + minA;
                double b = y * (r / winH) + minB;
                if (isInSet(a, b, 1000)) {
                    g2.setColor(Color.black);
                } else {
                    g2.setColor(Color.getHSBColor(0.05f + x / (float) (winW * 20), 1, 0.5f));
                }
                g2.fillRect(x, y, 1, 1);
            }
        }
        System.out.println("mandlebrot drawn");

    }

    public BufferedImage returnMandlebrotImage(BufferedImage image, int check) {

        double width = maxA - minA;
        double height = maxB - minB;
        Graphics img = image.createGraphics();
        img.setColor(Color.black);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                double a = x * (width / image.getWidth()) + minA;
                double b = y * (height / image.getHeight()) + minB;
                if (isInSet(a, b, check)) {
                    img.setColor(Color.black);
                } else {
                    img.setColor(Color.getHSBColor(0.05f + x / (float) (image.getWidth() * 20), 1, 0.5f));
                }
                img.fillRect(x, y, 1, 1);
            }
        }
        System.out.println("image returned");

        return image;

    }


    public void save(BufferedImage image, String title) throws IOException {
        ImageIO.write(image, "PNG", new File(title + ".png"));
        System.out.println("file saved");
    }

}
