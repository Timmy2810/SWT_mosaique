package org.iMage.iTiler;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ShowTiles extends JFrame {

    /**
     * Erstellt ein neues Fenster in welchem die Kachelbilder angezeigt werden
     * @param tiles entspricht der Liste aus Kachelbildern
     */
    public ShowTiles(ArrayList<BufferedImage> tiles) {
        super("Thumbnails");
        setSize(530, 530);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel images = new JPanel();
        images.setLayout(new GridLayout(0, 7, 2, 2));


        if (tiles == null) {
            JScrollPane scrollPane = new JScrollPane();
            getContentPane().add(scrollPane, BorderLayout.CENTER);
            return;
        }
        for (BufferedImage tile : tiles) {
            ImageIcon newImageIcon = new ImageIcon(tile);
            newImageIcon.setImage(newImageIcon.getImage().
                getScaledInstance(70, 70, Image.SCALE_SMOOTH));
            JLabel img = new JLabel(newImageIcon);
            images.add(img);
        }

        JScrollPane scrollPane = new JScrollPane(images);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
