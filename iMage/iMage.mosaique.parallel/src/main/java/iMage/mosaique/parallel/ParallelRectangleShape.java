package iMage.mosaique.parallel;

import java.awt.image.BufferedImage;
import org.iMage.mosaique.AbstractShape;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;
import org.iMage.mosaique.rectangle.RectangleCalculator;

public class ParallelRectangleShape extends AbstractShape implements IMosaiqueShape<BufferedArtImage> {

    /**
     * Create a new {@link IMosaiqueShape}.
     *
     * @param image the image to use
     * @param w     the width
     * @param h     the height
     */
    public ParallelRectangleShape(BufferedImage image, int w, int h) {
        super(image, w, h);
    }

    @Override
    public BufferedImage getThumbnail() {
        return image;
    }

    @Override
    protected int calcAverage() {
        return RectangleCalculator.getInstance().averageColor(image);
    }

    @Override
    protected void drawShape(BufferedArtImage targetRect, int w, int h) {
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                targetRect.setRGB(x, y, image.getRGB(x, y));
            }
        }
    }
}
