package org.iMage.mosaique;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Iterator;

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

        Iterator<Integer> iterator = getRegionIterator(region);
        while (iterator.hasNext()) {
            int col = iterator.next();

            Color c = new Color(col, true);
            r += c.getRed();
            g += c.getGreen();
            b += c.getBlue();
            a += c.getAlpha();
            ctr++;
        }
        return new Color((int) (r / ctr), (int) (g / ctr), (int) (b / ctr), (int) (a / ctr)).getRGB();
    }

    /**
     * Gibt einen Iterator, welcher die RGB-Werte jedes Pixels des region Bildes beinhaltet zurück
     *
     * @param region entspricht dem region Bild
     * @return gibt den Iterator zurück
     */
    protected abstract Iterator getRegionIterator(BufferedImage region);
}
