package org.iMage.mosaique.triangle;

import java.awt.image.BufferedImage;
import org.iMage.mosaique.AbstractCalculator;

/**
 * Helper class for the {@link TriangleArtist} and {@link LowerTriangleShape}.
 *
 * @author Tim-Cedric Inhoff
 *
 */
public final class LowerTriangleCalculator extends AbstractCalculator {

    private static LowerTriangleCalculator lowerTriangleCalculator = null;

    private LowerTriangleCalculator(){
    }

    public static LowerTriangleCalculator getInstance() {
        if (lowerTriangleCalculator == null) {
            lowerTriangleCalculator = new LowerTriangleCalculator();
        }
        return lowerTriangleCalculator;
    }

    @Override
    protected int getStart(BufferedImage region, int countWidth) {
        float m = (1F * region.getHeight()) / region.getWidth();
        return Math.round(Math.max((countWidth + 1) * m, 0));
    }
}
