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

        GridBagLayout gbl = new GridBagLayout();
        setLayout(new FlowLayout(2));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(2, 2, 2, 2);

        JScrollBar scrollBar = new JScrollBar(Adjustable.VERTICAL, 0, 100, 0, 100);
        add(scrollBar);

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
            add(img);
        }

        /*
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < dirList.length / 7; j++) {
                String filename =
            }
        }

         */

    }
}
