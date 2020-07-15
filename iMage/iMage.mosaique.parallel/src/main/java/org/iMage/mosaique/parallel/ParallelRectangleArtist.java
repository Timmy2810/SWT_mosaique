package org.iMage.mosaique.parallel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;
import org.iMage.mosaique.AbstractArtist;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.base.IMosaiqueArtist;
import org.iMage.mosaique.base.IMosaiqueShape;
import org.iMage.mosaique.rectangle.RectangleCalculator;

public class ParallelRectangleArtist extends AbstractArtist implements IMosaiqueArtist<BufferedArtImage>, Runnable {

    private List<ParallelRectangleShape> shapes;
    private int tileWidth = 0;
    private int tileHeight = 0;
    private int start = 0;
    private int stopp = 0;
    private List<BufferedImage> images = null;

    private static CyclicBarrier barrier = null;


    public ParallelRectangleArtist(List<BufferedImage> images, int tileWidth, int tileHeight, int numThreads) {
        super(tileWidth, tileHeight);
        if (images.isEmpty()) {
            throw new IllegalArgumentException("no tiles provided");
        }

        this.shapes = new ArrayList<>();
        ParallelRectangleArtist[] vmmArbeiter = new ParallelRectangleArtist[numThreads];
        Thread[] vmmFaeden = new Thread[numThreads];

        barrier = new CyclicBarrier(numThreads);

        int start = 0;
        int stopp = 0;
        int anzahlDurchlaeufe = (int) Math.ceil((double) images.size() / numThreads);

        for (int f = 0; f < numThreads; f++) {
            start = f * anzahlDurchlaeufe;
            stopp = Math.min((f + 1) * anzahlDurchlaeufe, images.size());

            vmmArbeiter[f] = new ParallelRectangleArtist(images, tileWidth, tileHeight, start, stopp, shapes);
            vmmFaeden[f] = new Thread(vmmArbeiter[f]);
            vmmFaeden[f].start();
        }
    }

    @Override
    public void run() {
        for (int x = start; x < stopp; x++) {
            shapes.add(x, new ParallelRectangleShape(images.get(x), tileWidth, tileHeight));
        }

        try { barrier.await();}
        catch (InterruptedException | BrokenBarrierException e) {e.printStackTrace();}
    }

    private ParallelRectangleArtist(List<BufferedImage> images, int tileWidth, int tileHeight,
                                    int start, int stopp, List<ParallelRectangleShape> shapes) {
        super(tileWidth, tileHeight);

        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.start = start;
        this.stopp = stopp;
        this.images = images;
        this.shapes = shapes;
    }

    public ParallelRectangleArtist(List<BufferedImage> images, int tileWidth, int tileHeight) {
        this(images, tileWidth, tileHeight, java.lang.Runtime.getRuntime().availableProcessors());
    }

    @Override
    public List<BufferedImage> getThumbnails() {
        return shapes.stream().map(ParallelRectangleShape::getThumbnail).collect(Collectors.toList());
    }

    @Override
    protected void drawTileForRegion(BufferedImage region, BufferedArtImage target) {
        int average = RectangleCalculator.getInstance().averageColor(region);
        IMosaiqueShape<BufferedArtImage> tile = findNearest(average, shapes);
        tile.drawMe(target);
    }


}


