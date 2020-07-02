package org.iMage.mosaique.rectangle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import org.iMage.mosaique.AbstractCalculator;

/**
 * Helper class for the {@link RectangleArtist} and {@link RectangleShape}.
 *
 * @author Dominik Fuchss
 */
public final class RectangleCalculator extends AbstractCalculator {

    private static RectangleCalculator rectangleCalculator = null;

    private RectangleCalculator() {

    }

    /**
     * @return gibt die einzige Instanz des RectangleCalculators zurück
     */
    public static RectangleCalculator getInstance() {
        if (rectangleCalculator == null) {
            rectangleCalculator = new RectangleCalculator();
        }
        return rectangleCalculator;
    }

    @Override
    protected Iterator getRegionIterator(BufferedImage region) {
        ArrayList<Integer> list = new ArrayList<>();

        for (int x = 0; x < region.getWidth(); x++) {
            for (int y = 0; y < region.getHeight(); y++) {
                list.add(region.getRGB(x, y));
            }
        }
        return list.iterator();
    }

}
