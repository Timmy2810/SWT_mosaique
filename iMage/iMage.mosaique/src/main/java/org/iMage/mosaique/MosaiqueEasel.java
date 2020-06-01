package org.iMage.mosaique;

import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;
import org.iMage.mosaique.base.IMosaiqueEasel;

import java.awt.image.BufferedImage;

/**
 * This class defines an {@link IMosaiqueEasel} which operates on {@link BufferedArtImage
 * BufferedArtImages}.
 *
 * @author Dominik Fuchss
 */
public class MosaiqueEasel implements IMosaiqueEasel<BufferedArtImage> {

    @Override
    public BufferedImage createMosaique(BufferedImage input,
                                        IMosaiqueArtist<BufferedArtImage> artist) {


        BufferedArtImage bufferedArtImage = new BufferedArtImage(input);
        BufferedArtImage subImage;

        int tileWidth = artist.getTileWidth();
        int tileHeight = artist.getTileHeight();

        int tempWidth = 0;
        int tempHeight = 0;

        int countWidth = input.getWidth() / tileWidth;
        int countHeight = input.getHeight() / tileHeight;


        for (int i = 0; i < countWidth; i++) {
            for (int j = 0; j < countHeight; j++) {
                subImage = bufferedArtImage.getSubimage(tempWidth, tempHeight, tileWidth, tileHeight);

                bufferedArtImage.setSubimage(tempWidth, tempHeight, artist.getTileForRegion(subImage));

                tempHeight += tileHeight;
            }
            tempWidth += tileWidth;
            tempHeight = 0;
        }


        return bufferedArtImage.toBufferedImage();
    }

}
