package org.iMage.iTiler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import javax.imageio.ImageIO;
import javax.print.event.PrintJobListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.iMage.mosaique.cli.App;

public class GUI extends JFrame implements ActionListener {

    private JButton loadInput;
    private JButton saveInput;
    private JButton loadTiles;
    private JButton showTiles;
    private JButton run;
    private JLabel inputeImage;
    private JLabel outputImage;
    private JTextField width;
    private JTextField height;
    private String inputPath;
    private String inputTilePath;
    private String outputPath;
    private String widthTile;
    private String heightTile;
    private BufferedImage inputImage;

    public GUI() {
        super("iTiler");
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        GridBagLayout gbl = new GridBagLayout();
        setLayout(gbl);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(5, 5, 5, 5);

        //Erstellen des ersten Grids mit Ein- und Ausgabe- Bildern
        c.gridx = 0;
        c.gridy = 0;
        JPanel panelPictures = new JPanel(new BorderLayout(5, 5));

        inputeImage = new JLabel();
        inputeImage.setPreferredSize(new Dimension(350, 250));
        panelPictures.add(inputeImage, BorderLayout.WEST);
        outputImage = new JLabel();
        outputImage.setPreferredSize(new Dimension(350, 250));
        panelPictures.add(outputImage, BorderLayout.EAST);
        gbl.setConstraints(panelPictures, c);
        add(panelPictures);


        //Erstellen des zweiten Grids mit Lade- und Speicher- Knöpfen
        c.gridx = 0;
        c.gridy = 1;
        JPanel panelPart2 = new JPanel(new BorderLayout(200, 5));
        JLabel configuration = new JLabel("Configuration");
        panelPart2.add(configuration, BorderLayout.PAGE_START);
        loadInput = new JButton("LoadInput");
        loadInput.addActionListener(e -> loadInputPicture());
        saveInput = new JButton("SaveInput");
        saveInput.addActionListener(this);
        saveInput.setEnabled(false);
        panelPart2.add(loadInput, BorderLayout.WEST);
        panelPart2.add(saveInput, BorderLayout.EAST);
        gbl.setConstraints(panelPart2, c);
        add(panelPart2);

        //Titel für 3. Abschnitt
        c.gridx = 0;
        c.gridy = 2;
        JLabel artistic = new JLabel("Artistic");
        gbl.setConstraints(artistic, c);
        add(artistic);

        //Erstellen des dritten Grids mit Einstellungen zu Mosaique
        c.gridx = 0;
        c.gridy = 3;
        JPanel options = new JPanel(new FlowLayout(3));
        JLabel size = new JLabel("Tile Size");
        options.add(size);

        width = new JTextField();
        options.add(width);
        width.setPreferredSize(new Dimension(100, 25));
        width.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isWidthInteger();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isWidthInteger();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isWidthInteger();
            }
        });

        JLabel x = new JLabel("x");
        options.add(x);

        height = new JTextField();
        height.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isHeightInteger();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isHeightInteger();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isHeightInteger();
            }
        });
        height.setPreferredSize(new Dimension(100, 25));
        options.add(height);

        loadTiles = new JButton("Load Tiles");
        loadTiles.addActionListener(this);
        options.add(loadTiles);
        showTiles = new JButton("Show Tiles");
        showTiles.addActionListener(this);
        options.add(showTiles);
        JLabel artist = new JLabel("Artist");
        options.add(artist);
        run = new JButton("Run");
        run.addActionListener(this);
        options.add(run);
        gbl.setConstraints(options, c);
        add(options);

        inputTilePath = "";
        inputPath = "";
        outputPath = "C:\\Users\\Timhoff\\Desktop\\out.png";
    }

    private void loadInputPicture() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "png");
        chooser.addChoosableFileFilter(filter);
        int cooserValue = chooser.showOpenDialog(null);

        if (cooserValue == JFileChooser.APPROVE_OPTION) {
            inputPath = chooser.getSelectedFile().getPath();
            ImageIcon newImageIcon = new ImageIcon(inputPath);
            newImageIcon.setImage(newImageIcon.getImage().
                getScaledInstance(350, 250, Image.SCALE_SMOOTH));
            inputeImage.setIcon(newImageIcon);
            try {
                inputImage = ImageIO.read(chooser.getSelectedFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveInputPicture() throws IOException {
        JFileChooser chooser = new JFileChooser();
        int chooserValue = chooser.showSaveDialog(null);
        if (chooserValue != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File inF = new File(outputPath);
        File outF = new File(chooser.getSelectedFile().getPath());

        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(inF).getChannel();
            outChannel = new FileOutputStream(outF).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            } catch (IOException e) {}
        }
    }

    private void isWidthInteger() {
        if (width.getText().matches("\\d*")) {
            width.setForeground(Color.BLACK);
        } else {
            width.setForeground(Color.RED);
        }
    }

    private void isHeightInteger() {
        if (height.getText().matches("\\d*")) {
            height.setForeground(Color.BLACK);
        } else {
            height.setForeground(Color.RED);
        }
    }

    private void run() {
        if (inputPath.isEmpty() || inputTilePath.isEmpty()) {
            return;
        }
        if (width.getText().isEmpty()) {
            widthTile = String.valueOf(inputImage.getWidth() * 0.8);
        } else {
            widthTile = width.getText();
        }
        if (height.getText().isEmpty()) {
            heightTile = String.valueOf(inputImage.getHeight() * 0.8);
        } else {
            heightTile = height.getText();
        }

        String args = "-i " + inputPath + " -t " + inputTilePath + " -o "
            + outputPath + " -w " + widthTile + " -h " + heightTile;

        String[] test = args.split(" ");
        App.main(test);

        ImageIcon newImageIcon = new ImageIcon(outputPath);
        newImageIcon.setImage(newImageIcon.getImage().getScaledInstance(350, 250, Image.SCALE_SMOOTH));
        outputImage.setIcon(newImageIcon);
        saveInput.setEnabled(true);
    }

    private void selectTiles() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int chooserValue = chooser.showOpenDialog(null);
        if (chooserValue == JFileChooser.APPROVE_OPTION) {
            inputTilePath = chooser.getSelectedFile().getPath();
        }
    }

    private void showAllTiles() {
        ShowTiles st = new ShowTiles(inputTilePath);
        st.setVisible(true);
    }

    public static void main(String args[]) {
        GUI gui = new GUI();
        gui.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadInput) {
            loadInputPicture();
        } else if (e.getSource() == saveInput) {
            try {
                saveInputPicture();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == loadTiles) {
            selectTiles();
        } else if (e.getSource() == showTiles) {
            showAllTiles();
        } else if (e.getSource() == run) {
            run();
        }
    }
}

