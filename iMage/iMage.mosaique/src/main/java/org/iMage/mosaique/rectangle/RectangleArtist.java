package org.iMage.mosaique.rectangle;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class represents an {@link IMosaiqueArtist} who uses rectangles as tiles.
 *
 * @author Dominik Fuchss
 */
public class RectangleArtist implements IMosaiqueArtist<BufferedArtImage> {

    private List<RectangleShape> scaledImageList;
    private int tileWidth;
    private int tileHeight;

    /**
     * Create an artist who works with {@link RectangleShape RectangleShapes}
     *
     * @param images     the images for the tiles
     * @param tileWidth  the desired width of the tiles
     * @param tileHeight the desired height of the tiles
     * @throws IllegalArgumentException iff tileWidth or tileHeight &lt;= 0, or images is empty.
     */
    public RectangleArtist(Collection<BufferedArtImage> images, int tileWidth, int tileHeight) {
        scaledImageList = new ArrayList<>();

        for (BufferedArtImage img : images) {
            scaledImageList.add(new RectangleShape(img, tileWidth, tileHeight));
        }
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
    }

    @Override
    public List<BufferedImage> getThumbnails() {
        throw new RuntimeException("not implemented");
    }

    /**
     * Die Methode gibt für einen bestimmten Bildausschnitt ein BufferedArtImage zurück, welches den möglichst gleichen
     * Farbdurchschnitt besitzt
     *
     * @param region der Bildausschnitt
     * @return das möglichst ähnliche Bild
     */
    @Override
    public BufferedArtImage getTileForRegion(BufferedArtImage region) {

        RectangleShape regionScaled = new RectangleShape(region, region.getWidth(), region.getHeight());
        RectangleShape tempImage = scaledImageList.get(0);
        float[] regionAverage = new float[4];
        regionAverage[0] = (regionScaled.getAverageColor() >> 24) & 255;
        regionAverage[1] = (regionScaled.getAverageColor() >> 16) & 255;
        regionAverage[2] = (regionScaled.getAverageColor() >> 8) & 255;
        regionAverage[3] = regionScaled.getAverageColor() & 255;
        double divNow = Double.MAX_VALUE;
        double divAfter;

        for (RectangleShape image : scaledImageList) {
            int argb = image.getAverageColor();
            divAfter = Math.sqrt(Math.pow(regionAverage[0] - (float) ((argb >> 24) & 255), 2)
                + Math.pow(regionAverage[1] - (float) ((argb >> 16) & 255), 2)
                + Math.pow(regionAverage[2] - (float) ((argb >> 8) & 255), 2)
                + Math.pow(regionAverage[3] - (float) (argb & 255), 2));
            if (divAfter < divNow) {
                tempImage = image;
                divNow = divAfter;
            }
        }
        return tempImage.getImage();
    }

    /**
     * @return gibt die Kachelbreite zurück
     */
    @Override
    public int getTileWidth() {
        return tileWidth;
    }

    /**
     * @return gibt die Kachelhöhe zurück
     */
    @Override
    public int getTileHeight() {
        return tileHeight;
    }
}
