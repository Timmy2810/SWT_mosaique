package org.iMage.mosaique;

import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class AbstractCalculator {

    /**
     * Calculate the average color for an region.
     *
     * @param region
     *          the region
     * @return the color as ARGB
     */
    public int averageColor(BufferedImage region) {
        long r = 0;
        long g = 0;
        long b = 0;
        long a = 0;
        int ctr = 0;



        for (int x = 0; x < region.getWidth(); x++) {
            for (int y = getStart(region, x); y < region.getHeight(); y++) {
                int col = region.getRGB(x, y);

                Color c = new Color(col, true);
                r += c.getRed();
                g += c.getGreen();
                b += c.getBlue();
                a += c.getAlpha();
                ctr++;
            }

        }

        return new Color((int) (r / ctr), (int) (g / ctr), (int) (b / ctr), (int) (a / ctr)).getRGB();
    }

    /**
     * Gibt den Startpunkt der for-Schleife an
     * @param region wird benötigt um den Startpunkt zu errechnen
     * @param countWidth wird benötigt um den Startpunkt zu errechnen
     * @return gibt den Startpunkt zurück
     */
    protected abstract int getStart(BufferedImage region, int countWidth);
}
