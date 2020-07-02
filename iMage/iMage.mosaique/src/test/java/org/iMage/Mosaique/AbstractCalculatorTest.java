package org.iMage.Mosaique;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.iMage.mosaique.AbstractCalculator;
import org.junit.Before;
import org.junit.Test;

public class AbstractCalculatorTest {

    private AbstractCalculator abstractCalculator;
    private TriangleCalculator triangleCalculator;

    private BufferedImage image;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage image4;


    @Before
    public void setUpBefor() throws IOException {
        triangleCalculator = TriangleCalculator.getInstance();
        image = ImageIO.read(new File("C:\\Users\\Timhoff\\Desktop\\SWT ab AB3\\iMage\\iMage.mosaique\\src\\test\\resources\\image.jpg"));
        image2 = ImageIO.read(new File("C:\\Users\\Timhoff\\Desktop\\Mosaikbilder\\0001.jpg"));
        image3 = ImageIO.read(new File("C:\\Users\\Timhoff\\Desktop\\Mosaikbilder\\1008.png"));
        image4 = ImageIO.read(new File("C:\\Users\\Timhoff\\Desktop\\Mosaikbilder\\1003.png"));

    }
    @Test
    public void test() {
        assertEquals(triangleCalculator.averageLowerColor(image), triangleCalculator.averageLowerColor2(image));
        assertEquals(triangleCalculator.averageLowerColor(image2), triangleCalculator.averageLowerColor2(image2));
        assertEquals(triangleCalculator.averageLowerColor(image3), triangleCalculator.averageLowerColor2(image3));
        assertEquals(triangleCalculator.averageLowerColor(image4), triangleCalculator.averageLowerColor2(image4));
    }
}
