package org.iMage.iTiler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.iMage.mosaique.MosaiqueEasel;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleArtist;
import org.iMage.mosaique.triangle.TriangleArtist;

public class Control {

    private List<BufferedArtImage> tiles;
    private RectangleArtist ra;
    private TriangleArtist ta;

    /**
     * Konstruktor initialisiert die Parameter
     */
    public Control() {
        tiles = new ArrayList<>();
        ra = null;
        ta = null;
    }

    /**
     * Erstellt das Mosaique
     *
     * @param inputImage entspricht dem Eingabebild
     * @param tileW entspricht der Breite der einzelnen Kacheln
     * @param tileH entspricht der Höhe der einzelnen Kacheln
     * @param recktangle gibt an ob es sich um ein Recktangle oder Triangle Bild handeln soll
     * @return gibt das Mosaique Bild zurück
     */
    public BufferedImage createMosaique(BufferedImage inputImage, int tileW, int tileH, boolean recktangle) {

        if (tiles == null) {
            return null;
        }

        if (tileW <= 0 || tileH <= 0 || tileW > inputImage.getWidth() || tileH > inputImage
            .getHeight()) {
            System.err.println("tileW/H is invalid: " + tileW + "," + tileH);
            return null;
        }

        MosaiqueEasel me = new MosaiqueEasel();

        if (recktangle) {
            if (ra == null || ra.getTileWidth() != tileW || ra.getTileHeight() != tileH) {
                ra = new RectangleArtist(tiles, tileW, tileH);
            }
            return me.createMosaique(inputImage, ra);
        } else {
            if (ta == null || ta.getTileWidth() != tileW || ta.getTileHeight() != tileH) {
                ta = new TriangleArtist(tiles, tileW, tileH);
            }
            return me.createMosaique(inputImage, ta);
        }
    }

    /**
     * Speichert und Überprüft die Eingabe der Kachelbilder anhand eines Pfades
     *
     * @param tileDir entspricht dem Kachelpfad
     */
    public void updateTiles(String tileDir) {
        List<BufferedArtImage> tilesTemp = new ArrayList<>();
        try {
            File directory = Control.ensureFile(tileDir);
            FileFilter isImage = f -> f.getName().toLowerCase().endsWith(".jpeg") || f.getName()
                .toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png");

            for (File file : directory.listFiles(isImage)) {
                BufferedImage bi = ImageIO.read(file);
                BufferedArtImage bai = new BufferedArtImage(bi);
                tilesTemp.add(bai);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        if (tilesTemp.size() < 10) {
            System.err.println("Not enough tiles found");
            System.exit(1);
        }
        tiles = tilesTemp;
    }

    /**
     * Stellt sicher, dass eine Datei vorhanden ist
     *
     * @param path der Pfad zu der Datei
     * @return gibt die Datei zurück
     * @throws IOException wird geworfen, wenn etwas falsch lief
     */
    private static File ensureFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            return file;
        }

        // File not available
        throw new IOException("The specified file does not exist: " + path);
    }

    /**
     * gibt alle Kachelbilder zurück
     *
     * @param tileW entspricht der Breite der Kachelbilder
     * @param tileH entspricht der Höhe der Kachelbilder
     * @param recktangle gibt an, um welchen Artist es sich handelt
     * @return gibt eine Liste der Kachelbilder zurück
     */
    public List<BufferedImage> getThubnails(int tileW, int tileH, boolean recktangle) {

        if (tiles.isEmpty()) {
            return new ArrayList<>();
        }

        if (recktangle) {
            if (ra == null || ra.getTileWidth() != tileW || ra.getTileHeight() != tileH) {
                ra = new RectangleArtist(tiles, tileW, tileH);
            }
            return ra.getThumbnails();
        } else {
            if (ta == null || ta.getTileWidth() != tileW || ta.getTileHeight() != tileH) {
                ta = new TriangleArtist(tiles, tileW, tileH);
            }
            return ta.getThumbnails();
        }
    }
}
