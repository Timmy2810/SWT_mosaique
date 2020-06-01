package org.iMage.mosaique.rectangle;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueShape;
import org.iMage.mosaique.base.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * This class represents a rectangle as {@link IMosaiqueShape} based on an {@link BufferedArtImage}.
 *
 * @author Dominik Fuchss
 */
public class RectangleShape implements IMosaiqueShape<BufferedArtImage> {

    private BufferedArtImage image;

    /**
     * Create a new {@link IMosaiqueShape}.
     *
     * @param image the image to use
     * @param w     the width
     * @param h     the height
     */
    public RectangleShape(BufferedArtImage image, int w, int h) {
        BufferedArtImage imageNew = new BufferedArtImage(ImageUtils.scaleAndCrop(image.toBufferedImage(), w, h));
        this.image = imageNew;
    }

    /**
     * @return gibt die Durchschnittsfarbe des skalierten bildes in Type_int_argb zurück
     */
    @Override
    public int getAverageColor() {
        int h = image.getHeight();
        int w = image.getWidth();
        double countAlpha = 0;
        double countBlue = 0;
        double countRed = 0;
        double countGreen = 0;
        double ratio = (double) 1 / (w * h);
        Color color;
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                color = new Color(image.getRGB(i, j));
                countAlpha = countAlpha + color.getAlpha();
                countBlue = countBlue + color.getBlue();
                countRed = countRed + color.getRed();
                countGreen = countGreen + color.getGreen();
            }
        }

        countAlpha = countAlpha * ratio;
        countBlue = countBlue * ratio;
        countRed = countRed * ratio;
        countGreen = countGreen * ratio;

        return (int) countAlpha << 24 | (int) countRed << 16 | (int) countGreen << 8 | (int) countBlue;
    }

    /**
     * @return gibt das image als BufferedImage zurück
     */
    @Override
    public BufferedImage getThumbnail() {
        return image.toBufferedImage();
    }

    /**
     * Die Methode überschreibt das übergebe Bild mit dem skalierten Bild
     *
     * @param targetRect das zu überschreibende Bild
     */
    @Override
    public void drawMe(BufferedArtImage targetRect) {
        if (targetRect.getHeight() > image.getHeight() || targetRect.getWidth() > image.getWidth()) {
            throw new RuntimeException("Das überschreibende Bild ist zu groß");
        }

        targetRect.setSubimage(0, 0, image);

    }


    /**
     * @return gibt die Höhe des skalierten Bildes zurück
     */
    @Override
    public int getHeight() {
        return image.getHeight();
    }

    /**
     * @return gibt die Breite des skalierten Bildes zurück
     */
    @Override
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * @return gibt das Bild als BufferesArtImage zurück
     */
    public BufferedArtImage getImage() {
        return image;
    }
}