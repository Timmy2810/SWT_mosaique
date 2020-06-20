package org.iMage.iTiler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI extends JFrame implements ActionListener {

    private final JButton loadInput;
    private final JButton saveInput;
    private final JButton loadTiles;
    private final JButton showTiles;
    private final JButton run;
    private final JLabel inputImageLable;
    private final JLabel outputImageLable;
    private final JTextField width;
    private final JTextField height;
    private String inputPath;
    private String inputTilePath;
    private BufferedImage inputImage;
    private BufferedImage outputImage;
    private final JComboBox auswahl;
    private final Control control;
    private boolean recktangle;

    /**
     * Erstellt die GBO und initialisiert die zu initialisierenden Parameter;
     */
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

        inputImageLable = new JLabel();
        inputImageLable.setPreferredSize(new Dimension(350, 250));
        panelPictures.add(inputImageLable, BorderLayout.WEST);
        outputImageLable = new JLabel();
        outputImageLable.setPreferredSize(new Dimension(350, 250));
        panelPictures.add(outputImageLable, BorderLayout.EAST);
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
        String[] comboBox = {"Rectange", "Triangle"};
        auswahl = new JComboBox(comboBox);
        options.add(auswahl);

        run = new JButton("Run");
        run.addActionListener(this);
        options.add(run);
        gbl.setConstraints(options, c);
        add(options);

        inputTilePath = "";
        inputPath = "";
        control = new Control();
        recktangle = true;
    }

    /**
     * Lädt das Inputbild und zeigt dieses in der GBO an
     */
    private void loadInputPicture() {
        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter =
            new FileNameExtensionFilter("Bilder", "png", "jpg", "jpeg");
        chooser.addChoosableFileFilter(filter);
        int cooserValue = chooser.showOpenDialog(null);

        if (cooserValue == JFileChooser.APPROVE_OPTION) {
            inputPath = chooser.getSelectedFile().getPath();
            ImageIcon newImageIcon = new ImageIcon(inputPath);
            inputImageLable.setIcon(scaleImageIcon(newImageIcon, 350, 250));
            try {
                inputImage = ImageIO.read(chooser.getSelectedFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Speichert das Output Bild
     * @throws IOException wird geworfen, falls das Speichern fehlgeschlagen ist
     */
    private void saveInputPicture() throws IOException {
        JFileChooser chooser = new JFileChooser();
        int chooserValue = chooser.showSaveDialog(null);
        if (chooserValue != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File outF = new File(chooser.getSelectedFile().getPath());
        ImageIO.write(outputImage, "png", outF);
    }

    /**
     * Überprüft ob die Kachelbreite eine Zahl ist (Ob sie im Zahlenbereich liegt wird in Control getestet)
     */
    private void isWidthInteger() {
        if (width.getText().matches("\\d*")) {
            width.setForeground(Color.BLACK);
        } else {
            width.setForeground(Color.RED);
        }
    }

    /**
     * Überprüft ob die Kachelhöhe eine Zahl ist (Ob sie im Zahlenbereich liegt wird in Control getestet)
     */
    private void isHeightInteger() {
        if (height.getText().matches("\\d*")) {
            height.setForeground(Color.BLACK);
        } else {
            height.setForeground(Color.RED);
        }
    }

    /**
     * Erstellt das Mosaique und zeigt dieses in der GBO an
     */
    private void run() {
        if (inputPath.isEmpty() || inputTilePath.isEmpty()) {
            return;
        }

        if (auswahl.getSelectedIndex() == 1) { //Entspicht Triangle
            recktangle = false;
        }

        outputImage = control.createMosaique(inputImage, getTileWidth(), getTileHeight(), recktangle);

        if (outputImage == null) {
            return;
        }

        ImageIcon newImageIcon = new ImageIcon(outputImage);
        outputImageLable.setIcon(scaleImageIcon(newImageIcon, 350, 250));
        saveInput.setEnabled(true);
    }

    /**
     * @return gibt die aktuell eingegebene Kachelbreite zurück
     */
    private int getTileWidth() {
        if (width.getText().isEmpty()) {
            return 42;
        } else {
            return Integer.parseInt(width.getText());
        }
    }

    /**
     * @return gibt die aktuell eingegebene Kachelhöhe zurück
     */
    private int getTileHeight() {
        if (height.getText().isEmpty()) {
            return 42;
        } else {
            return Integer.parseInt(height.getText());
        }
    }

    /**
     * wählt den Ordner mit den Kachelbildern aus
     */
    private void selectTiles() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int chooserValue = chooser.showOpenDialog(null);
        if (chooserValue == JFileChooser.APPROVE_OPTION) {
            inputTilePath = chooser.getSelectedFile().getPath();
            control.updateTiles(inputTilePath);
        }
    }

    /**
     * Zeigt die Kachelbilder an
     */
    private void showAllTiles() {
        ShowTiles st = new ShowTiles(new ArrayList<>(control.getThubnails(getTileWidth(),
            getTileHeight(), recktangle)));
        st.setVisible(true);
    }

    /**
     * Skaliert das Eingabebild image auf die Breite toWidth und die Höhe toHeight ohne es zu verzerren
     *
     * @param image entspricht dem zu skalierendem Bild
     * @param toWidth entspricht der zu skalierenden Breite
     * @param toHeight entspricht der zu skalierenden Höhe
     * @return gibt das Skalierte Bild zurück
     */
    private ImageIcon scaleImageIcon(ImageIcon image, int toWidth, int toHeight) {
        int width = image.getIconWidth();
        int height = image.getIconHeight();
        if (width > height) {
            double scaleFactor = (double) height / width;
            int newHeight = (int) Math.ceil((double) toWidth * scaleFactor);
            image.setImage(image.getImage().getScaledInstance(toWidth, newHeight, Image.SCALE_SMOOTH));
        } else {
            double scaleFactor = (double) width / height;
            int newWidth = (int) Math.ceil((double) toHeight * scaleFactor);
            image.setImage(image.getImage().getScaledInstance(newWidth, toHeight, Image.SCALE_SMOOTH));
        }
        return image;
    }

    /**
     * Erstellt die GBO
     * @param args Kommandozeilenarguemnte - werden nicht weiter benötigt
     */
    public static void main(String[] args) {
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

