package iMage.mosaique.parallel;

import java.awt.image.BufferedImage;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;
import org.iMage.mosaique.base.IMosaiqueEasel;

public class ParallelMosaiqueEasel implements IMosaiqueEasel<BufferedArtImage>, Runnable {

    private int anzahlFaeden = 1;
    private IMosaiqueArtist<BufferedArtImage> artist = null;
    private BufferedArtImage bild = null;
    private BufferedArtImage ergebnis = null;
    private int start = 0;
    private int stopp = 0;

    private static CyclicBarrier barrier = null;

    public ParallelMosaiqueEasel(int numThreads) {
        this.anzahlFaeden = numThreads;
    }

    public ParallelMosaiqueEasel() {
        this(java.lang.Runtime.getRuntime().availableProcessors());
    }

    private ParallelMosaiqueEasel(BufferedArtImage input, IMosaiqueArtist<BufferedArtImage> artist,
                                  int start, int stopp, BufferedArtImage result) {

        this.bild = input;
        this.ergebnis = result;
        this.artist = artist;
        this.start = start;
        this.stopp = stopp;
    }


    @Override
    public void run() {
        int tileWidth = artist.getTileWidth();
        int tileHeight = artist.getTileHeight();

        for (int x = start; x < stopp; x += tileWidth) {
            for (int y = 0; y < bild.getHeight(); y += tileHeight) {
                int width = x + tileWidth < bild.getWidth() ? tileWidth : bild.getWidth() - x;
                int height = y + tileHeight < bild.getHeight() ? tileHeight : bild.getHeight() - y;

                BufferedArtImage sub = bild.getSubimage(x, y, width, height);
                BufferedArtImage tile = artist.getTileForRegion(sub);
                ergebnis.setSubimage(x, y, tile);
            }
        }

        try { barrier.await();}
        catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
    }


    @Override
    public BufferedImage createMosaique(BufferedImage input, IMosaiqueArtist<BufferedArtImage> artist) {
        ParallelMosaiqueEasel[] vmmArbeiter = new ParallelMosaiqueEasel[anzahlFaeden];
        Thread[] vmmFaeden = new Thread[anzahlFaeden];

        barrier = new CyclicBarrier(anzahlFaeden);


        BufferedArtImage bild = new BufferedArtImage(input);
        int start = 0;
        int stopp = 0;
        int anzahlDurchlaeufe = (int) Math.ceil((double) bild.getWidth() / anzahlFaeden);

        for (int f = 0; f < anzahlFaeden; f++) {
            start = f * anzahlDurchlaeufe;
            stopp = Math.min((f+1) * anzahlDurchlaeufe, bild.getWidth());

            vmmArbeiter[f] = new ParallelMosaiqueEasel(bild, artist, start, stopp, bild.createBlankImage());
            vmmFaeden[f] = new Thread(vmmArbeiter[f]);
            vmmFaeden[f].start();
        }

        return ergebnis.toBufferedImage();
    }
}