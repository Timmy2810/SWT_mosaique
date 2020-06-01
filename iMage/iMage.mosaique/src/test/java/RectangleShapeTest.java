import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleShape;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RectangleShapeTest {

    private RectangleShape rectangleShape;
    private BufferedArtImage testImage1; //10x10 white
    private BufferedArtImage testImage2; //12x12 black
    private static int whiteARGB;
    private static int blackARGB;
    private static int greenARGB;

    @BeforeClass
    public static void setUpClass() {
        whiteARGB = 255 << 24 | 255 << 16 | 255 << 8 | 255;
        blackARGB = 255 << 24 | 0 << 16 | 0 << 8 | 0;
        greenARGB = 255 << 24 | 0 << 16 | 255 << 8 | 0;
    }

    @Before
    public void setUp() {
        testImage1 = new BufferedArtImage(10, 10);
        testImage1.createBlankImage();
        int w = testImage1.getWidth();
        int h = testImage1.getHeight();
        for (int i = 0; i < w; i++) { //i und j könnten bei 1 beginnen
            for (int j = 0; j < h; j++) {
                testImage1.setRGB(i, j, whiteARGB);
            }
        }
        rectangleShape = new RectangleShape(testImage1, w, h);


        testImage2 = new BufferedArtImage(8, 8);
        testImage2.createBlankImage();
        w = testImage2.getWidth();
        h = testImage2.getHeight();
        for (int i = 0; i < w; i++) { //i und j könnten bei 1 beginnen
            for (int j = 0; j < h; j++) {
                testImage2.setRGB(i, j, blackARGB);
            }
        }
    }


    @Test
    public final void testAverageColor() {
        //Das image wurde einfarbig aus weiß erstellt und muss daher das gleiche Ergebnis liefern
        assertEquals(whiteARGB, rectangleShape.getAverageColor());
    }

    @Test
    public final void testDrawMe() {
        rectangleShape.drawMe(testImage2);
    }

    @After
    public void tearDown() {

    }

}
