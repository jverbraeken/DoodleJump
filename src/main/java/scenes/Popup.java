package scenes;
import system.Game;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Popup extends JFrame implements ActionListener {

    public Popup(final String msg) {

        // field.setSize(60, 15);
        JButton okButton = new JButton("ok");
        final JLabel label = new JLabel(msg);
        GridBagLayout gbag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(gbag);

        BufferedImage img = null;
        try {
            img = ImageIO.read(
                    new URL("http://rabbitbrush.frazmtn.com/kittens.jpg"));
            //ImageIO.read(new File("sprites/classic-popup-background@2x.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setUndecorated(true);
        JLabel background = new JLabel(new ImageIcon(img));

        // gbc.insets = new Insets(2, 0, 2, 0);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbag.setConstraints(label, gbc);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbag.setConstraints(background, gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbag.setConstraints(okButton, gbc);
        setContentPane(background);
        add(okButton);
        add(label);
        setTitle("Test name");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);


        setSize(400, 200);
        setLocationRelativeTo(Game.frame);
        setVisible(true);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (e.getActionCommand().equals("ok")) {
                    System.out.println("Hello");
                    dispose();
                    //setVisible(false);


                    // label.setText(field.getText());
                    // send(field.getText());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub

    }
}
//public class Popup implements ActionListener {
//
//    public static void CreatePopup(final String msg) {
////        try {
////            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
////        }
//
//        final JDialog dialog = new JDialog(Game.frame, "Boo");
//
//        JOptionPane op = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
//
//        dialog.setUndecorated(true);
//        dialog.setLayout(new BorderLayout());
//        dialog.add(op);
//        dialog.pack();
//        dialog.setLocationRelativeTo(Game.frame);
//        dialog.setVisible(true);
//        public void actionPerformed(ActionEvent e) {
//            dispose();
//        }
//    }
//
//}