package org.iMage.iTiler;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class ShowTiles extends JFrame {

    public ShowTiles(String tilesPath) {
        super("Thumbnails");
        setSize(530, 530);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel images = new JPanel();
        images.setLayout(new GridLayout(0, 7, 2, 2));

        File dir = new File(tilesPath);
        String[] dirList = dir.list();
        if (dirList == null) {
            return;
        }
        for (int i = 0; i < dirList.length; i++) {
            ImageIcon newImageIcon = new ImageIcon(tilesPath + "\\" + dirList[i]);
            newImageIcon.setImage(newImageIcon.getImage().
                getScaledInstance(70, 70, Image.SCALE_SMOOTH));
            JLabel img = new JLabel(newImageIcon);
            images.add(img);
        }

        JScrollPane scrollPane = new JScrollPane(images);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}
