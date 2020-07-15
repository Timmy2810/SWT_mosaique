package org.iMage.mosaique.parallel;

import java.awt.image.BufferedImage;
import org.iMage.mosaique.AbstractCalculator;
import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;

public class ParallelRectangleShape extends AbstractShape implements IMosaiqueShape<BufferedArtImage> {

    /**
     * Create a new {@link IMosaiqueShape}.
     *
     * @param image the image to use
     * @param w     the width
     * @param h     the height
     */
    public ParallelRectangleShape(BufferedImage image, int w, int h) {
        super(new BufferedArtImage(image), w, h);
    }

    @Override
    protected AbstractCalculator getCalculator() {
        return null;
    }
}
