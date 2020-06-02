package org.jis.view.dialog;

import org.jis.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListPlugins extends JDialog {

    public ListPlugins(Main frame) throws HeadlessException {
        JDialog fenster = new JDialog();
        fenster.setTitle("Load Plugins");
        fenster.setSize(500, 700);
        fenster.setModal(true);
        fenster.add(new JButton("Configure"));
        fenster.add(new JButton("Run"));
        fenster.setVisible(true);
        /*
        super(frame, "Load Plugins", true);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        GraphicsDevice gd = gs[0];
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        Rectangle bounds = gc.getBounds();
        this.setLocation((bounds.width), (bounds.height));

        this.setSize(500, 1000);
        GridLayout layout = new GridLayout(7, 1);
        layout.setHgap(3);
        layout.setVgap(3);

        setLayout(layout);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.add(new JLabel("Hallo Welt"));
        this.add(new JLabel(frame.mes.getString("AboutBox.20")));
        JButton close = new JButton(frame.mes.getString("AboutBox.15"));
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        this.add(close);
        setVisible(true);

         */

    }
}
