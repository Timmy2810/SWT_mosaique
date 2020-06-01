package org.iMage.mosaique.cli;

import org.apache.commons.cli.*;
import org.iMage.mosaique.MosaiqueEasel;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleArtist;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses all command line parameters and creates a mosaique.
 */
public final class App {
    private App() {
        throw new IllegalAccessError();
    }

    private static final String CMD_OPTION_INPUT_IMAGE = "i";
    private static final String CMD_OPTION_INPUT_TILES_DIR = "t";
    private static final String CMD_OPTION_OUTPUT_IMAGE = "o";

    private static final String CMD_OPTION_TILE_W = "w";
    private static final String CMD_OPTION_TILE_H = "h";

    public static void main(String[] args) throws RuntimeException, IOException {
        // Don't touch...
        CommandLine cmd = null;
        try {
            cmd = App.doCommandLineParsing(args);
        } catch (ParseException e) {
            System.err.println("Wrong command line arguments given: " + e.getMessage());
            System.exit(1);
        }
        // ...this!

        BufferedImage img = null;
        File inputDir = new File(cmd.getOptionValue(App.CMD_OPTION_INPUT_TILES_DIR));
        File outDir = new File(cmd.getOptionValue(App.CMD_OPTION_OUTPUT_IMAGE));
        List<BufferedArtImage> imageList = new ArrayList<>();

        try {
            File inputImage = new File(cmd.getOptionValue(App.CMD_OPTION_INPUT_IMAGE));
            img = ImageIO.read(inputImage);
        } catch (IOException e) {
            System.err.println("Eingabebild nicht gefunden");
            System.exit(1);
        }


        try {
            File[] images = inputDir.listFiles();
            for (int i = 0; i < images.length; i++) {
                BufferedArtImage skaledImage = new BufferedArtImage(ImageIO.read(images[i]));
                imageList.add(skaledImage);
            }
        } catch (IOException e) {
            System.err.println("Bilddatei nicht gefunden");
        }


        int w;
        int h;

        try {
            w = Integer.parseInt(cmd.getOptionValue(App.CMD_OPTION_TILE_W));
        } catch (NumberFormatException e) {
            System.err.println("Keine Breite angegeben, es wird die Standardbreite genutzt");
            w = (int) Math.floor(img.getWidth() * 0.1);
        }

        try {
            h = Integer.parseInt(cmd.getOptionValue(App.CMD_OPTION_TILE_H));
        } catch (NumberFormatException e) {
            System.err.println("Keine Höhe angegeben, es wird die Standardhöhe genutzt");
            h = (int) Math.floor(img.getHeight() * 0.1);
        }

        RectangleArtist rectangleArtist = new RectangleArtist(imageList, w, h);
        MosaiqueEasel mosaiqueEasel = new MosaiqueEasel();
        BufferedImage outImage = mosaiqueEasel.createMosaique(img, rectangleArtist);

        try {
            ImageIO.write(outImage, "png", outDir);
        } catch (IOException e) {
            System.err.println("Ausgabedatei konnte nicht erstellt werden");
            System.exit(1);
        }


    }

    /**
     * Parse and check command line arguments
     *
     * @param args command line arguments given by the user
     * @return CommandLine object encapsulating all options
     * @throws ParseException if wrong command line parameters or arguments are given
     */
    private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
        Options options = new Options();
        Option opt;

        /*
         * Define command line options and arguments
         */
        opt = new Option(App.CMD_OPTION_INPUT_IMAGE, "input-images", true, "path to input image");
        opt.setRequired(true);
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option(App.CMD_OPTION_INPUT_TILES_DIR, "tiles-dir", true, "path to tiles directory");
        opt.setRequired(true);
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option(App.CMD_OPTION_OUTPUT_IMAGE, "image-output", true, "path to output image");
        opt.setRequired(true);
        opt.setType(String.class);
        options.addOption(opt);

        opt = new Option(App.CMD_OPTION_TILE_W, "tile-width", true, "the width of a tile");
        opt.setRequired(false);
        opt.setType(Integer.class);
        options.addOption(opt);

        opt = new Option(App.CMD_OPTION_TILE_H, "tile-height", true, "the height of a tile");
        opt.setRequired(false);
        opt.setType(Integer.class);
        options.addOption(opt);

        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

}
