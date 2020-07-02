package org.iMage.mosaique.triangle;

import java.awt.image.BufferedImage;
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
    protected int getStart(BufferedImage region, int countWidth) {
        float m = (1F * region.getHeight()) / region.getWidth();
        return Math.round(Math.min((countWidth + 1) * m, 0));
    }
}
