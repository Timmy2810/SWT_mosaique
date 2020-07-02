package org.iMage.mosaique.rectangle;

import java.awt.image.BufferedImage;
import org.iMage.mosaique.AbstractCalculator;

/**
 * Helper class for the {@link RectangleArtist} and {@link RectangleShape}.
 *
 * @author Dominik Fuchss
 *
 */
public final class RectangleCalculator extends AbstractCalculator {

  private static RectangleCalculator rectangleCalculator = null;

  private RectangleCalculator() {

  }

  /**
   *
   * @return gibt die einzige Instanz des RectangleCalculators zurück
   */
  public static RectangleCalculator getInstance() {
    if (rectangleCalculator == null) {
      rectangleCalculator = new RectangleCalculator();
    }
    return rectangleCalculator;
  }

  @Override
  protected int getStart(BufferedImage region, int countWidth) {
    return 0;
  }

}
