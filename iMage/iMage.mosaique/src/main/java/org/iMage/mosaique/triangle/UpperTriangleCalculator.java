package org.iMage.mosaique.triangle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import org.iMage.mosaique.AbstractCalculator;

/**
 * Helper class for the {@link TriangleArtist} and {@link LowerTriangleShape}.
 *
 * @author Tim-Cedric Inhoff
 *
 */
public final class UpperTriangleCalculator extends AbstractCalculator {

    private static UpperTriangleCalculator upperTriangleCalculator = null;

    private UpperTriangleCalculator(){
    }

    public static UpperTriangleCalculator getInstance() {
        if (upperTriangleCalculator == null) {
            upperTriangleCalculator = new UpperTriangleCalculator();
        }
        return upperTriangleCalculator;
    }

    @Override
    protected Iterator getRegionIterator(BufferedImage region) {
        float m = (1F * region.getHeight()) / region.getWidth();

        ArrayList<Integer> list = new ArrayList<>();

        for (int x = 0; x < region.getWidth(); x++) {
            float yBound = Math.min((x + 1) * m, 0);
            for (int y = region.getHeight() - 1; y >= yBound; y--) {
                list.add(region.getRGB(x, y));
            }
        }
        return list.iterator();
    }
}
